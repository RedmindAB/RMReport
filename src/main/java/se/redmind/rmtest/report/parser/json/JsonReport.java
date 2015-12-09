package se.redmind.rmtest.report.parser.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

public class JsonReport extends Report<JsonObject> {

	public JsonReport() {
		super();
	}
	
	public JsonReport(JsonObject fullReport, boolean generateFullReport) {
		super(fullReport, generateFullReport);
	}

	@Override
	protected int tests(JsonObject fullReport) {
		return fullReport.get("totalTests").getAsInt();
	}

	@Override
	protected int errors(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int failures(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int skipped(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected long extractTimestamp(JsonObject fullReport, String name) {
		return fullReport.get("timestamp").getAsLong();
	}

	@Override
	protected String extractSuiteName(JsonObject fullReport, String name) {
		return fullReport.get("suite").getAsString();
	}

	@Override
	protected String getName(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected double getTime(JsonObject element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<JsonObject> getTestCases(JsonObject fullReport) {
		JsonObject tests = fullReport.get("tests").getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = tests.entrySet();
		List<JsonObject> testArray = new ArrayList<JsonObject>();
		for (Entry<String, JsonElement> entry : entrySet) {
			testArray.add(entry.getValue().getAsJsonObject());
		}
		return testArray;
	}

	@Override
	protected ReportTestCase<?> extractTestCase(JsonObject testcase) {
		return new JsonReportTestCase(testcase,null);
	}

	@Override
	protected String extractSuitePackage(JsonObject fullReport, String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getPresentTestClasses() {
		List<ReportTestCase> testCaseArray = getTestCaseArray();
		List<String> classnames = new ArrayList<String>();
		for (ReportTestCase reportTestCase : testCaseArray) {
			String classname = reportTestCase.getClassname();
			if (!classnames.contains(classname)) classnames.add(classname);
		}
		return classnames;
	}

	@Override
	protected HashMap<String, String> parameters(JsonObject fullReport) {
		HashMap<String, String> parametersMap = new HashMap<String,String>();
		JsonObject parametersJson = fullReport.get("properties").getAsJsonObject();
		Set<Entry<String, JsonElement>> parametersEnties = parametersJson.entrySet();
		for (Entry<String, JsonElement> entry : parametersEnties) {
			String key = entry.getKey();
			String value = entry.getValue().getAsString();
			parametersMap.put(key, value);
		}
		return parametersMap;
	}
	
}
