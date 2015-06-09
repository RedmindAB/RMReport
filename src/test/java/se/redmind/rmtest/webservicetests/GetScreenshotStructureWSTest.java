package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.screenshot.structure.ScreenshotStructureWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetScreenshotStructureWSTest extends WSSetupHelper{
	
	@Test
	public void getScreenshotStructure()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("classid")).thenReturn("1");
		when(request.queryParams("timestamp")).thenReturn("20150101080000");
		
		ScreenshotStructureWS ws = new ScreenshotStructureWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		//asserting size to 4 because the class have 4 methods.
		assertEquals(4, array.size());
		
		//Testing structure
		JsonObject firstObjectInArray = array.get(0).getAsJsonObject();
		JsonArray testcaseArray = firstObjectInArray.get("testcases").getAsJsonArray();
		//asserting size of 8 because there are 4 devices with 2 browsers each.
		assertEquals(8, testcaseArray.size());
		
		JsonObject firstObject = testcaseArray.get(0).getAsJsonObject();
		JsonArray screenshots = firstObject.get("screenshots").getAsJsonArray();
		//asserting 0 because in this test there are no screenshots to be accessed. 
		assertEquals(0, screenshots.size());
	}
}
