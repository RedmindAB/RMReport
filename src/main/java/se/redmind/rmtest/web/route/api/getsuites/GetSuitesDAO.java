package se.redmind.rmtest.web.route.api.getsuites;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadSuiteFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuitesDAO {
	
	public String getSuites(){
		JsonArray jsonArray = new JsonArray();
		List<HashMap<String, Object>> allSuites = new ReadSuiteFromDB().getAllSuites();
		for (HashMap<String, Object> hashMap : allSuites) {
			try {
				JsonObject suiteObject = new JsonObject();
				System.out.println(hashMap.toString());
				suiteObject.add("name", new JsonPrimitive((String)hashMap.get("name")));
				suiteObject.add("id", new JsonPrimitive((int)hashMap.get("id")));
				jsonArray.add(suiteObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Gson().toJson(jsonArray);
	}

}
