package se.redmind.rmtest.report.reportvalidation;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.create.classinserter.ClassInserter;
import se.redmind.rmtest.db.create.suiteinserter.SuiteInserter;
import se.redmind.rmtest.db.create.testcaseinserter.TestCaseInserter;
import se.redmind.rmtest.db.create.testcaseruninserter.TestCaseRunInserter;
import se.redmind.rmtest.db.lookup.classname.ClassDbLookup;
import se.redmind.rmtest.db.lookup.report.ReportDbLookup;
import se.redmind.rmtest.db.lookup.suite.SuiteDbLookup;
import se.redmind.rmtest.db.lookup.testcase.TestcaseDbLookup;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportValidator {
	
	private ReportDbLookup readFromDB;
	private ReportLoader loader;
	private ReportXMLParser parser;
	private Connection connection;
	private String filename;
	private Report report;
	private File reportFile;
	private ClassInserter classInserter;
	private SuiteInserter suiteInserter;
	private TestCaseInserter testCaseInserter;
	private TestCaseRunInserter testCaseRunInserter;
	private ClassDbLookup readClassFromDB;
	private SuiteDbLookup readSuiteFromDB;
	private TestcaseDbLookup readTestcaseFromDB;
	
	public ReportValidator(String filename) {
		this.filename = filename;
		this.readFromDB = new ReportDbLookup();
		this.readClassFromDB = new ClassDbLookup();
		this.readSuiteFromDB = new SuiteDbLookup();
		this.connection = DBCon.getDbInstance().getConnection();
		this.loader = new ReportLoader();
		this.parser = new ReportXMLParser();
		this.suiteInserter = new SuiteInserter();
		this.testCaseRunInserter = new TestCaseRunInserter();
		testCaseInserter = new TestCaseInserter();
		loadReport();
	}
	
	private void loadReport(){
		this.reportFile = loader.getXMLReportByFileName(filename);
		this.report = parser.getSimpleReportFromFile(reportFile);
	}
	
	public boolean reportExists(){
		long timestamp = report.getTimestamp();
		return readFromDB.reportExists(timestamp);
	}
	
	public void saveReport(){
		report.convertToFullReport();
		HashMap<String, Integer> classIDs = getTestClassIDs(report.getPresentTestClasses());
		int suiteID = getSuiteID(report.getSuiteName());
		HashMap<String, Integer> testCases = getTestCases(report, classIDs);
		DriverValidation driverValidation = new DriverValidation(report);
		testCaseRunInserter.insertTestCases(report, suiteID, classIDs, testCases, driverValidation);
	}

	public int getSuiteID(String suiteName){
		int suiteID = readSuiteFromDB.getSuiteID(suiteName);
		if (suiteID < 0) {
			suiteID = insertSuite(suiteName);
		}
		return suiteID;
	}
	
	
	private int insertSuite(String suiteName) {
		boolean success = suiteInserter.insertSuite(suiteName);
		if (success) {
			return readSuiteFromDB.getSuiteID(suiteName);
		}
		return -1;
	}

	/**
	 * Gets a HashMap of the name : id of the testclass array.
	 * @param testClassNames - Testclass names.
	 * @return - hashmap with ids from the database.
	 */
	public HashMap<String, Integer> getTestClassIDs(List<String> testClassNames){
		HashMap<String, Integer> testClassIDs = new HashMap<String,Integer>();
		for (String testClassName : testClassNames) {
			int id = readClassFromDB.getClassID(testClassName);
			if (!(id > 0)) {
				int id_new = insertTestClass(testClassName);
				if (id_new > 0) {
					testClassIDs.put(testClassName, id_new);
				}else {
					return null;
				}
			}
			else testClassIDs.put(testClassName, id);
		}
		return testClassIDs;
	}
	
	/**
	 * Inserts a test class name into the database and returns the id.
	 * @param testClassname - name of the testclass
	 * @return - id of the testclass in the database.
	 */
	private int insertTestClass(String testClassname){
		boolean success = getClassInserter().insertTestClass(testClassname);
		if (success) {
			return readClassFromDB.getClassID(testClassname);
		}
		return -1;
	}
	
	private ClassInserter getClassInserter(){
		if (classInserter == null) {
			return classInserter = new ClassInserter();
		}
		return classInserter;
	}
	
	private HashMap<String,Integer> getTestCases(Report report, HashMap<String, Integer> classIDs){
		TestcaseDbLookup readTestcaseFromDB = new TestcaseDbLookup();
		//get testcases from db.
		HashMap<String, Integer> allFromTestcaseConcat = readTestcaseFromDB.getAllFromTestcaseConcat();
		HashSet<String> addedTestCases = new HashSet<String>();
		//get testcases from report
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		for (ReportTestCase testCase : testCaseArray) {
			Integer classID = classIDs.get(testCase.getClassName());
			String methodName = testCase.getMethodName();
			String searchKey = methodName+classID;
			if (addedTestCases.contains(searchKey)) {
				continue;
			}
			boolean containsKey = allFromTestcaseConcat.containsKey(searchKey);
			if (!containsKey) {
				testCaseInserter.addTestCaseToBatch(methodName, classID);
				addedTestCases.add(searchKey);
			}
			testCaseInserter.executeBatch();
		}
		return readTestcaseFromDB.getAllFromTestcaseConcat();
	}
}
