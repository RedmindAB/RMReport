package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.method.getmethods.GetMethodsWS;
import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsWS;
import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetGraphOptionsWSTest extends WSSetupHelper{
	
	@Test
	public void getMethods_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		when(request.queryParams("limit")).thenReturn("5");
		
		GetGraphOptionsWS ws = new GetGraphOptionsWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonObject object = gson.fromJson(result.toString(), JsonObject.class);
		//assert platforms to be 3 because the testdb has 2 android, 1 osx and 1 ubuntu devices.
		JsonArray platforms = object.get("platforms").getAsJsonArray();
		assertEquals(3, platforms.size());
		//Assert browsers to be two because all devices in the testdb has same version of chrome and firefox
		JsonArray browsers = object.get("browsers").getAsJsonArray();
		assertEquals(2, browsers.size());
	}
}
