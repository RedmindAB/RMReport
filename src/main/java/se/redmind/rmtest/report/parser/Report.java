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
	
	public static final String 
	TESTCASES = "testcases",
	SIMPLE_REPORT = "simpleReport",
	NAME = "name",
	SUITE_NAME = "suiteName",
	TIMESTAMP = "timestamp",
	TESTS = "tests",
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

	private JsonObject jsonObject;
	private Element file;
	private boolean simpleReport;
	private List<ReportTestCase> testCaseArray;
	private List<String> presentTestClasses;

	public Report(Element element) {
		this.jsonObject = new JsonObject();
		this.jsonObject.add(SIMPLE_REPORT, new JsonPrimitive(false));
		this.simpleReport = false;
		this.file = element;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
		generateReportFromElement(element);
	}
	
	public Report(Element element, boolean simpleReport) {
		this.jsonObject = new JsonObject();
		this.jsonObject.add(SIMPLE_REPORT, new JsonPrimitive(simpleReport));
		this.simpleReport = simpleReport;
		this.file = element;
		this.testCaseArray = new ArrayList<ReportTestCase>();
		this.presentTestClasses = new ArrayList<String>();
		generateReportFromElement(element);
	}

	private void generateReportFromElement(Element element) {
		String name = element.getAttribute(NAME);
		this.jsonObject.add(NAME, new JsonPrimitive(name));
		
		this.jsonObject.add(SUITE_NAME, new JsonPrimitive(extractSuiteName(name)));
		this.jsonObject.add(TIMESTAMP, new JsonPrimitive(extractTimestamp(name)));
		
		String testString = element.getAttribute(TESTS);
		int tests = Integer.valueOf(testString);
		this.jsonObject.add(TESTS, new JsonPrimitive(tests));

		String errorString = element.getAttribute(ERRORS);
		int errors = Integer.valueOf(errorString);
		this.jsonObject.add(ERRORS, new JsonPrimitive(errors));

		String skippString = element.getAttribute(SKIPPED);
		int skipped = Integer.valueOf(skippString);
		this.jsonObject.add(SKIPPED, new JsonPrimitive(skipped));

		String failString = element.getAttribute(FAILURES);
		int failures = Integer.valueOf(failString);
		this.jsonObject.add(FAILURES, new JsonPrimitive(failures));

		String timeString = element.getAttribute(TIME);
		double time = Double.valueOf(timeString);
		this.jsonObject.add(TIME, new JsonPrimitive(time));
		
		this.jsonObject.add(PASSED, new JsonPrimitive(isTestPassed(errors, failures)));
		

		if (!simpleReport) {
			NodeList testCaseNodes = element.getElementsByTagName(TESTCASE);
			JsonArray testCases = new JsonArray();
			HashSet<String> driverSet = new HashSet<String>();
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
				testCases.add(test.getAsJsonObject());
			}
			JsonArray drivers = new JsonArray();
			for (String driver : driverSet) {
				drivers.add(new JsonPrimitive(driver));
			}
			this.jsonObject.add(DRIVERS, drivers);
			this.jsonObject.add(TESTCASES, testCases);
			
			NodeList propertyNodes = element.getElementsByTagName(PROPERTY);
			JsonObject properties = new JsonObject();
			for (int i = 0; i < propertyNodes.getLength(); i++) {
				Element property = (Element) propertyNodes.item(i);
				JsonObject propertyObject = new JsonObject();
				String propKey = removePunctuations(property.getAttribute(NAME), "");
				
				String propName = removePunctuations(property.getAttribute(NAME), " ");
				String propertyVal = property.getAttribute(VALUE);
				propertyObject.add(READ_NAME, new JsonPrimitive(propName));
				propertyObject.add(VALUE, new JsonPrimitive(propertyVal));
				properties.add(propKey, propertyObject);
			}
			this.jsonObject.add(PROPERTIES, properties);
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
	
	public String extractTimestamp(String name){
		int start = name.lastIndexOf("(");
		int end = name.lastIndexOf(")");
		return name.substring(start+1, end);
	}
	
	public boolean isTestPassed(int errors, int failures){
		return errors == 0 && failures == 0;
	}

	public double getTime() {
		return this.jsonObject.get(TIME).getAsDouble();
	}

	public String getName() {
		return this.jsonObject.get(NAME).getAsString();
	}
	
	public String getSuiteName() {
		return this.jsonObject.get(SUITE_NAME).getAsString();
	}
	
	public String getTimestamp() {
		return this.jsonObject.get(TIMESTAMP).getAsString();
	}

	public int getTests() {
		return this.jsonObject.get(TESTS).getAsInt();
	}

	public int getErrors() {
		return this.jsonObject.get(ERRORS).getAsInt();
	}

	public int getSkipped() {
		return this.jsonObject.get(SKIPPED).getAsInt();
	}

	public int getFailures() {
		return this.jsonObject.get(FAILURES).getAsInt();
	}

	public JsonObject getProperties() {
		if (this.jsonObject.get(PROPERTIES) == null) return null;
		return this.jsonObject.get(PROPERTIES).getAsJsonObject();
	}

	public JsonArray getTestCases() {
		if (this.jsonObject.get(TESTCASES) == null) return null;
		return this.jsonObject.get(TESTCASES).getAsJsonArray();
	}
	
	public JsonArray getDrivers(){
		return this.jsonObject.get(DRIVERS).getAsJsonArray();
	}
	
	public List<ReportTestCase> getTestCaseArray(){
		return testCaseArray;
	}
	
	public JsonObject getAsJsonObject(){
		return this.jsonObject;
	}

	public List<String> getPresentTestClasses() {
		return presentTestClasses;
	}

}
