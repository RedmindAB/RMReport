package se.redmind.rmtest.report.reportvalidation;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.create.classinserter.ClassInserter;
import se.redmind.rmtest.db.create.parameterinserter.ParameterInserter;
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
import se.redmind.rmtest.report.parser.json.JsonReport;
import se.redmind.rmtest.report.parser.json.JsonReportBuilder;
import se.redmind.rmtest.report.parser.xml.XMLReport;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportValidator {
	
	Logger log = LogManager.getLogger(ReportValidator.class);
	
	private String reportPath;
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
	private boolean validFilename;
	
	public ReportValidator(String filename, String path) {
		this.filename = filename;
		this.readFromDB = new ReportDbLookup();
		this.readClassFromDB = new ClassDbLookup();
		this.readSuiteFromDB = new SuiteDbLookup();
		this.connection = DBCon.getDbInstance().getConnection();
		this.loader = new ReportLoader(path, false);
		this.parser = new ReportXMLParser();
		this.suiteInserter = new SuiteInserter();
		this.testCaseRunInserter = new TestCaseRunInserter();
		testCaseInserter = new TestCaseInserter();
		this.reportPath = path;
		loadReport();
	}
	
	public ReportValidator(File file, ReportLoader loader) {
		this.filename = file.getName();
		this.readFromDB = new ReportDbLookup();
		this.readClassFromDB = new ClassDbLookup();
		this.readSuiteFromDB = new SuiteDbLookup();
		this.connection = DBCon.getDbInstance().getConnection();
		this.loader = loader;
		this.parser = new ReportXMLParser();
		this.suiteInserter = new SuiteInserter();
		this.testCaseRunInserter = new TestCaseRunInserter();
		testCaseInserter = new TestCaseInserter();
		this.reportPath = loader.getReportFolderPath();
		loadReport();
	}
	
	private void loadReport(){
		this.reportFile = loader.getReportByFileName(filename);
		if (this.reportFile.getAbsolutePath().endsWith("xml")){
			this.report = (XMLReport) parser.getSimpleReportFromFile(reportFile).build();
		}
		else if (this.reportFile.getAbsolutePath().endsWith("json")){
			this.report = (JsonReport) new JsonReportBuilder(this.reportFile).build();
		}
	}
	
	public boolean reportExists(){
		long timestamp = report.getTimestamp();
		return readFromDB.reportExists(timestamp);
	}
	
	public boolean saveReport(){
		if (!isValidFilename()) {
			log.warn(reportFile.getName()+" is not a valid report file");
			return false;
		}
		boolean convertToFullReport = report.convertToFullReport();
		if (!convertToFullReport) {
			log.warn(reportFile.getName()+" was not able to convert to a full report");
			return false;
		}
		HashMap<String, Integer> classIDs = getTestClassIDs(report.getPresentTestClasses());
		int suiteID = getSuiteID(report.getSuiteName());
		HashMap<String, Integer> testCases = getTestCases(report, classIDs);
		DriverValidation driverValidation = new DriverValidation(report);
		ParameterInserter parameterInserter = new ParameterInserter();
		parameterInserter.insertParameters(suiteID, report.getTimestamp(), report.getParameters());
		return testCaseRunInserter.insertTestCases(report, suiteID, classIDs, testCases, driverValidation);
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
			Integer classID = classIDs.get(testCase.getClassname());
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
	
	/**
	 * validates if the filename matches RM report standards.
	 * @return - boolean
	 */
	public boolean isValidFilename(){
		return isValidFilename(filename);
	}
	
	/**
	 * validates if the String matches RM report standards.
	 * @param filename - name of the file that should be validated.
	 * @return - boolean
	 */
	public boolean isValidFilename(String filename){
		return filename.matches("(TEST-)+([.a-zA-Z])+(-)+([0-9]{8}-[0-9]{6})+(.xml)") || filename.endsWith(".json");
	}
	
	public File getReportFile(){
		return this.reportFile;
	}
	
	public Report getReport(){
		return report;
	}
	
	public ReportLoader getReportLoader(){
		return this.loader;
	}
}
