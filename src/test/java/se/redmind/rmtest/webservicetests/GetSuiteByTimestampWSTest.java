package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetSuiteByTimestampWSTest extends WSSetupHelper{

	
	@Test
	public void getSuiteByTimestamp_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		when(request.queryParams("timestamp")).thenReturn("20150101080000");
		
		GetSuiteByTimestampWS ws = new GetSuiteByTimestampWS();
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(2, array.size());

	}
}
