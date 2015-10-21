package se.redmind.rmtest.report.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.parser.xml.XMLReport;
import se.redmind.rmtest.report.parser.xml.XMLReportTestCase;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportXMLParserTest {

	private static ReportLoader loader;
	private static ReportXMLParser parser;
	private static String testFileName;
	private static File file;
	
	@BeforeClass
	public static void beforeClass(){
		loader = new ReportLoader(ReportLoaderTest.path, false);
		parser = new ReportXMLParser();
		testFileName = ReportLoaderTest.specificReportFileName;  
		file = loader.getReportByFileName(testFileName);
	}
	
	@Test
	public void getTestCaseList() {
		NodeList list = parser.getNodeList(file, "testcase");
		assertEquals(64, list.getLength());
	}
	
	@Test
	public void createTestcaseFromFile(){
		NodeList list = parser.getNodeList(file, "testcase");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			XMLReportTestCase testCase = (XMLReportTestCase) new XMLReportTestCase(element, "hej").build();
			boolean testNameIsNotEmpty = testCase.getName().length() > 0;
			assertTrue(testNameIsNotEmpty);
		}
	}
	
	@Test
	public void createReportObject(){
		XMLReport report = parser.getReportFromFile(file);
		report.build();
		assertEquals("test.java.se.redmind.rmtest.selenium.example.MockedTestSuite(20150101-080000)", report.getName());
		List<ReportTestCase> caseList = report.getTestCaseArray();
		assertEquals(64, caseList.size());
	}
	
	@Test
	public void convertSimpleReportToFullReport(){
		XMLReport report = parser.getSimpleReportFromFile(file);
		report.build();
		assertTrue(report.isSimpleReport());
		report.convertToFullReport();
		assertFalse(report.isSimpleReport());
		assertEquals(64, report.getTestCaseArray().size());
	}
	
	@Test
	public void convertPropName(){
		XMLReport report = parser.getSimpleReportFromFile(file);
		report.build();
		String convertedString = report.removePunctuations("dot.dot.dot", "");
		assertEquals("dotdotdot", convertedString);
	}
	
	@Test
	public void containsTestClasses(){
		XMLReport report = parser.getReportFromFile(file);
		report.build();
		List<String> classes = report.getPresentTestClasses();
		assertEquals(2, classes.size());
		assertTrue(classes.contains("se.redmind.rmtest.selenium.example.RandomClass0"));
		assertTrue(classes.contains("se.redmind.rmtest.selenium.example.RandomClass1"));
	}

	@Test
	public void getTestCaseDriverValues(){
		XMLReport report = parser.getReportFromFile(file);
		report.build();
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
	
	@Test
	public void checkForSkippedTests(){
		XMLReport report = parser.getReportFromFile(file);
		report.build();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int skipped = 0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			if (reportTestCase.getResult().equals("skipped")) {
				skipped++;
			}
		}
		assertEquals(0, skipped);
	}
	
	@Test
	public void getSuiteName(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		String suiteName = report.getSuiteName();
		assertEquals("MockedTestSuite", suiteName);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void checkFailures(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int failCount = 0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("failure")) {
				failCount++;
			}
		}
		assertEquals(24, failCount);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void checkErrors(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int errorCount = 0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("error")) {
				errorCount++;
			}
		}
		assertEquals(4, errorCount);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void checkPassed(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int passedCount = 0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("passed")) {
				passedCount++;
			}
		}
		assertEquals(36, passedCount);
	}
	
	@Test
	public void checkRunTime(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		for (ReportTestCase reportTestCase : testCaseArray) {
			Driver driver = reportTestCase.getDriver();
			String device = driver.getDevice();
			switch (device) {
			case "Nexus 6":
				assertEquals(2.0, reportTestCase.getTime(), 0.0);
				break;
			case "iPhone 6":
				assertEquals(2.5, reportTestCase.getTime(), 0.0);
				break;
			case "HTC ONE":
				assertEquals(1.0, reportTestCase.getTime(), 0.0);
				break;
			case "UNKNOWN":
				assertEquals(3.0, reportTestCase.getTime(), 0.0);
				break;
			default:
				break;
			}
		}
	}
	
	@Test
	public void getParameters(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		HashMap<String, String> parameters = report.getParameters();
		assertEquals(1, parameters.size());
		String superBranch = parameters.get("rmreport.branch");
		assertNotNull(superBranch);
		assertEquals("superBranch", superBranch);
	}
	
	@Test
	public void customReportName(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		assertEquals("customSuiteName", report.getSuiteName());
	}
	
	@Test
	public void getTheDefaultSuiteName(){
		XMLReport report = (XMLReport) parser.getReportFromFile(file).build();
		report.getParameters().remove("rmreport.suitename");
		assertEquals("MockedTestSuite", report.getSuiteName());
	}
}
