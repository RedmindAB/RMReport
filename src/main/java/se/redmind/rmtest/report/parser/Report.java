package se.redmind.rmtest.report.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Report {

	private String name;
	private int tests, errors, skipped, failures;
	private double time;
	private HashMap<String, String> properties;
	private ArrayList<ReportTestCase> testCases;
	private boolean simpleReport;
	private Element file;

	public Report(Element element) {
		this.testCases = new ArrayList<>();
		this.properties = new HashMap<String,String>();
		this.simpleReport = false;
		this.file = element;
		generateReportFromElement(element);
	}
	
	public Report(Element element, boolean simpleReport) {
		this.testCases = new ArrayList<>();
		this.properties = new HashMap<String,String>();
		this.simpleReport = simpleReport;
		this.file = element;
		generateReportFromElement(element);
	}

	private void generateReportFromElement(Element element) {
		name = element.getAttribute("name");
		String testString = element.getAttribute("tests");
		tests = Integer.valueOf(testString);

		String errorString = element.getAttribute("errors");
		errors = Integer.valueOf(errorString);

		String skippString = element.getAttribute("skipped");
		skipped = Integer.valueOf(skippString);

		String failString = element.getAttribute("failures");
		failures = Integer.valueOf(failString);

		String timeString = element.getAttribute("time");
		time = Double.valueOf(timeString);

		if (!simpleReport) {
			NodeList propertyNodes = element.getElementsByTagName("property");
			for (int i = 0; i < propertyNodes.getLength(); i++) {
				Element property = (Element) propertyNodes.item(i);
				properties.put(property.getAttribute("name"),
						property.getAttribute("value"));
			}

			NodeList testCaseNodes = element.getElementsByTagName("testcase");
			for (int i = 0; i < testCaseNodes.getLength(); i++) {
				Element testCase = (Element) testCaseNodes.item(i);
				testCases.add(new ReportTestCase(element));
			}
		}
	}
	
	public void convertToFullReport(){
		if (simpleReport) {
			simpleReport = !simpleReport;
			generateReportFromElement(this.file);
		}
	}

	public double getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	public int getTests() {
		return tests;
	}

	public int getErrors() {
		return errors;
	}

	public int getSkipped() {
		return skipped;
	}

	public int getFailures() {
		return failures;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public ArrayList<ReportTestCase> getTestCases() {
		return testCases;
	}

}
