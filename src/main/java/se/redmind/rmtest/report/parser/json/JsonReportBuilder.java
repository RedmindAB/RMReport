package se.redmind.rmtest.report.parser.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportTestCase.ResultType;

public class JsonReportBuilder {
	
	private JsonObject reportJson;

	public JsonReportBuilder(JsonObject reportJson) {
		this.reportJson = reportJson;
	}
	
	public JsonReportBuilder(File reportFile) {
		this.reportJson = getReportJsonFromFile(reportFile);
	}

	private JsonObject getReportJsonFromFile(File reportFile) {
		try {
			String reportString = new String(Files.readAllBytes(reportFile.toPath()));
			return new Gson().fromJson(reportString, JsonObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
		report.setParameters(getParameter());
		return report;
	}
	
	private HashMap<String, String> getParameter() {
		HashMap<String, String> parametersMap = new HashMap<>();
		if(reportJson.has("properties")){
			JsonObject parametersJson = reportJson.get("properties").getAsJsonObject();
			Set<Entry<String, JsonElement>> parametersEnties = parametersJson.entrySet();
			for (Entry<String, JsonElement> entry : parametersEnties) {
				String key = entry.getKey();
				if(key.startsWith("rmreport") || key.startsWith("rmt")){
					String value = entry.getValue().getAsString();
					parametersMap.put(key, value);
				}
			}
		}
		return parametersMap;
	}

	private List<ReportTestCase> getTestcases() {
		JsonArray tests = reportJson.get("tests").getAsJsonArray();
		List<ReportTestCase> results = new ArrayList<>();
		for (JsonElement entry : tests) {
			JsonObject test = entry.getAsJsonObject();
			JsonReportTestCase testCase = new JsonReportTestCase();
			if (test.get("isGherkin").getAsBoolean()) {
				testCase.setGherkinSteps(getGherkinSteps(test));
			}
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
		setDriverFullName(driver);
		return driver;
	}
	
	private void setDriverFullName(Driver driver){
		String os = driver.getOs();
		String osVer = driver.getOsVer();
		String device = driver.getDevice();
		String browser = driver.getBrowser();
		String browserVer = driver.getBrowserVer();
		driver.setFullName(os+"_"+osVer+"_"+device+"_"+browser+"_"+browserVer);
	}

	private ResultType getTestResult(JsonObject test) {
		switch (test.get("result").getAsString()) {
		case "passed":
			return ResultType.PASSED;
		case "failure":
			return ResultType.FAILURE;
		case "error":
			return ResultType.ERROR;
		case "skipped":
			return ResultType.SKIPPED;
		default:
			return ResultType.PASSED;
		}
	}

	private String getTestMessage(JsonObject test) {
		try {
            return test.get("failureMessage").getAsString();
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
		System.out.println();
		String fullSuiteName = reportJson.get("suite").getAsString();
		int start = fullSuiteName.lastIndexOf('.');
        return fullSuiteName.substring(start+1);
	}

    private List<String> getGherkinSteps(JsonObject test) {
        JsonArray steps = test.get("steps").getAsJsonArray();
        if (steps == null) System.out.println("test = null");
        List<String> gherkinSteps = new ArrayList<>();
        for (JsonElement entry : steps) {
            JsonObject step = entry.getAsJsonObject();
            for (Entry<String, JsonElement> element : step.entrySet()) {
                gherkinSteps.add(element.getValue().getAsString());
            }
        }
        return gherkinSteps;
    }
	
}
