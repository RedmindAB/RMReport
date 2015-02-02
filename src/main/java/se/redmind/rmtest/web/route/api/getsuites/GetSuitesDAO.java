package se.redmind.rmtest.web.route.api.getsuites;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuitesDAO {
	
	public String getSuites(){
		JsonArray jsonArray = new JsonArray();
		JsonObject suiteObject = new JsonObject();
		suiteObject.add("name", new JsonPrimitive("CreateLogsTest"));
		suiteObject.add("id", new JsonPrimitive(1));
		suiteObject.add("tests", new JsonPrimitive(12));
		suiteObject.add("error", new JsonPrimitive(4));
		suiteObject.add("success", new JsonPrimitive(8));
		jsonArray.add(suiteObject);
		return new Gson().toJson(jsonArray);
	}

}
