package se.redmind.rmtest.webservicetests;

import static org.mockito.Mockito.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

import org.junit.Test;

import se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile.RMDocsFilterFileDAO;
import se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile.RMDocsFilterFileWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

public class RMDocsFilterFileWSTest extends WSSetupHelper {
	
	public RMDocsFilterFileWSTest() {
		super(true);
	}
	
	@Test
	public void testFilteredBrowsers(){
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.params("suiteid")).thenReturn("1");
		System.out.println(RMDocsFilterFileDAO.sql);
		RMDocsFilterFileWS ws = new RMDocsFilterFileWS();
		
		
		String res = (String) ws.handle(request, response);
		
		assertTrue(res.contains("GraphPage")); 					//all fail
		assertTrue(res.contains("VisualPage"));					//all fail
		assertTrue(res.contains("NavbarPage"));					//all fail
		assertTrue(res.contains("SuitePage"));					//all fail
		assertFalse(res.contains("GridPage#test_gridGetJson")); // passed
		assertFalse(res.contains("isRedmindLogoPresent")); 		// passed
		assertTrue(res.contains("isGraphViewPresent")); 		//skipped
		assertTrue(res.contains("isScreenshotPresent")); 		//skipped
	}
	
	@Test
	public void testFilteredBrowsersExtended(){
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.params("suiteid")).thenReturn("1");
		when(request.queryParams("treshold")).thenReturn("75");
		RMDocsFilterFileWS ws = new RMDocsFilterFileWS();
		
		
		String res = (String) ws.handle(request, response);
		
		assertTrue(res.contains("GraphPage")); 					// all fail
		assertTrue(res.contains("VisualPage"));					// all fail
		assertTrue(res.contains("NavbarPage"));					// all fail
		assertTrue(res.contains("SuitePage"));					// all fail
		assertFalse(res.contains("GridPage#test_gridGetJson")); // passed
		assertFalse(res.contains("isRedmindLogoPresent")); 		// passed
		assertTrue(res.contains("isGraphViewPresent")); 		// skipped
		assertTrue(res.contains("isScreenshotPresent")); 		// skipped
		assertTrue(res.contains("test_goToAdmin Chrome"));		// fail
		assertTrue(res.contains("test_goToDashboard Chrome"));	// fail
		assertFalse(res.contains("test_goToDashboard firefox"));// pass
	}
	
	@Test
	public void testFilteredBrowsersWith75PercentPass(){
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.params("suiteid")).thenReturn("2");
		when(request.queryParams("threshold")).thenReturn("30");
		when(request.queryParams("limit")).thenReturn("10");
		RMDocsFilterFileWS ws = new RMDocsFilterFileWS();
		
		
		String res = (String) ws.handle(request, response);
		
		assertFalse(grepRandomClassRow(res, 7, 1).contains("Opera"));
		assertFalse(grepRandomClassRow(res, 7, 1).contains("Explorer"));
		assertTrue(grepRandomClassRow(res, 7, 1).contains("Chrome"));
		assertTrue(grepRandomClassRow(res, 7, 1).contains("Firefox"));
		
		assertFalse(grepRandomClassRow(res, 0, 2).contains("Firefox"));
		assertTrue(grepRandomClassRow(res, 8, 2).contains("Firefox"));
		
		assertFalse(grepRandomClassRow(res, 6, 2).contains("Opera"));
		
		assertTrue(grepRandomClassRow(res, 0, 2).contains("Explorer"));
	}
	
	private String grepRandomClassRow(String res, int klass, int method){
		Pattern pat = Pattern.compile("(se.redmind.rmtest.selenium.example.RandomClass"+klass+"#random"+method+")+(.*)");
		Matcher matcher = pat.matcher(res);
		matcher.find();
		return matcher.group(2).trim();
	}

}
