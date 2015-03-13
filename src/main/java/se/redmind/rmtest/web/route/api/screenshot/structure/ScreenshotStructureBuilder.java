package se.redmind.rmtest.web.route.api.screenshot.structure;

import java.util.List;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ScreenshotStructureBuilder {

	private static final String NAME = "name";
	private static final String TESTCASES = "testcases";
	private TreeMap<String, JsonObject> methodMap;
	
	
	public ScreenshotStructureBuilder() {
		this.methodMap = new TreeMap<String, JsonObject>();
		
	}
	
	public void addTestcase(String methodname, String browsername, String devicename, List<String> screenshots){
		JsonObject method = getMethod(methodname);
		JsonObject testcase = new JsonObject();
		testcase.addProperty("device", devicename);
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

	public JsonObject getMethod(String methodname){
		JsonObject method = methodMap.get(methodname);
		if (method == null) {
			method = new JsonObject();
			method.addProperty(NAME, methodname);
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
