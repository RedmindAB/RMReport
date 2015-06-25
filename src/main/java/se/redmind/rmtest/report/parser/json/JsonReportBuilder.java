package se.redmind.rmtest.report.parser.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportTestCase.ResultType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonReportBuilder {
	
	private JsonObject reportJson;

	public JsonReportBuilder(JsonObject reportJson) {
		this.reportJson = reportJson;
	}
	
	public JsonReport build(){
		JsonReport report = new JsonReport();
		//Set up suite variables.
		report.setSuite_name(getSuiteName());
		report.setTests(getTests());
		report.setTimestamp(getTimestamp());
		report.setTime(getTime());
		report.setFailures(getfailures());
		report.setTestCaseArray(getTestcases());
		return report;
	}
	
	private List<ReportTestCase> getTestcases() {
		JsonObject tests = reportJson.get("tests").getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = tests.entrySet();
		List<ReportTestCase> results = new ArrayList<ReportTestCase>();
		for (Entry<String, JsonElement> entry : entrySet) {
			JsonObject test = entry.getValue().getAsJsonObject();
			JsonReportTestCase testCase = new JsonReportTestCase();
			testCase.setTime(test.get("runTime").getAsDouble());
			testCase.setMessage(getTestMessage(test));
			testCase.setName(test.get("method").getAsString());
			testCase.setClassname(test.get("testclass").getAsString());
			testCase.setResultType(getTestResult(test));
			testCase.setDriver(getDriver(test));
			results.add(testCase);
		}
		return results;
	}

	private Driver getDriver(JsonObject test) {
		Driver driver = new Driver();
		JsonObject deviceInfo = test.get("deviceInfo").getAsJsonObject();
		driver.setDevice(deviceInfo.get("device").getAsString());
		driver.setOs(deviceInfo.get("os").getAsString());
		driver.setOsVer(deviceInfo.get("osver").getAsString());
		driver.setBrowser(deviceInfo.get("browser").getAsString());
		driver.setBrowserVer(deviceInfo.get("browserVer").getAsString());
		return driver;
	}

	private ResultType getTestResult(JsonObject test) {
		switch (test.get("result").getAsString()) {
		case "passed":
			return ResultType.PASSED;
		case "failure":
			return ResultType.FAILURE;
		case "error":
			return ResultType.ERROR;
		case "ignored":
			return ResultType.SKIPPED;
		default:
			return ResultType.PASSED;
		}
	}

	private String getTestMessage(JsonObject test) {
		try {
			String message = test.get("message").getAsString();
			return message;
		} catch (Exception e) {
			return "";
		}
	}

	private int getfailures() {
		return reportJson.get("failures").getAsInt();
	}

	private double getTime() {
		return reportJson.get("runTime").getAsDouble();
	}

	private long getTimestamp() {
		return reportJson.get("timestamp").getAsLong();
	}

	private int getTests() {
		return reportJson.get("totalTests").getAsInt();
	}

	private String getSuiteName(){
		String fullSuiteName = reportJson.get("suite").getAsString();
		int start = fullSuiteName.lastIndexOf('.');
		return fullSuiteName.substring(start+1);
	}
	
	
}