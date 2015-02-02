package se.redmind.rmtest.web.route.api.getclasses;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadClassFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class GetClassesDAO {

	
	public String getClasses(int suiteid){
		JsonArray array = new JsonArray();
		List<HashMap<String, Object>> allClassNames = new ReadClassFromDB().getAllClassNames(suiteid);
		for (HashMap<String, Object> hashMap : allClassNames) {
			JsonObject classObject = new JsonObject();
			String name = (String) hashMap.get("name");
			int id = Integer.valueOf((String) hashMap.get("id"));
			classObject.add("id", new JsonPrimitive(id));
			classObject.add("name", new JsonPrimitive(name));
			array.add(classObject);
		}
		return new Gson().toJson(array);
	}
	
}
