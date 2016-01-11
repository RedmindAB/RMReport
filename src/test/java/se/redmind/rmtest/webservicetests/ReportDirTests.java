package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.route.api.admin.reportdir.CreateReportDirWS;
import spark.Request;
import spark.Response;

public class ReportDirTests {

	private static String fakeDir = "tmp/fake/path";
	static File file = new File(System.getProperty("user.dir")+"/"+fakeDir);
	
	@BeforeClass
	public static void beforeClass(){
		file.mkdirs();
	}
	
	@After
	public void afterClass(){
		new File(System.getProperty("user.dir")+"/tmp").delete();
	}
	
	@Test
	public void createDir() {
		CreateReportDirWS ws = new CreateReportDirWS(true);
		ConfigHandler instance = ConfigHandler.getInstance(true);
		instance.clearReportPaths();
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.body()).thenReturn("[\""+fakeDir+"\"]");
		
		ws.handle(request, response);
		
		String[] reportPaths = instance.getReportPaths();
		assertEquals(1, reportPaths.length);
		assertEquals(fakeDir, reportPaths[0]);
	}
	
	@Test
	public void createDirDuplicate() {
		CreateReportDirWS ws = new CreateReportDirWS(true);
		ConfigHandler instance = ConfigHandler.getInstance(true);
		instance.clearReportPaths();
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.body()).thenReturn("[\""+fakeDir+"\"]");
		
		ws.handle(request, response);
		
		String[] reportPaths = instance.getReportPaths();
		assertEquals(1, reportPaths.length);
		assertEquals(fakeDir, reportPaths[0]);
		
		String res = (String) ws.handle(request, response);
		assertTrue(res.contains("You can not add duplicates"));
		reportPaths = instance.getReportPaths();
		assertEquals(1, reportPaths.length);
		assertEquals(fakeDir, reportPaths[0]);
	}
	
	@Test
	public void badRequestCreateReport() {
		CreateReportDirWS ws = new CreateReportDirWS(true);
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		when(response.raw()).thenReturn(httpResponse);
		when(request.body()).thenReturn("bad request");
		
		ws.handle(request, response);
	}
	
	@Test
	public void pathDidNotExist() {
		CreateReportDirWS ws = new CreateReportDirWS(true);
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		when(response.raw()).thenReturn(httpResponse);
		when(request.body()).thenReturn("[\"/a/path/that/not/Ex15t5\"]");
		
		String res = (String) ws.handle(request, response);
		assertTrue(res.contains("path did not exist"));
	}

}
