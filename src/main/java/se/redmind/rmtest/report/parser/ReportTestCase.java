package se.redmind.rmtest.report.parser;

import java.util.HashMap;

import org.w3c.dom.Element;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ReportTestCase{

	private String name, classname;
	private boolean passed;
	private JsonObject jsonObject;
	
	public ReportTestCase(Element element) {
		this.jsonObject = new JsonObject();
		passed = false;
		generateTestCaseFromElement(element);
	}
	
	private void generateTestCaseFromElement(Element element){
		name = element.getAttribute("name");
		this.jsonObject.add("name", new JsonPrimitive(name));
		
		classname = element.getAttribute("classname");
		this.jsonObject.add("classname", new JsonPrimitive(classname));
		
		Element errorElement = (Element) element.getElementsByTagName("error").item(0);
		
		Element failureElement = (Element) element.getElementsByTagName("failure").item(0);
		if (errorElement != null) {
			String message = errorElement.getTextContent();
			String type = errorElement.getAttribute("type");
			
			JsonObject error = new JsonObject();
			error.add("message", new JsonPrimitive(message));
			error.add("type", new JsonPrimitive(type));
			this.jsonObject.add("error", error);
		}
		else if(failureElement != null){
			String message = failureElement.getTextContent();
			String type = failureElement.getAttribute("type");
			
			JsonObject failureObject = new JsonObject();
			failureObject.add("message", new JsonPrimitive(message));
			failureObject.add("type", new JsonPrimitive(type));
			this.jsonObject.add("failure", failureObject);
		}
		else passed = true;
		
		this.jsonObject.add("passed", new JsonPrimitive(this.passed));
		
		String timeValue = element.getAttribute("time");
		double time = Double.parseDouble(timeValue);
		
		this.jsonObject.add("time", new JsonPrimitive(time));
	}
	
	public String getName() {
		return name;
	}

	public String getClassName() {
		return classname;
	}

	public JsonObject getError() {
		return this.jsonObject.get("error").getAsJsonObject();
	}

	public JsonObject getFailure() {
		return this.jsonObject.get("failure").getAsJsonObject();
	}

	public double getTime() {
		return this.jsonObject.get("time").getAsDouble();
	}

	public boolean isPassed() {
		return this.jsonObject.get("passed").getAsBoolean();
	}
	
	public JsonObject getAsJsonObject(){
		return this.jsonObject;
	}

}
