package se.redmind.rmtest.report.parser;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Report{
	
	private String 
	TESTCASES = "testcases",
	SIMPLE_REPORT = "simpleReport",
	NAME = "name",
	TESTS = "tests",
	SUITE_NAME = "suiteName",
	ERRORS = "errors",
	SKIPPED = "skipped",
	FAILURES = "failures",
	TIME = "time",
	PASSED = "passed",
	PROPERTY = "property",
	PROPERTIES = "properties",
	READ_NAME = "readName",
	TESTCASE = "testcase",
	DRIVERS = "drivers",
	VALUE = "value";

	private int tests, errors, skipped, failures;
	private double time;
	private long timestamp;
	private String name, suite_name;
//	private JsonObject jsonObject;
	private Element file;
	private boolean simpleReport;
	private List<ReportTestCase> testCaseArray;
	private List<String> presentTestClasses;

	private boolean passed;

	private HashSet<String> driverSet;

	public Report(Element element) {
		this.simpleReport = false;
		this.file = element;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
		generateReportFromElement(element);
	}
	
	public Report(Element element, boolean simpleReport) {
		this.simpleReport = simpleReport;
		this.file = element;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
		generateReportFromElement(element);
	}

	private void generateReportFromElement(Element element) {
		String name = element.getAttribute(NAME);
		this.name = name;
		this.suite_name = extractSuiteName(name);
		this.timestamp = extractTimestamp(name);
		
		String testString = element.getAttribute(TESTS);
		int tests = Integer.valueOf(testString);
		this.tests = tests;

		String errorString = element.getAttribute(ERRORS);
		int errors = Integer.valueOf(errorString);
		this.errors = errors;

		String skippString = element.getAttribute(SKIPPED);
		int skipped = Integer.valueOf(skippString);
		this.skipped = skipped;
		
		String failString = element.getAttribute(FAILURES);
		int failures = Integer.valueOf(failString);
		this.failures = failures;
		
		String timeString = element.getAttribute(TIME);
		double time = Double.valueOf(timeString);
		this.time = time;
		
		passed = isTestPassed(errors, failures);

		if (!simpleReport) {
			NodeList testCaseNodes = element.getElementsByTagName(TESTCASE);
			driverSet = new HashSet<String>();
			for (int i = 0; i < testCaseNodes.getLength(); i++) {
				Element testCase = (Element) testCaseNodes.item(i);
				ReportTestCase test = new ReportTestCase(testCase);
				if (test.isBroken()) {
					continue;
				}
				testCaseArray.add(test);
				String driver = test.getDriverName();
				if (!driverSet.contains(driver)) {
					driverSet.add(driver);
				}
				String testClass = test.getClassName();
				if (!getPresentTestClasses().contains(testClass)) {
					getPresentTestClasses().add(testClass);
				}
			}
			JsonArray drivers = new JsonArray();
			for (String driver : driverSet) {
				drivers.add(new JsonPrimitive(driver));
			}
		}
	}
	
	public void convertToFullReport(){
		if (simpleReport) {
			simpleReport = false;
			generateReportFromElement(this.file);
		}
	}
	
	public String removePunctuations(String string, String replacement){
		return string.replace(".", replacement);
		
	}
	
	public String extractSuiteName(String name){
		int start = name.lastIndexOf(".");
		int end = name.lastIndexOf("(");
		return name.substring(start+1, end);
	}
	
	public long extractTimestamp(String name){
		int start = name.lastIndexOf("(");
		int end = name.lastIndexOf(")");
		String timestampString = name.substring(start+1, end);
		long timestamp = Long.valueOf(timestampString.replaceAll("-", ""));
		return timestamp;
	}
	
	public boolean isTestPassed(int errors, int failures){
		return errors == 0 && failures == 0;
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
	
	public List<ReportTestCase> getTestCaseArray(){
		return testCaseArray;
	}
	
	public List<String> getPresentTestClasses() {
		return presentTestClasses;
	}

}
