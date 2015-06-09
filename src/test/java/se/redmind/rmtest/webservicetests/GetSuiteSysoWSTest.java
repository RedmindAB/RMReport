package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

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
		
		GetSuiteSysosWS ws = new GetSuiteSysosWS("", basedir);
		
		String result = (String) ws.handle(request, response);
		assertNotNull(result);
		assertTrue(result.length() > 0);
	}
	
}
