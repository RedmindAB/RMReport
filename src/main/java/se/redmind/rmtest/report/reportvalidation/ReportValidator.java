package se.redmind.rmtest.report.reportvalidation;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.create.classinserter.ClassInserter;
import se.redmind.rmtest.db.create.suiteinserter.SuiteInserter;
import se.redmind.rmtest.db.create.testcaseinserter.TestCaseInserter;
import se.redmind.rmtest.db.create.testcaseruninserter.TestCaseRunInserter;
import se.redmind.rmtest.db.read.ReadClassFromDB;
import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.read.ReadSuiteFromDB;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportValidator {
	
	private ReadReportFromDB readFromDB;
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
	private ReadClassFromDB readClassFromDB;
	private ReadSuiteFromDB readSuiteFromDB;
	private ReadTestcaseFromDB readTestcaseFromDB;
	
	public ReportValidator(String filename) {
		this.filename = filename;
		this.readFromDB = new ReadReportFromDB();
		this.readClassFromDB = new ReadClassFromDB();
		this.readSuiteFromDB = new ReadSuiteFromDB();
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
		String timestamp = report.getTimestamp();
		return readFromDB.reportExists(timestamp);
	}
	
	public void saveReport(){
		report.convertToFullReport();
		try {
			HashMap<String, Integer> classIDs = getTestClassIDs(report.getPresentTestClasses());
			System.out.println(classIDs);
			int suiteID = getSuiteID(report.getSuiteName());
			HashMap<String, Integer> testCases = getTestCases(report, classIDs);
			DriverValidation driverValidation = new DriverValidation(report);
			connection.setAutoCommit(false);
			testCaseRunInserter.insertTestCases(report, suiteID, classIDs, testCases, driverValidation);
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println(testClassName);
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
		ReadTestcaseFromDB readTestcaseFromDB = new ReadTestcaseFromDB();
		//get testcases from db.
		HashMap<String, Integer> allFromTestcaseConcat = readTestcaseFromDB.getAllFromTestcaseConcat();
		HashSet<String> addedTestCases = new HashSet<String>();
		//get testcases from report
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		System.out.println(classIDs);
		for (ReportTestCase testCase : testCaseArray) {
			System.out.println(testCase.getClassName());
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
