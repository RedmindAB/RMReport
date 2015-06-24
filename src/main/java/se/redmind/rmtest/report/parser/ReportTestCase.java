package se.redmind.rmtest.report.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;


public abstract class ReportTestCase<E> {
	
	Logger log = LogManager.getLogger(ReportTestCase.class);

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
							
	public static enum ResultType {PASSED, ERROR, FAILURE, SKIPPED};
	private ResultType resultType;
	
	private String name, classname, message, driverName;
	private double time;
	private boolean passed;
	private Driver driverParser;
	private String suite_name;
	private boolean suiteIsTestcase;
	
	public ReportTestCase(E element, String suite_name) {
		passed = false;
		this.suite_name = suite_name;
		generateTestCaseFromElement(element);
	}
	
	protected abstract String getTestcaseName(E testcase);
	
	protected abstract String getClassname(E testcase);
	
	protected abstract ResultType getResult(E testcase);
	
	protected abstract String getErrorMessage(E testcase);
	
	protected abstract double getRunTime(E testcase);
	
	private void generateTestCaseFromElement(E testcase){
		name = getTestcaseName(testcase);
		broken = checkIfBroken();
		if (broken) {
			boolean testCaseNameSameAsSuiteName = isTestCaseNameSameAsSuiteName(name);
			if (testCaseNameSameAsSuiteName) {
				broken = false;
				return;
			}
			log.warn(name+" is a broken testcase");
			return;
		}
		
		String driverName = checkDriverName(name);
		this.driverName = driverName;
		this.driverParser = new Driver(driverName);
		
		
		String elementClassname = getClassname(testcase);
		classname = getTestClassName(elementClassname);
		
		setResult(testcase);
		
		if (this.resultType == ResultType.ERROR) {
			String message = getErrorMessage(testcase);
			message = removeAllIlligalChars(message);
			this.message = message;
		}
		else if(this.resultType == ResultType.FAILURE){
			String message = getErrorMessage(testcase);
			message = removeAllIlligalChars(message);
			this.message = message;
		}
		else if (this.resultType == ResultType.SKIPPED){
			this.message = "";
		}
		else {
			passed = true;
			this.message = "";
			this.resultType = ResultType.PASSED;
		}
		
		
		double time = getRunTime(testcase);
		this.time = time;
	}
	
	private void setResult(E testcase) {
		this.resultType = getResult(testcase);
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
			String res = name.substring(0, end);
			return res;
		}
		return null;
	}
	
	private boolean isTestCaseNameSameAsSuiteName(String methodName){
		suiteIsTestcase = methodName.equals(suite_name);
		return suiteIsTestcase;
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
	
	public boolean isSuiteTestCase(){
		return suiteIsTestcase;
	}
	
}
