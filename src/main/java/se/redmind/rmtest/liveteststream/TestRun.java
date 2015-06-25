package se.redmind.rmtest.liveteststream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TestRun {
	
	private static final String STATUS = "status";
	private JsonObject suite;
	private String UUID;
	private TreeMap<Integer, JsonObject> history;
	private int historyID = 0;
	
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

	public JsonObject getTests() {
		return suite.get("tests").getAsJsonObject();
	}
	
	public void startTest(String testID) {
		JsonObject tests = suite.get("tests").getAsJsonObject();
		JsonObject test = tests.get(testID).getAsJsonObject();
		test.addProperty("status", "running");
		addToHistory(test, "running");
	}
	
	public void finishTest(String id, JsonObject test){
		JsonObject tests = suite.get("tests").getAsJsonObject();
		test.addProperty("status", "done");
		tests.add(id, test);
		addToHistory(test, "done");
	}

	public JsonObject getSuite() {
		return suite;
	}
	
	private void addToHistory(JsonObject test, String type){
		this.historyID++;
		JsonObject historyObj = new JsonObject();
		historyObj.addProperty("type", type);
		historyObj.add("data", test);
		history.put(this.historyID, historyObj);
		setLastChangeToSuite(historyID);
	}
	
	public void setLastChangeToSuite(int lastChange){
		suite.addProperty("historyid", lastChange);
	}
	
	public JsonArray getHistory(int fromIndex){
		SortedMap<Integer, JsonObject> tailMap = history.tailMap(fromIndex);
		Set<Entry<Integer, JsonObject>> entrySet = tailMap.entrySet();
		JsonArray results = new JsonArray();
		for (Entry<Integer, JsonObject> entry : entrySet) {
			JsonObject test = entry.getValue();
			test.addProperty("historyid", entry.getKey());
			results.add(test);
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
