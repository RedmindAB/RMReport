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
	
	public Report(Element element) {
		generateReportFromElement(element);
	}
	
	private void generateReportFromElement(Element element){
		name = element.getAttribute("name");
		System.out.println(name);
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
		
		properties = new HashMap<String, String>();
		NodeList propertyNodes = element.getElementsByTagName("property");
		
		for (int i = 0; i < propertyNodes.getLength(); i++) {
			Element property = (Element) propertyNodes.item(i);
			properties.put(property.getAttribute("name"), property.getAttribute("value"));
		}
		
		NodeList testCaseNodes = element.getElementsByTagName("testcase");
		testCases = new ArrayList<ReportTestCase>();
		for (int i = 0; i < testCaseNodes.getLength(); i++) {
			Element testCase = (Element) testCaseNodes.item(i);
			testCases.add(new ReportTestCase(element));
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
	
	public ArrayList<ReportTestCase> getTestCases(){
		return testCases;
	}
	
}
