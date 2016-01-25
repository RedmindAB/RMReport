package se.redmind.rmtest.report.parser.json.gherkin;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

public class GherkinRmtestReport extends Report<JsonObject> {

	@Override
	protected int tests(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String extractSuiteName(JsonObject fullReport, String name) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ReportTestCase<?> extractTestCase(JsonObject testcase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String extractSuitePackage(JsonObject fullReport, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, String> parameters(JsonObject fullReport) {
		// TODO Auto-generated method stub
		return null;
	}

}
