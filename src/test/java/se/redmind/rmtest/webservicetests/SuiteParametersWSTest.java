package se.redmind.rmtest.webservicetests;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static org.mockito.Mockito.*;

import se.redmind.rmtest.web.route.api.suite.parameters.SuiteParametersWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

public class SuiteParametersWSTest extends WSSetupHelper {

	@Test
	public void getParameters(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("1");
		when(request.params("timestamp")).thenReturn("20150101080000");
		SuiteParametersWS ws = new SuiteParametersWS();
		JsonElement res = ws.handleRequest(response, request);
		JsonObject json = jsonObject(res);
		assertEquals("superBranch", json.get("rmreport.branch").getAsString());
		assertEquals("customSuiteName", json.get("rmreport.suitename").getAsString());
		assertEquals(2, json.entrySet().size());
	}
	
	@Test
	public void getParameters_wrongTimestamp(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("1");
		when(request.params("timestamp")).thenReturn("20150101080001");
		SuiteParametersWS ws = new SuiteParametersWS();
		JsonElement res = ws.handleRequest(response, request);
		JsonObject json = jsonObject(res);
		assertEquals(0, json.entrySet().size());
	}
	
}
