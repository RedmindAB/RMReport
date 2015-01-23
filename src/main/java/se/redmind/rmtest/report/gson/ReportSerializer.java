package se.redmind.rmtest.report.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.Reports;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ReportSerializer implements JsonSerializer<Reports> {

	@Override
	public JsonElement serialize(Reports reports, Type type,
			JsonSerializationContext context) {
		JsonArray result = new JsonArray();
		List<Report> reportArray = reports.getReports();
		for (Report report : reportArray) {
			JsonObject jObject = new JsonObject();
			
			String name = report.getName();
			jObject.add("name", new JsonPrimitive(name));
			
			double time = report.getTime();
			jObject.add("time", new JsonPrimitive(time));
			
			int errors = report.getErrors();
			jObject.add("errors", new JsonPrimitive(errors));
			
			int failures = report.getFailures();
			jObject.add("faliures", new JsonPrimitive(failures));
			
			int skipped = report.getSkipped();
			jObject.add("skipped", new JsonPrimitive(skipped));
			
			int tests = report.getTests();
			jObject.add("tests", new JsonPrimitive(tests));
			
			HashMap<String, String> properties = report.getProperties();
			if (properties != null) {
				jObject.add("properties", extractHashMap(properties));
			}
				ArrayList<ReportTestCase> testCases = report.getTestCases();
			if (testCases != null) {
				jObject.add("testCases", getTestCases(testCases));
			}
			result.add(jObject);
		}
		return result;
	}
	
	private JsonArray getTestCases(List<ReportTestCase> cases){
		JsonArray jArray = new JsonArray();
		for (ReportTestCase reportTestCase : cases) {
			JsonObject caseObject = new JsonObject();
			
			String name = reportTestCase.getName();
			caseObject.add("name", new JsonPrimitive(name));
			
			String className = reportTestCase.getClassName();
			caseObject.add("className", new JsonPrimitive(className));
			
			double time = reportTestCase.getTime();
			caseObject.add("time", new JsonPrimitive(time));
			
			HashMap<String, String> error = reportTestCase.getError();
			if (error != null) {
				caseObject.add("error", extractHashMap(error));
			}
			HashMap<String, String> failiure = reportTestCase.getFailure();
			if (failiure != null) {
				caseObject.add("failiure", extractHashMap(failiure));
			}
			jArray.add(caseObject);
		}
		return jArray;
	}
	
	private JsonObject extractHashMap(HashMap<String, String> props){
		JsonObject result = new JsonObject();
		for (String key : props.keySet()) {
			String value = (String)props.get(key);
			result.add(key, new JsonPrimitive(value));
		}
		return result;
	}

}
