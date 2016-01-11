package se.redmind.rmtet.web.properties.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.properties.ConfigJson;

public class PropertiesTests {
	
	@BeforeClass
	public static void beforeClass() throws IOException{
		Files.deleteIfExists(new File(ConfigHandler.TEST_CONFIG_PATH).toPath());
	}
	
	@AfterClass
	public static void afterClass() throws IOException{
		Files.deleteIfExists(new File(ConfigHandler.TEST_CONFIG_PATH).toPath());
	}
	
	private static final String GRID_QUERY_SERVLET = "http://localhost:4444/grid/admin/GridQueryServlet";
	ConfigHandler ch = ConfigHandler.getInstance(true);

	@Before
	public void checkFile(){
		Assume.assumeTrue(ch.getFileName().equals("configTest.json"));
		ch.clearReportPaths();
		ch.savePort(4567);
		ch.saveSeleniumGridURL(GRID_QUERY_SERVLET);
		ch.saveLiveStreamPort(12345);
	}
	
	@Test
	public void getTestConfig() {
		String fileName = ch.getFileName();
		assertEquals("configTest.json", fileName);
	}
	
	@Test
	public void saveAPath(){
		String path = "/a/path";
		String[] reportPaths = ch.getReportPaths();
		assertEquals(0 ,reportPaths.length);
		ch.saveReportPath(path);
		reportPaths = ch.getReportPaths();
		assertEquals(1 ,reportPaths.length);
		assertEquals(path, reportPaths[0]);
	}
	
	@Test
	public void setPort(){
		int port = ch.getPort();
		assertEquals(4567, port);
		ch.savePort(5678);
		port = ch.getPort();
		assertEquals(5678, port);
	}
	
	@Test
	public void setGridURL(){
		String url = ch.getSeleniumGridURL();
		assertEquals(GRID_QUERY_SERVLET, url);
		String newURL = "http://newurl.com";
		ch.saveSeleniumGridURL(newURL);
		assertEquals(newURL, ch.getSeleniumGridURL());
	}
	
	@Test
	public void liveStreamPort(){
		int liveStreamPort = ch.getLiveStreamPort();
		assertEquals(12345, liveStreamPort);
		int newPort = 23456;
		ch.saveLiveStreamPort(newPort);
		liveStreamPort = ch.getLiveStreamPort();
		assertEquals(newPort, liveStreamPort);
	}
	
	@Test
	public void configClone(){
		assertTrue(ch.saveReportPath("/path"));
		ConfigJson config = ch.getConfig();
		ConfigJson clone = config.clone();
		assertNotEquals(config, clone);
	}
	
	@Test
	public void removeAllPaths(){
		ch.saveReportPath("/a/path");
		ch.saveReportPath("/another/path");
		ch.saveReportPath("/a/new/path");
		String[] reportPaths = ch.getReportPaths();
		assertEquals(3, reportPaths.length);
		ch.clearReportPaths();
		reportPaths = ch.getReportPaths();
		assertEquals(0, reportPaths.length);
	}
	
	@Test
	public void removeAllNamedPaths(){
		String path1 = "/a/path";
		String path2 = "/another/path";
		String path3 = "/a/new/path";
		ch.saveReportPath(path1);
		ch.saveReportPath(path2);
		ch.saveReportPath(path3);
		String[] reportPaths = ch.getReportPaths();
		assertEquals(3, reportPaths.length);
		JsonArray removeArray = new JsonArray();
		removeArray.add(new JsonPrimitive(path1));
		removeArray.add(new JsonPrimitive(path2));
		ch.removeAllPaths(removeArray);
		reportPaths = ch.getReportPaths();
		assertEquals(path3, reportPaths[0]);
	}
	
	@Test
	public void removeSinglePath(){
		String path1 = "/a/path";
		String path2 = "/another/path";
		String path3 = "/a/new/path";
		ch.saveReportPath(path1);
		ch.saveReportPath(path2);
		ch.saveReportPath(path3);
		String[] reportPaths = ch.getReportPaths();
		assertEquals(3, reportPaths.length);
		ch.deleteReportPath(path3);
		reportPaths = ch.getReportPaths();
		assertEquals(2, reportPaths.length);
		assertEquals(path1, reportPaths[0]);
		assertEquals(path2, reportPaths[1]);
	}
	
	@Test
	public void pathExistsInConfig(){
		String path1 = "/a/path";
		String path2 = "/another/path";
		String path3 = "/a/new/path";
		ch.saveReportPath(path1);
		ch.saveReportPath(path2);
		ch.saveReportPath(path3);
		String[] reportPaths = ch.getReportPaths();
		assertEquals(3, reportPaths.length);
		assertTrue(ch.reportPathExistInConfig(path1));
		assertTrue(ch.reportPathExistInConfig(path2));
		assertTrue(ch.reportPathExistInConfig(path3));
		assertFalse(ch.reportPathExistInConfig("/path/that/not/exist"));
	}
	
	@Test
	public void updateReportPath(){
		String path1 = "/a/path";
		String path2 = "/another/path";
		String path3 = "/a/new/path";
		ch.saveReportPath(path1);
		ch.saveReportPath(path2);
		ch.saveReportPath(path3);
		String[] reportPaths = ch.getReportPaths();
		assertEquals(3, reportPaths.length);
		ch.updateReportPath(path1, "/new/path");
	}
	
}
