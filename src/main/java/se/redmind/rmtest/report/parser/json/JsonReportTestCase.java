package se.redmind.rmtest.report.parser.json;

import com.google.gson.JsonObject;

import se.redmind.rmtest.report.parser.ReportTestCase;

public class JsonReportTestCase extends ReportTestCase<JsonObject> {

	private String methodName;

	public JsonReportTestCase(JsonObject element, String suite_name) {
		super(element, suite_name);
	}

	public JsonReportTestCase() {
		super();
	}

	@Override
	protected String getTestcaseName(JsonObject testcase) {
		System.out.println(testcase);
		methodName = testcase.get("method").getAsString();
		return methodName;
	}

	@Override
	protected String getClassname(JsonObject testcase) {
		String classname = testcase.get("testclass").getAsString();
		System.out.println(classname);
		return classname;
	}

	@Override
	protected ResultType getResult(JsonObject testcase) {
		String result = testcase.get("result").getAsString();
		switch (result) {
		case "passed":
			return ResultType.PASSED;
		case "error":
			return ResultType.ERROR;
		case "failure":
			return ResultType.FAILURE;
		case "skipped":
			return ResultType.SKIPPED;
		default:
			return ResultType.PASSED;
		}
	}

	@Override
	protected String getErrorMessage(JsonObject testcase) {
		return testcase.get("message").getAsString();
	}

	@Override
	protected double getRunTime(JsonObject testcase) {
		return 0;
	}

	@Override
	public String getMethodName() {
		return getName();
	}
	
}
