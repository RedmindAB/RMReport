package se.redmind.rmtest.report.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportXMLParserTest {

	private static ReportLoader loader;
	private static ReportXMLParser parser;
	private static final String testFileName = "TEST-test.java.se.redmind.rmtest.selenium.example.CreateLogTests-20150121-160906.xml";
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
		assertEquals(12, list.getLength());
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
		assertEquals("test.java.se.redmind.rmtest.selenium.example.CreateLogTests(20150121-160906)", report.getName());
		assertTrue(0.031 == report.getTime());
		assertEquals("10.9.5", report.getProperties().get("osversion").getAsJsonObject().get("value").getAsString());
		assertEquals("os version", report.getProperties().get("osversion").getAsJsonObject().get("readName").getAsString());
		JsonArray caseList = report.getTestCases();
		assertEquals(12, caseList.size());
	}
	
	@Test
	public void convertSimpleReportToFullReport(){
		Report report = parser.getSimpleReportFromFile(file);
		assertNull(report.getTestCases());
		report.convertToFullReport();
		assertEquals(12, report.getTestCases().size());
	}
	
	@Test
	public void convertPropName(){
		Report report = parser.getSimpleReportFromFile(file);
		String convertedString = report.removePunctuations("dot.dot.dot", "");
		assertEquals("dotdotdot", convertedString);
	}

}
