package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

public class GetGraphDataWSTest extends WSSetupHelper{
	
	public JsonArray generateRequestJson(String name, int reslimit, int[] os, int[] devices, int[] browsers, int[] classes, int[] testcases, String suiteid){
		JsonArray array = new JsonArray();
		JsonObject object = new JsonObject();
		object.addProperty("name", name);
		object.addProperty("suiteid", suiteid);
		object.addProperty("reslimit", reslimit);
		
		//add OS's
		object.add("os", generateArray(os));
		//add devices
		object.add("devices", generateArray(devices));
		object.add("browsers", generateArray(browsers));
		object.add("classes", generateArray(classes));
		object.add("testcases", generateArray(testcases));
		array.add(object);
		return array;
	}
	
	private JsonArray generateArray(int[] ids) {
		JsonArray array = new JsonArray();
		for (int id : ids) {
			array.add(new JsonPrimitive(id));
		}
		return array;
	}
	
	
	@Test
	public void getGraphdata_basicRequest() throws InterruptedException  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		String generateRequestJson = generateRequestJson("nexus 6", 5, new int[0], new int[0], new int[0], new int[0], new int[0],"2").toString();
		when(request.body()).thenReturn(generateRequestJson);
		when(request.pathInfo()).thenReturn("awdawd");
		GetGraphDataWS ws = new GetGraphDataWS();
		ws.setLoggingEnabled(false);
		
		GetSuitesWS gws = new GetSuitesWS();
		Object handle = gws.handle(request, response);
		System.out.println("Suites: "+handle);
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		JsonObject resultJson = array.get(0).getAsJsonObject();
		assertEquals("nexus 6", resultJson.get("name").getAsString());
		assertEquals(5, resultJson.get("data").getAsJsonArray().size());
	}
	
	@Test
	public void getGraphdata_limitedResult()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		String generateRequestJson = generateRequestJson("nexus 6", 15, new int[]{1}, new int[0], new int[0], new int[0], new int[0],"2").toString();
		when(request.body()).thenReturn(generateRequestJson);
		when(request.pathInfo()).thenReturn("awdawd");
		
		GetGraphDataWS ws = new GetGraphDataWS();
		ws.setLoggingEnabled(false);
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		JsonObject resultJson = array.get(0).getAsJsonObject();
		assertEquals("nexus 6", resultJson.get("name").getAsString());
		assertEquals(9, resultJson.get("data").getAsJsonArray().size());
	}
	
	@Test
	public void getGraphdata_multiRequest()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		JsonArray firstArray = generateRequestJson("first array", 15, new int[]{1}, new int[0], new int[0], new int[0], new int[0],"2");
		JsonArray secondArray = generateRequestJson("second array", 15, new int[]{1}, new int[0], new int[0], new int[0], new int[0],"2");
		firstArray.addAll(secondArray);
		when(request.body()).thenReturn(new Gson().toJson(firstArray));
		when(request.pathInfo()).thenReturn("awdawd");
		GetGraphDataWS ws = new GetGraphDataWS();
		ws.setLoggingEnabled(false);
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		//assert the result to be 2 two objects
		assertEquals(2, array.size());
		//checks values in the first result
		JsonObject resultJson = array.get(0).getAsJsonObject();
		assertEquals("first array", resultJson.get("name").getAsString());
		assertEquals(9, resultJson.get("data").getAsJsonArray().size());
		
		//checks values in second result
		resultJson = array.get(1).getAsJsonObject();
		assertEquals("second array", resultJson.get("name").getAsString());
		assertEquals(9, resultJson.get("data").getAsJsonArray().size());
	}
	
	@Test
	public void getGraphdata_wrongSuiteID()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		String generateRequestJson = generateRequestJson("nexus 6", 15, new int[]{1}, new int[0], new int[0], new int[0], new int[0], "awd").toString();
		when(request.body()).thenReturn(generateRequestJson);
		when(request.pathInfo()).thenReturn("awdawd");
		
		GetGraphDataWS ws = new GetGraphDataWS();
		ws.setLoggingEnabled(false);
		
		Object result = ws.handle(request, response);
		JsonObject json = jsonObject((String) result);
		assertTrue(json.has("error"));
		assertEquals("suite id was not set to a valid value", json.get("error").getAsString());
	}
	
	@Test
	public void getGraphdata_badLimit()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		String generateRequestJson = generateRequestJson("nexus 6", -11, new int[]{1}, new int[0], new int[0], new int[0], new int[0], "2").toString();
		when(request.body()).thenReturn(generateRequestJson);
		when(request.pathInfo()).thenReturn("awdawd");
		
		GetGraphDataWS ws = new GetGraphDataWS();
		ws.setLoggingEnabled(false);
		
		Object result = ws.handle(request, response);
		JsonObject json = jsonObject((String) result);
		assertTrue(json.has("error"));
		assertEquals("reslimit is not set to a valid value", json.get("error").getAsString());
	}

}
