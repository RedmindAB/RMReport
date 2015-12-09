package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.classes.passfail.PassFailClassWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class PassFailClassWSTest extends WSSetupHelper {
	
	@Test
	public void getMethods_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("classid")).thenReturn("1");
		when(request.queryParams("timestamp")).thenReturn("20150110080009");
		when(request.queryParams("testcaseid")).thenReturn("1");
		
		PassFailClassWS ws = new PassFailClassWS();
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonObject object = gson.fromJson(result.toString(), JsonObject.class);
		int total = 0;
		total += object.get("passed").getAsInt();
		total += object.get("error").getAsInt();
		total += object.get("failure").getAsInt();
		total += object.get("skipped").getAsInt();
		assertEquals(total, object.get("total").getAsInt());
	}
	
}
