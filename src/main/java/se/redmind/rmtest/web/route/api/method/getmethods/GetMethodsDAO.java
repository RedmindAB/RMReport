package se.redmind.rmtest.web.route.api.method.getmethods;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadTestcaseFromDB;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetMethodsDAO {

	public String getMethods(int classid) {
		JsonArray array = new JsonArray();
		List<HashMap<String, String>> testCasesFromClassID = new ReadTestcaseFromDB().getTestCasesFromClassID(classid);
		for (HashMap<String, String> hashMap : testCasesFromClassID) {
			JsonObject method = new JsonObject();
			method.add("name", new JsonPrimitive(hashMap.get("name")));
			method.add("id", new JsonPrimitive(hashMap.get("id")));
			array.add(method);
		}
		return new Gson().toJson(array);
	}

}
