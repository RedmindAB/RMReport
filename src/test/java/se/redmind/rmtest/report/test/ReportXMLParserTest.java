package se.redmind.rmtest.report.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;

import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportXMLParserTest {

	private static ReportLoader loader;
	private static ReportXMLParser parser;
	private static final String testFileName = "TEST-test.java.se.redmind.rmtest.selenium.example.AnotherGeneratedSuite-20150204-000001.xml";
	private static File file;
	
	@BeforeClass
	public static void beforeClass(){
		loader = new ReportLoader(ReportLoaderTest.path, false);
		parser = new ReportXMLParser();
		file = loader.getXMLReportByFileName(testFileName);
	}
	
	@Test
	public void getTestCaseList() {
		NodeList list = parser.getNodeList(file, "testcase");
		assertEquals(480, list.getLength());
	}
	
	@Test
	public void createTestcaseFromFile(){
		NodeList list = parser.getNodeList(file, "testcase");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			ReportTestCase testCase = new ReportTestCase(element);
			boolean testNameIsNotEmpty = testCase.getName().length() > 0;
			assertTrue(testNameIsNotEmpty);
		}
	}
	
	@Test
	public void createReportObject(){
		Report report = parser.getReportFromFile(file);
		assertEquals("test.java.se.redmind.rmtest.selenium.example.AnotherGeneratedSuite(20150204-000001)", report.getName());
		assertTrue(481.3582 == report.getTime());
		assertEquals("10.9.5", report.getProperties().get("osversion").getAsJsonObject().get("value").getAsString());
		assertEquals("os version", report.getProperties().get("osversion").getAsJsonObject().get("readName").getAsString());
		JsonArray caseList = report.getTestCases();
		assertEquals(480, caseList.size());
	}
	
	@Test
	public void convertSimpleReportToFullReport(){
		Report report = parser.getSimpleReportFromFile(file);
		assertNull(report.getTestCases());
		report.convertToFullReport();
		assertEquals(480, report.getTestCases().size());
	}
	
	@Test
	public void convertPropName(){
		Report report = parser.getSimpleReportFromFile(file);
		String convertedString = report.removePunctuations("dot.dot.dot", "");
		assertEquals("dotdotdot", convertedString);
	}
	
	@Test
	public void containsTestClasses(){
		Report report = parser.getReportFromFile(file);
		List<String> classes = report.getPresentTestClasses();
		assertEquals(2, classes.size());
		assertTrue(classes.contains("se.redmind.rmtest.selenium.example.RandomClass"));
		assertTrue(classes.contains("se.redmind.rmtest.selenium.example.RandomSuperClass"));
	}

	@Test
	public void getTestCaseDriverValues(){
		Report report = parser.getReportFromFile(file);
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		
		Driver driver = testCaseArray.get(0).getDriver();
		String os = driver.getOs();
		String osVer = driver.getOsVer();
		String device = driver.getDevice();
		String browser = driver.getBrowser();
		String browserVer = driver.getBrowserVer();
		
		assertEquals("Android", os);
		assertEquals("4.4.4", osVer);
		assertEquals("HTC ONE", device);
		assertEquals("firefox", browser);
		assertEquals("31", browserVer);
	}
	
}
