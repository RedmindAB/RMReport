package se.redmind.rmtest.web.route.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ErrorResponse {

	private String message;
	private JsonElement json;
	
	public ErrorResponse(String message) {
		this.message = message;
	}
	
	public ErrorResponse(JsonElement json) {
		this.json = json;
	}
	
	@Override
	public String toString(){
		JsonObject errorMessage = new JsonObject();
		if (message != null) errorMessage.addProperty("error", message);
		else errorMessage.add("error", this.json);
		return new Gson().toJson(errorMessage);
	}
	
}
