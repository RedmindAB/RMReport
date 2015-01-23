package se.redmind.rmtest.report.parser;

import java.util.HashMap;

import org.w3c.dom.Element;

public class ReportTestCase {

	private String name, classname;
	private HashMap<String, String> error;
	private HashMap<String, String> faliure;
	private double time;
	private boolean passed;
	
	public ReportTestCase(Element element) {
		passed = false;
		generateTestCaseFromElement(element);
	}
	
	private void generateTestCaseFromElement(Element element){
		name = element.getAttribute("name");
		error = new HashMap<String, String>();
		faliure = new HashMap<String, String>();
		classname = element.getAttribute("classname");
		Element errorElement = (Element) element.getElementsByTagName("error").item(0);
		Element faliureElement = (Element) element.getElementsByTagName("failure").item(0);
		if (errorElement != null) {
			error.put("message", errorElement.getTextContent());
			error.put("type", errorElement.getAttribute("type"));
		}
		else if(faliureElement != null){
			faliure.put("message", faliureElement.getTextContent());
			faliure.put("type", faliureElement.getAttribute("type"));
		}
		else passed = true;
		
		String timeValue = element.getAttribute("time");
		time = Double.valueOf(timeValue);
	}
	
	public String getName() {
		return name;
	}

	public String getClassName() {
		return classname;
	}

	public HashMap<String, String> getError() {
		return error;
	}

	public HashMap<String, String> getFaliure() {
		return faliure;
	}

	public double getTime() {
		return time;
	}

	public boolean isPassed() {
		return passed;
	}

}
