package se.redmind.rmtest.webservicetests;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static org.mockito.Mockito.*;

import se.redmind.rmtest.web.route.api.stats.devicefail.DeviceStatsFailWS;
import se.redmind.rmtest.web.route.api.suite.parameters.SuiteParametersWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

public class DeviceFailStatsWSTEst extends WSSetupHelper {
	
	@Test
	public void deviceFail_emptyRequest(){
		Request request = request();
		Response response = response();
		DeviceStatsFailWS ws = new DeviceStatsFailWS();
		JsonElement res = ws.handleRequest(response, request);
		assertEquals(0, jsonArray(res).size());
	}
	
	@Test
	public void deviceFail_getDevices(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("1");
		when(request.params("osname")).thenReturn("Android");
		DeviceStatsFailWS ws = new DeviceStatsFailWS();
		JsonArray res = jsonArray(ws.handleRequest(response, request));
		JsonObject nexus6 = res.get(0).getAsJsonObject();
		JsonObject htcOne = res.get(1).getAsJsonObject();
		assertEquals(2, res.size());
		assertEquals(16, nexus6.get("total").getAsInt());
		assertEquals(0, nexus6.get("totalFail").getAsInt());
		assertEquals(16, htcOne.get("total").getAsInt());
		assertEquals(16, htcOne.get("totalFail").getAsInt());
	}
	
	@Test
	public void deviceFail_insaneInput(){
		Request request = request();
		Response response = response();
		when(request.params("suiteid")).thenReturn("awd");
		when(request.params("osname")).thenReturn("Android");
		when(request.queryParams("limit")).thenReturn("awdaw");
		DeviceStatsFailWS ws = new DeviceStatsFailWS();
		JsonArray res = jsonArray(ws.handleRequest(response, request));
		assertEquals(0, res.size());
	}
}
