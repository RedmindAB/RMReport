package se.redmind.rmtest.web.route.api.getmethods;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetMethodsDAO {

	public String getMethods(int classid) {
		JsonArray array = new JsonArray();
		for (int i = 0; i < 30; i++) {
			JsonObject methodObject = new JsonObject();
			methodObject.add("name", new JsonPrimitive("method"+i));
			array.add(methodObject);
		}
		return new Gson().toJson(array);
	}

}
