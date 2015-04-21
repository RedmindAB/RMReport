package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import se.redmind.rmtest.web.route.api.classes.getclasses.GetClassesWS;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetClassesWSTest {
	
	@Test
	public void getMethods_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		
		GetClassesWS ws = new GetClassesWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(10, array.size());

	}

}
