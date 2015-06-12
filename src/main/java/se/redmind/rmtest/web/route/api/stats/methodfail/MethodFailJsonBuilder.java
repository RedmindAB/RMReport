package se.redmind.rmtest.web.route.api.stats.methodfail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MethodFailJsonBuilder {
	
	private static final String RATIO_FAIL = "ratioFail";
	private final String TOTAL = "total", FAIL = "fail";
	private HashMap<String, JsonObject> jsonMap;
	private HashSet<String> maxTimestampsMethodClassKeys;
	
	public MethodFailJsonBuilder(HashSet<String> maxTimestampsMethodClassKeys2) {
		this.maxTimestampsMethodClassKeys = maxTimestampsMethodClassKeys2;
		jsonMap = new HashMap<String, JsonObject>();
	}
	
	public void addTestCase(String testcaseName, String classname, String result){
		JsonObject json = getJson(testcaseName, classname);
		if (json == null) return; 
		addResult(json, result);
	}

	private void addResult(JsonObject json, String result) {
		Integer totalRes = json.get(TOTAL).getAsInt();
		json.add(TOTAL, new JsonPrimitive(totalRes+1));
		addFailure(json, result);
	}

	private void addFailure(JsonObject json, String result) {
		switch (result) {
		case "failure":
		case "error":
			Integer fails = json.get(FAIL).getAsInt();
			json.add(FAIL, new JsonPrimitive(fails+1));
			break;
		default:
			break;
		}
	}

	private JsonObject getJson(String testcaseName, String classname) {
		String key = classname+testcaseName;
		if (!maxTimestampsMethodClassKeys.contains(key)) {
			return null;
		}
		JsonObject json = jsonMap.get(key);
		if (json == null) {
			//Creates new Json if it dose not exist with defaul values.
			JsonObject nJson = new JsonObject();
			nJson.addProperty("classname", classname);
			nJson.addProperty("testcaseName", testcaseName);
			nJson.addProperty(TOTAL, 0);
			nJson.addProperty(FAIL, 0);
			json = nJson;
			jsonMap.put(key, nJson);
		}
		return json;
	}
	
	private ArrayList<JsonObject> buildAsList(){
		ArrayList<JsonObject> array = new ArrayList<JsonObject>();
		Set<String> keySet = jsonMap.keySet();
		for (String key : keySet) {
			JsonObject jsonObject = jsonMap.get(key);
			int totalFail = jsonObject.get(FAIL).getAsInt();
			int total = jsonObject.get(TOTAL).getAsInt();
			jsonObject.addProperty(RATIO_FAIL, (double) (totalFail*100)/total);
			array.add(jsonObject);
		}
		return array;
	}
	
	public JsonArray build(int maxResults){
		ArrayList<JsonObject> jsonList = buildAsList();
		Collections.sort(jsonList, new Comparator<JsonObject>() {
			@Override
			public int compare(JsonObject o1, JsonObject o2) {
				double o1V = o1.get(RATIO_FAIL).getAsDouble();
				double o2V = o2.get(RATIO_FAIL).getAsDouble();
				if (o1V==o2V) {
					return 0;
				}
				return o1V > o2V ? -1 : 1;
			}
		});
		JsonArray array = new JsonArray();
		int it = maxResults > jsonList.size() ? jsonList.size() : maxResults;
		for (int i = 0; i < it; i++) {
			array.add(jsonList.get(i));
		}
		return array;
	}
	
	
	
}
