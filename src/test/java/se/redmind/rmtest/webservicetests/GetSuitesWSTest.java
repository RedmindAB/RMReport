package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetSuitesWSTest extends WSSetupHelper{
	
	
	@Test
	public void getSuites_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		
		GetSuitesWS ws = new GetSuitesWS();
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(2, array.size());
//		assertEquals(1, array.get(0).getAsJsonObject().get("id").getAsInt());
	}
}
