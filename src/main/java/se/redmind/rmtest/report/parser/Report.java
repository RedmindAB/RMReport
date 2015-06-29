package se.redmind.rmtest.report.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public abstract class Report<E> {
	
	Logger log = LogManager.getLogger(Report.class);
	
	private E fullReport;
	
	private String name;
	private String suite_name;
	private long timestamp;
	private int tests;
	private int errors;
	private int skipped;
	private int failures;
	private double time;
	private boolean passed;
	private boolean simpleReport;

	private HashSet<String> driverSet;

	private List<String> presentTestClasses;

	private List<ReportTestCase> testCaseArray;
	
	public Report(E fullReport) {
		this.simpleReport = false;
		this.fullReport = fullReport;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
	}
	
 	public Report(E fullReport, boolean simpleReport) {
		this.simpleReport = simpleReport;
		this.fullReport = fullReport;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
	}
 	
 	/**
 	 * This constructor is used if you want to set the report up yourself
 	 */
 	public Report(){}
 	
 	
 	public Report<E> build(){
 		generateReportFromElement(fullReport);
 		return this;
 	}

	/**
	 * 
	 * @param reportElement
	 * @return - the amount of tests in this suite;
	 */
	protected abstract int tests(E fullReport); 
	
	/**
	 * 
	 * @param reportElement
	 * @return - the amount of errors in this suite;
	 */
	protected abstract int errors(E fullReport);
	
	/**
	 * 
	 * @param reportElement
	 * @return - the amount of failures in this suite.
	 */
	protected abstract int failures(E fullReport);
	
	/**
	 * 
	 * @param fullReport
	 * @return - return the total amount of skipped tests in the element.
	 */
	protected abstract int skipped(E fullReport);
	
	/**
	 * In this method u need to extract the timestamp from the report, and make sure that the timestamp have the format YYYYMMDDhhmmss
	 * @param fullReport
	 * @return - the timestamp of the suite.
	 */
	protected abstract long extractTimestamp(E fullReport, String name);
	
	/**
	 * this method should return the suite name without any extra shit around it. so if the suite is "a.package.with.test.SuiteName" you only want to get the "SuiteName"
	 * @param fullReport
	 * @param name
	 * @return - the naked suite name.
	 */
	protected abstract String extractSuiteName(E fullReport, String name);
	
	/**
	 * Gets the name of the 
	 * @param fullReport
	 * @return - 
	 */
	protected abstract String getName(E fullReport);
	
	/**
	 * 
	 * @param element
	 * @return - should return the total time of the testrun.
	 */
	protected abstract double getTime(E element);
	
	/**
	 * Should return an array with test cases only. not the full report.
	 * @param fullReport
	 * @return - array of test cases.
	 */
	protected abstract List<E> getTestCases(E fullReport);
	
	/**
	 * 
	 * @param testcase
	 * @return - Extract and build the testcase here.
	 */
	protected abstract ReportTestCase<?> extractTestCase(E testcase);
	
	
	/**
	 * 
	 * @param fullReport - the full representation of the report.
	 * @param name - the name of the report
	 * @return - the package name of the report.
	 */
	protected abstract String extractSuitePackage(E fullReport, String name);
	
	@SuppressWarnings("rawtypes")
	private boolean generateReportFromElement(E fullReport) {
		String name = getName(fullReport);
		this.name = name;
		this.suite_name = extractSuiteName(fullReport, name);
		this.timestamp = extractTimestamp(fullReport, name);
		
		this.tests = tests(fullReport);
		this.errors = errors(fullReport);
		this.skipped = skipped(fullReport);
		this.failures = failures(fullReport);
		this.time = getTime(fullReport);
		this.passed = isTestPassed(errors, failures);

		if (!simpleReport) {
			List<E> testCaseNodes = getTestCases(fullReport);
			driverSet = new HashSet<String>();
			int i = 0;
			for (E e : testCaseNodes) {
				ReportTestCase test = extractTestCase(e);
				if (test.isBroken()) {
					log.warn("Broken testcase: "+i+" case text: "+test.getMethodName()+test.getDriverName());
					return false;
				}
				if (test.isSuiteTestCase()) continue;
				testCaseArray.add(test);
				String driver = test.getDriverName();
				if (!driverSet.contains(driver)) {
					driverSet.add(driver);
				}
				String testClass = test.getClassName();
				if (!getPresentTestClasses().contains(testClass)) {
					getPresentTestClasses().add(testClass);
				}
				i++;
			}
			JsonArray drivers = new JsonArray();
			for (String driver : driverSet) {
				drivers.add(new JsonPrimitive(driver));
			}
		}
		return true;
	}
	
	public boolean isTestPassed(int errors, int failures){
		return errors == 0 && failures == 0;
	}
	
	public List<String> getPresentTestClasses() {
		return presentTestClasses;
	}
	
	public List<ReportTestCase> getTestCaseArray(){
		return testCaseArray;
	}
	
	public boolean isSimpleReport(){
		return simpleReport;
	}

	public double getTime() {
		return this.time;
	}

	public String getName() {
		return this.name;
	}
	
	public String getSuiteName() {
		return this.suite_name;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}

	public int getTests() {
		return this.tests;
	}

	public int getErrors() {
		return this.errors;
	}

	public int getSkipped() {
		return this.skipped;
	}

	public int getFailures() {
		return this.failures;
	}

	public HashSet<String> getDrivers(){
		return this.driverSet;
	}
	
	public boolean convertToFullReport(){
		if (simpleReport) {
			simpleReport = false;
			return generateReportFromElement(this.fullReport);
		}
		return true;
	}

	public String getSuite_name() {
		return suite_name;
	}

	public void setSuite_name(String suite_name) {
		this.suite_name = suite_name;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setTests(int tests) {
		this.tests = tests;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public void setFailures(int failures) {
		this.failures = failures;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public void setTestCaseArray(List<ReportTestCase> testCaseArray) {
		this.testCaseArray = testCaseArray;
	}
	
}
