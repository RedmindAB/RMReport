package se.redmind.rmtest.web.route.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ErrorResponse {

	Logger log;
	
	private String message;
	private JsonElement json;
	
	public ErrorResponse(String message, Class<?> invokingClass) {
		this.message = message;
		errorResponse(invokingClass);
	}
	
	public ErrorResponse(JsonElement json, Class<?> invokingClass) {
		this.json = json;
		errorResponse(invokingClass);
	}
	
	private void errorResponse(Class<?> invokingClass){
		this.log = LogManager.getLogger(invokingClass.getSimpleName());
		log();
	}
	
	private void log(){
		if (message != null) {
			log.error(message);
		}
		else log.error(toString());
	}
	
	public JsonObject getJson(){
		return new Gson().fromJson(toString(), JsonObject.class);
	}
	
	@Override
	public String toString(){
		JsonObject errorMessage = new JsonObject();
		if (message != null) errorMessage.addProperty("error", message);
		else errorMessage.add("error", this.json);
		return new Gson().toJson(errorMessage);
	}
	
}
