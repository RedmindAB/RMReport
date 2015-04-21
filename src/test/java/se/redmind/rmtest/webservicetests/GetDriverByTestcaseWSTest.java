package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseWS;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetDriverByTestcaseWSTest {

	@Test
	public void getDriverByTestcaseWS_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("id")).thenReturn("1");
		when(request.queryParams("timestamp")).thenReturn("20150101080000");
		
		GetDriverByTestcaseWS ws = new GetDriverByTestcaseWS("");
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
		assertEquals(16, array.size());
	}
}
