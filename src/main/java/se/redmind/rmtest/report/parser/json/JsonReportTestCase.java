package se.redmind.rmtest.report.parser.json;

import se.redmind.rmtest.report.parser.ReportTestCase;

import com.google.gson.JsonObject;

public class JsonReportTestCase extends ReportTestCase<JsonObject> {

	public JsonReportTestCase(JsonObject element, String suite_name) {
		super(element, suite_name);
	}

	public JsonReportTestCase() {
		super();
	}

	@Override
	protected String getTestcaseName(JsonObject testcase) {
		return testcase.get("method").getAsString();
	}

	@Override
	protected String getClassname(JsonObject testcase) {
		return testcase.get("testclass").getAsString();
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

}
