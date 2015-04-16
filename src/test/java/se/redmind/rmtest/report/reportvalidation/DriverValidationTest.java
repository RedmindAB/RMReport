package se.redmind.rmtest.report.reportvalidation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class DriverValidationTest {

	static DBCon dbc;
	static String reportPath = System.getProperty("user.dir")+"/reports_for_test";
	static File file = new ReportLoader(reportPath, false).getXMLReports().get(0);
	static ReportXMLParser parser = new ReportXMLParser();
	static DriverValidation driverValidation;
	
	@BeforeClass
	public static void beforeClass(){
		dbc = DBCon.getDbTestInstance();
		dbc.dropDatabase(dbc.getConnection());
		Report report = parser.getReportFromFile(file);
		driverValidation = new DriverValidation(report);
	}
	
	@Ignore
	@Test
	public void testOS() {
		HashMap<String, Integer> osMap = driverValidation.getOSMap();
		int mapSize = osMap.size();
		assertEquals(10, mapSize);
		assertTrue(osMap.containsKey("Windows8.1"));
		assertTrue(osMap.containsKey("Android5.1"));
		assertTrue(osMap.containsKey("Android4.4.4"));
		assertTrue(osMap.containsKey("OSX10.9.5"));
		assertTrue(osMap.containsKey("IOS8.2"));
		assertTrue(osMap.containsKey("Ubuntu14.04"));
		HashMap<String, Integer> osMap2 = driverValidation.getOSMap();
		assertEquals(10, osMap2.size());
	}
	
	
	@Test
	@Ignore
	public void testDevice() {
		HashMap<String, Integer> deviceMap = driverValidation.getDeviceMap();
		assertEquals(7, deviceMap.size());
		assertTrue(deviceMap.containsKey("UNKNOWN"));
		assertTrue(deviceMap.containsKey("HTC ONE"));
		assertTrue(deviceMap.containsKey("iPhone 6"));
		assertTrue(deviceMap.containsKey("Nexus 6"));
	}
	
	@Ignore
	@Test
	public void testBrowser(){
		HashMap<String, Integer> browserMap = driverValidation.getBrowserMap();
		assertEquals(4, browserMap.size());
		assertTrue(browserMap.containsKey("chrome42"));
		assertTrue(browserMap.containsKey("firefox31"));
		assertTrue(browserMap.containsKey("explorer10"));
		assertTrue(browserMap.containsKey("opera15"));
	}
	
}
