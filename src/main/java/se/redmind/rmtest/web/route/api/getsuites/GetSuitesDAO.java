package se.redmind.rmtest.web.route.api.getsuites;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuitesDAO {
	
	public String getSuites(){
		JsonArray jsonArray = new JsonArray();
		for (int i = 1; i <= 2; i++) {
			JsonObject suiteObject = new JsonObject();
			suiteObject.add("name", new JsonPrimitive("CreateLogsTest"+i));
			suiteObject.add("id", new JsonPrimitive(i));
			suiteObject.add("tests", new JsonPrimitive(12*i));
			suiteObject.add("error", new JsonPrimitive(4*i));
			suiteObject.add("success", new JsonPrimitive(8*i));
			jsonArray.add(suiteObject);
		}
		return new Gson().toJson(jsonArray);
	}

}
