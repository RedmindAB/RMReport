package se.redmind.rmtest.util;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GraphDataExtractor {
	
	public JsonArray extractGraphData(List<HashMap<String,String>> reportData){
		JsonArray array = new JsonArray();
		try {
			for (HashMap<String, String> hashMap : reportData) {
				JsonObject report = new JsonObject();
				report.add("timestamp", new JsonPrimitive(hashMap.get("timestamp")));
				report.add("pass", new JsonPrimitive(Integer.valueOf(hashMap.get("pass"))));
				report.add("fail", new JsonPrimitive(Integer.valueOf(hashMap.get("fail"))));
				report.add("error", new JsonPrimitive(Integer.valueOf(hashMap.get("error"))));
				report.add("time", new JsonPrimitive(Float.valueOf(hashMap.get("time"))));
				array.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	
}
