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
import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetMethodsWSTest {
	
	@Test
	public void getMethods_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("classid")).thenReturn("1");
		
		GetMethodsWS ws = new GetMethodsWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(4, array.size());
//		assertEquals(1, array.get(0).getAsJsonObject().get("id").getAsInt());
	}
	
	@Test
	public void getMethods_minus1()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("classid")).thenReturn("-1");
		
		GetMethodsWS ws = new GetMethodsWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(0, array.size());
//		assertEquals(1, array.get(0).getAsJsonObject().get("id").getAsInt());
	}
}
