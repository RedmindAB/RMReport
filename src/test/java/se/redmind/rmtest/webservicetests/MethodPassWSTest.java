package se.redmind.rmtest.webservicetests;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static org.mockito.Mockito.*;


import se.redmind.rmtest.web.route.api.stats.methodpass.MethodPassWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

public class MethodPassWSTest extends WSSetupHelper {
	
		MethodPassWS ws = new MethodPassWS();
	
	@Test
	public void getPassFail(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("1");
		
		JsonElement result = ws.handleRequest(response, request);
		Gson gson = new Gson();
		JsonArray json = gson.fromJson(result, JsonArray.class);
		assertEquals(8, json.size());
	}
	
	@Test
	public void getNothing(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("awdawd");
		
		JsonElement result = ws.handleRequest(response, request);
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(result, JsonObject.class);
		assertTrue(json.has("error"));
		assertEquals("suiteid must be an integer", json.get("error").getAsString());
	}
	
	@Test
	public void insaneSuiteID(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("9999");
		
		JsonElement result = ws.handleRequest(response, request);
		Gson gson = new Gson();
		JsonArray json = gson.fromJson(result, JsonArray.class);
		assertEquals(0, json.size());
	}
	
}
