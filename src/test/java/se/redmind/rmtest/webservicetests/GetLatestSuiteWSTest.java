package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteWS;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetLatestSuiteWSTest {
	
	
	@Test
	public void getLatestSuite_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		
		GetLatestSuiteWS ws = new GetLatestSuiteWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(2, array.size());
	}
}
