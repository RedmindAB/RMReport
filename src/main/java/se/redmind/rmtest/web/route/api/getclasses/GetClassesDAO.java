package se.redmind.rmtest.web.route.api.getclasses;

import se.redmind.rmtest.db.read.ReadClassFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class GetClassesDAO {

	
	public String getClasses(int suiteid){
		JsonArray array = new JsonArray();
		new ReadClassFromDB().getAllClassNames(suiteid);
		for (int i = 0; i < 10; i++) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("classname", new JsonPrimitive("testclass"+i));
			array.add(jsonObject);
		}
		return new Gson().toJson(array);
	}
	
}
