package se.redmind.rmtest.report.parser;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Report{
	
	private static final String TESTCASES = "testcases";

	private JsonObject jsonObject;
	private Element file;
	private boolean simpleReport;

	public Report(Element element) {
		this.jsonObject = new JsonObject();
		this.jsonObject.add("simpleReport", new JsonPrimitive(false));
		this.simpleReport = false;
		this.file = element;
		generateReportFromElement(element);
	}
	
	public Report(Element element, boolean simpleReport) {
		this.jsonObject = new JsonObject();
		this.jsonObject.add("simpleReport", new JsonPrimitive(simpleReport));
		this.simpleReport = simpleReport;
		this.file = element;
		generateReportFromElement(element);
	}

	private void generateReportFromElement(Element element) {
		String name = element.getAttribute("name");
		this.jsonObject.add("name", new JsonPrimitive(name));
		
		this.jsonObject.add("suiteName", new JsonPrimitive(getSuiteName(name)));
		this.jsonObject.add("timestamp", new JsonPrimitive(getTimestamp(name)));
		
		String testString = element.getAttribute("tests");
		int tests = Integer.valueOf(testString);
		this.jsonObject.add("tests", new JsonPrimitive(tests));

		String errorString = element.getAttribute("errors");
		int errors = Integer.valueOf(errorString);
		this.jsonObject.add("errors", new JsonPrimitive(errors));

		String skippString = element.getAttribute("skipped");
		int skipped = Integer.valueOf(skippString);
		this.jsonObject.add("skipped", new JsonPrimitive(skipped));

		String failString = element.getAttribute("failures");
		int failures = Integer.valueOf(failString);
		this.jsonObject.add("failures", new JsonPrimitive(failures));

		String timeString = element.getAttribute("time");
		double time = Double.valueOf(timeString);
		this.jsonObject.add("time", new JsonPrimitive(time));
		

		if (!simpleReport) {
			NodeList propertyNodes = element.getElementsByTagName("property");
			JsonObject properties = new JsonObject();
			for (int i = 0; i < propertyNodes.getLength(); i++) {
				Element property = (Element) propertyNodes.item(i);
				JsonObject propertyObject = new JsonObject();
				String propKey = removePunctuations(property.getAttribute("name"), "");
				
				String propName = removePunctuations(property.getAttribute("name"), " ");
				String propertyVal = property.getAttribute("value");
				propertyObject.add("readName", new JsonPrimitive(propName));
				propertyObject.add("value", new JsonPrimitive(propertyVal));
				properties.add(propKey, propertyObject);
			}
			this.jsonObject.add("properties", properties);
			
			NodeList testCaseNodes = element.getElementsByTagName("testcase");
			JsonArray testCases = new JsonArray();
			for (int i = 0; i < testCaseNodes.getLength(); i++) {
				Element testCase = (Element) testCaseNodes.item(i);
				testCases.add(new ReportTestCase(testCase).getAsJsonObject());
			}
			this.jsonObject.add(TESTCASES, testCases);
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
	
	public String getSuiteName(String name){
		int start = name.lastIndexOf(".");
		int end = name.lastIndexOf("(");
		return name.substring(start+1, end-1);
	}
	
	public String getTimestamp(String name){
		int start = name.lastIndexOf("(");
		int end = name.lastIndexOf(")");
		return name.substring(start+1, end-1);
	}

	public double getTime() {
		return this.jsonObject.get("time").getAsDouble();
	}

	public String getName() {
		return this.jsonObject.get("name").getAsString();
	}

	public int getTests() {
		return this.jsonObject.get("tests").getAsInt();
	}

	public int getErrors() {
		return this.jsonObject.get("errors").getAsInt();
	}

	public int getSkipped() {
		return this.jsonObject.get("skipped").getAsInt();
	}

	public int getFailures() {
		return this.jsonObject.get("failures").getAsInt();
	}

	public JsonObject getProperties() {
		if (this.jsonObject.get("properties") == null) return null;
		return this.jsonObject.get("properties").getAsJsonObject();
	}

	public JsonArray getTestCases() {
		if (this.jsonObject.get(TESTCASES) == null) return null;
		return this.jsonObject.get(TESTCASES).getAsJsonArray();
	}
	
	public JsonObject getAsJsonObject(){
		return this.jsonObject;
	}

}
