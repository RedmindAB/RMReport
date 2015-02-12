package se.redmind.rmtest.report.parser;

import org.w3c.dom.Element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ReportTestCase{

	public static final String 	
	NAME = "name",
	DRIVER_NAME = "driverName",
	CLASSNAME = "className",
	ERROR = "error",
	FAILURE = "failure",
	TYPE = "type",
	MESSAGE = "message",
	PASSED = "passed",
	TIME = "time";
	
								
	
	private String name, classname;
	private boolean passed;
	private JsonObject jsonObject;
	private Driver driverParser;
	
	public ReportTestCase(Element element) {
		this.jsonObject = new JsonObject();
		passed = false;
		generateTestCaseFromElement(element);
	}
	
	private void generateTestCaseFromElement(Element element){
		name = element.getAttribute(NAME);
		this.jsonObject.add(NAME, new JsonPrimitive(name));
		
		String driverName = checkDriverName(name);
		this.driverParser = new Driver(driverName);
		this.jsonObject.add("driverName", new JsonPrimitive(driverName));
		
		
		classname = element.getAttribute("classname");
		classname = getTestClassName(classname);
		this.jsonObject.add(CLASSNAME, new JsonPrimitive(classname));
		
		Element errorElement = (Element) element.getElementsByTagName(ERROR).item(0);
		
		Element failureElement = (Element) element.getElementsByTagName(FAILURE).item(0);
		if (errorElement != null) {
			String message = errorElement.getTextContent();
			String type = errorElement.getAttribute(TYPE);
			
			JsonObject error = new JsonObject();
			error.add(MESSAGE, new JsonPrimitive(message));
			error.add(TYPE, new JsonPrimitive(type));
			this.jsonObject.add(ERROR, error);
		}
		else if(failureElement != null){
			String message = failureElement.getTextContent();
			String type = failureElement.getAttribute(TYPE);
			
			JsonObject failureObject = new JsonObject();
			failureObject.add(MESSAGE, new JsonPrimitive(message));
			failureObject.add(TYPE, new JsonPrimitive(type));
			this.jsonObject.add(FAILURE, failureObject);
		}
		else passed = true;
		
		this.jsonObject.add(PASSED, new JsonPrimitive(this.passed));
		
		String timeValue = element.getAttribute(TIME);
		double time = Double.parseDouble(timeValue);
		
		this.jsonObject.add(TIME, new JsonPrimitive(time));
	}
	
	private String getTestClassName(String testCaseName) {
		int end = testCaseName.indexOf("(");
		if (end > 0) {
			return testCaseName.substring(0, end);
		}
		return testCaseName;
	}

	public String checkDriverName(String name){
		if (name.contains("[")) {
		int start = name.lastIndexOf("[");
		int end = name.lastIndexOf("]");
			return name.substring(start+1, end);
		}
		return name;
	}
	
	public String getMethodName(){
		int end = name.indexOf("[");
		if (end > 0) {
			return name.substring(0, end);
		}
		return "No method name found";
	}
	
	//TODO: Not returning skipped, skipped is not supported yet.
	public String getResult(){
		if (isPassed()) 				return PASSED;
		else if(getFailure() != null) 	return FAILURE;
		else							return ERROR;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDriverName(){
		return this.jsonObject.get(DRIVER_NAME).getAsString();
	}
	
	public JsonElement getDriverNameAsJson(){
		return this.jsonObject.get(DRIVER_NAME);
	}

	public String getClassName() {
		return classname;
	}

	public JsonObject getError() {
		return this.jsonObject.get(ERROR).getAsJsonObject();
	}
	
	public String getErrorMessage(){
		Object error = this.jsonObject.get(ERROR);
		if (error != null) {
			return this.jsonObject.get(ERROR).getAsJsonObject().get(MESSAGE).getAsString();
		}
		return "";
	}

	public JsonObject getFailure() {
		Object fail = this.jsonObject.get(FAILURE);
		if (fail != null) {
			return (JsonObject) fail;
		}
		return null;
	}
	
	public String getFailureMessage(){
		Object error = this.jsonObject.get(FAILURE);
		if (error != null) {
			return this.jsonObject.get(FAILURE).getAsJsonObject().get(MESSAGE).getAsString();
		}
		return "";
	}

	public double getTime() {
		return this.jsonObject.get(TIME).getAsDouble();
	}

	public boolean isPassed() {
		return this.jsonObject.get(PASSED).getAsBoolean();
	}
	
	public JsonObject getAsJsonObject(){
		return this.jsonObject;
	}
	
	public Driver getDriver(){
		return this.driverParser;
	}
	
	//TODO: Add support for skipped?
	public String getMessage() {
		String result = getResult();
		switch (result) {
		case PASSED:
			return "";
		case ERROR:
			return getErrorMessage();
		case FAILURE:
			return getFailureMessage();
		default:
			return "";
		}
	}
	
}
