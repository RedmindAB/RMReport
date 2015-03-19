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
	SKIPPED = "skipped",
	TYPE = "type",
	MESSAGE = "message",
	PASSED = "passed",
	TIME = "time";
	
	private boolean broken;
							
	private enum ResultType {PASSED, ERROR, FAILURE, SKIPPED};
	private ResultType resultType;
	
	private String name, classname, message, driverName;
	private double time;
	private boolean passed;
	private Driver driverParser;
	
	public ReportTestCase(Element element) {
		passed = false;
		generateTestCaseFromElement(element);
	}
	
	private void generateTestCaseFromElement(Element element){
		name = element.getAttribute(NAME);
		broken = checkIfBroken();
		if (broken) {
			return;
		}
		
		String driverName = checkDriverName(name);
		this.driverName = driverName;
		this.driverParser = new Driver(driverName);
		
		
		String elementClassname = element.getAttribute("classname");
		classname = getTestClassName(elementClassname);
		
		Element errorElement = (Element) element.getElementsByTagName(ERROR).item(0);
		
		Element failureElement = (Element) element.getElementsByTagName(FAILURE).item(0);
		Element skippedElement = (Element) element.getElementsByTagName(SKIPPED).item(0);
		if (errorElement != null) {
			String message = errorElement.getTextContent();
			String type = errorElement.getAttribute(TYPE);
			message = removeAllIlligalChars(message);
			this.message = message;
			this.resultType = ResultType.ERROR;
		}
		else if(failureElement != null){
			String message = failureElement.getTextContent();
			String type = failureElement.getAttribute(TYPE);
			message = removeAllIlligalChars(message);
			this.message = message;
			this.resultType = ResultType.FAILURE;
		}
		else if (skippedElement != null){
			this.resultType = ResultType.SKIPPED;
		}
		else {
			passed = true;
			this.message = "";
			this.resultType = ResultType.PASSED;
		}
		
		
		String timeValue = element.getAttribute(TIME);
		double time = Double.parseDouble(timeValue);
		this.time = time;
	}

	private boolean checkIfBroken() {
		return getMethodName() == null;
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
		return null;
	}
	
	public String getResult(){
		String res;
		switch (resultType) {
		case PASSED:
			res = PASSED;
			break;
		case ERROR:
			res = ERROR;
			break;
		case FAILURE:
			res = FAILURE;
			break;
		case SKIPPED:
			res = SKIPPED;
			break;
		default:
			res = "";
			break;
		}
		return res;
	}
	
	public String getDriverName(){
		return this.driverName;
	}

	public String getName() {
		return name;
	}
	

	public String getClassName() {
		return classname;
	}


	public double getTime() {
		return this.time;
	}

	public boolean isPassed() {
		return this.passed;
	}
	
	public Driver getDriver(){
		return this.driverParser;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String removeAllIlligalChars(String message){
		return message.replaceAll("[^\\s\\w\\.\\:]*", "");
	}
	
	public boolean isBroken(){
		return this.broken;
	}
}
