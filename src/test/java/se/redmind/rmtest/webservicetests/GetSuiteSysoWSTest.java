package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.suite.syso.GetSuiteSysosWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetSuiteSysoWSTest extends WSSetupHelper{
	
	@Test
	public void getSuiteSyos_checkLength()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		when(request.queryParams("timestamp")).thenReturn("20150415130511");
		
		String basedir = System.getProperty("user.dir")+"/reports_for_test/sysos";
		GetSuiteSysosWS ws = new GetSuiteSysosWS(basedir);
		
		String result = (String) ws.handle(request, response);
		assertTrue(result.contains("Closing driver: Android_5.1_Nexus 6_se.systembolaget.android_UNKNOWN"));
	}
	
	@Test
	public void getSuiteSyos_getErrorResponse()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		String suiteid = "uadwuawduh";
		when(request.queryParams("suiteid")).thenReturn(suiteid);
		when(request.queryParams("timestamp")).thenReturn("20150415130511");
		
		String basedir = System.getProperty("user.dir")+"/reports_for_test/sysos";
		
		GetSuiteSysosWS ws = new GetSuiteSysosWS(basedir);
		
		JsonObject result = new Gson().fromJson((String) ws.handle(request, response), JsonObject.class);
		assertTrue(result.has("error"));
		assertEquals(result.get("error").getAsString(), "For input string: \"uadwuawduh\"");
	}
	
	@Test
	public void getSuiteSyos_getEmptyResponse()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		String suiteid = "1";
		when(request.queryParams("suiteid")).thenReturn(suiteid);
		when(request.queryParams("timestamp")).thenReturn("2015041513058"); // <--- this is wrong timestamp
		
		String basedir = System.getProperty("user.dir")+"/reports_for_test/sysos";
		
		GetSuiteSysosWS ws = new GetSuiteSysosWS(basedir);
		
		String result = (String) ws.handle(request, response);
		assertEquals("No system out prints for this timestamp", result);
	}
	
}
