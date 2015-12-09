package se.redmind.rmtest.liveteststream;

import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestRun {
	
	private static final String STATUS = "status";
	private JsonObject suite;
	private String UUID;
	private TreeMap<Integer, JsonObject> history;
	private int historyID = 0;
	private int finishID = 0;
	private int startID = 0;
	
	public TestRun(String UUID, JsonObject suite) {
		this.UUID = UUID;
		this.suite = suite;
		this.history = new TreeMap<Integer, JsonObject>();
	}
	
	public void setStatus(String status){
		suite.addProperty(STATUS, status);
	}
	
	public void updateTime(){
		suite.addProperty("lastUpdated", System.currentTimeMillis());
	}

	public JsonArray getTests() {
		return suite.get("tests").getAsJsonArray();
	}
	
	public void startTest(String testID) {
		JsonArray tests = suite.get("tests").getAsJsonArray();
		JsonObject test = null;
		for (JsonElement jsonElement : tests) {
			 test = jsonElement.getAsJsonObject();
			if (test.get("id").getAsString().equals(testID)){
				break;
			}
		}
		test.addProperty("status", "running");
		test.addProperty("startID", startID++);
		test.addProperty("startTime", System.currentTimeMillis());
		addToHistory(test, "running");
	}
	
	public void finishTest(String id, JsonObject finishedTest){
		JsonObject test = getTestCase(id);
		test.addProperty("status", "done");
		test.addProperty("result", finishedTest.get("result").getAsString());
		test.addProperty("runTime", finishedTest.get("runTime").getAsDouble());
		test.addProperty("finishID", finishID++);
		addToHistory(test, "done");
		setTestCase(id, test);
	}
	
	public void setTestCase(String id, JsonObject test){
		JsonArray tests = suite.get("tests").getAsJsonArray();
		int index = Integer.valueOf(id)-1;
		JsonObject rTest = tests.get(index).getAsJsonObject();
		if (rTest.get("id").equals(id)){
			tests.set(index, test);
		}
		else {
			for (int i = 0; i < tests.size(); i++) {
				rTest = tests.get(i).getAsJsonObject();
				if (rTest.get("id").equals(id)){
					tests.set(i, rTest);
					break;
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param id - the ID of the testcase as a string
	 * @return - JsonObject representing a testcase
	 */
	public JsonObject getTestCase(String id){
		JsonArray tests = suite.get("tests").getAsJsonArray();
		JsonObject test = tests.get(Integer.valueOf(id)-1).getAsJsonObject();
		if (test.get("id").getAsString().equals(id)){
			return test;
		}
		else {
			for (JsonElement jsonElement : tests) {
				test = jsonElement.getAsJsonObject();
				if (test.get("id").getAsString().equals(id)) return test;
			}
		}
		return null;
	}

	public JsonObject getSuite() {
		return suite;
	}
	
	private void addToHistory(JsonObject test, String type){
		this.historyID++;
		Gson gson = new Gson();
		JsonObject historyObj = new JsonObject();
		historyObj.addProperty("type", type);
		historyObj.add("data", gson.fromJson(test, JsonObject.class));
		history.put(this.historyID, historyObj);
		setLastChangeToSuite(historyID);
	}
	
	public void setLastChangeToSuite(int lastChange){
		suite.addProperty("historyid", lastChange);
	}
	
	public JsonArray getHistory(int fromIndex){
		JsonArray results = new JsonArray();
		try {
			SortedMap<Integer, JsonObject> tailMap = history.tailMap(fromIndex);
			Set<Entry<Integer, JsonObject>> entrySet = tailMap.entrySet();
			Gson gson = new Gson();
			for (Entry<Integer, JsonObject> entry : entrySet) {
				JsonObject test = entry.getValue();
				test.addProperty("historyid", entry.getKey());
				results.add(test);
		}
		} catch (Exception e) {
		}
		return results;
	}
	
	public String getUUID() {
		return UUID;
	}

	public void finishSuite() {
		setStatus("finished");
		addToHistory(new JsonObject(), "suiteFinish");
	}

}
