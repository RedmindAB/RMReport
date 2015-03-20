package se.redmind.rmtest.web.route.api.screenshot.structure;

import java.util.List;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ScreenshotStructureBuilder {

	private static final String SKIPPED = "skipped";
	private static final String ERROR = "error";
	private static final String FAILURE = "failure";
	private static final String PASSED = "passed";
	private static final String RESULT = "result";
	private static final String NAME = "name";
	private static final String TESTCASES = "testcases";
	private TreeMap<String, JsonObject> methodMap;
	
	
	public ScreenshotStructureBuilder() {
		this.methodMap = new TreeMap<String, JsonObject>();
		
	}
	
	public void addTestcase(String methodname, String browsername, String devicename, String result, List<String> screenshots){
		JsonObject method = getMethod(methodname, result);
		JsonObject testcase = new JsonObject();
		testcase.addProperty("device", devicename);
		testcase.addProperty(RESULT, result);
		testcase.addProperty("browser", browsername);
		testcase.add("screenshots", generateFilenameArray(screenshots));
		method.get(TESTCASES).getAsJsonArray().add(testcase);
	}
	
	private JsonArray generateFilenameArray(List<String> screenshots) {
		JsonArray array = new JsonArray();
		for (String filename : screenshots) {
			array.add(new JsonPrimitive(filename));
		}
		return array;
	}
	
	private JsonObject getMethod(String methodname, String result){
		JsonObject method = getMethod(methodname);
		incResult(result, method);
		return method;
	}
	
	private void incResult(String result, JsonObject method){
		int res = 0;
		switch (result) {
		case PASSED:
			res = method.get(PASSED).getAsInt();
			method.addProperty(PASSED, res+1);
			break;
		case FAILURE:
			res = method.get(FAILURE).getAsInt();
			method.addProperty(FAILURE, res+1);
			method.addProperty(RESULT, FAILURE);
			break;
		case ERROR:
			res = method.get(ERROR).getAsInt();
			method.addProperty(ERROR, res+1);
			method.addProperty(RESULT, ERROR);
			break;
		case SKIPPED:
			res = method.get(SKIPPED).getAsInt();
			method.addProperty(SKIPPED, res+1);
			break;
		default:
			break;
		}
	}

	private JsonObject getMethod(String methodname){
		JsonObject method = methodMap.get(methodname);
		if (method == null) {
			method = new JsonObject();
			method.addProperty(NAME, methodname);
			method.addProperty(RESULT, PASSED);
			method.addProperty(PASSED, 0);
			method.addProperty(FAILURE, 0);
			method.addProperty(ERROR, 0);
			method.addProperty(SKIPPED, 0);
			method.add(TESTCASES, new JsonArray());
			methodMap.put(methodname, method);
		}
		return method;
	}
	
	public JsonArray getAsJsonArray(){
		JsonArray mainArray = new JsonArray();
		for (String key : methodMap.keySet()) {
			mainArray.add(methodMap.get(key));
		}
		return mainArray;
	}
	
}
