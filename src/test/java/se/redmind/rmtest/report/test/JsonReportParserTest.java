package se.redmind.rmtest.report.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.json.JsonReport;
import se.redmind.rmtest.report.parser.json.JsonReportBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import static org.junit.Assert.*;

public class JsonReportParserTest{

	private File jsonFile = new File(System.getProperty("user.dir")+"/statictests/testRes.json");
	private JsonObject reportJson;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@Before
	public void before(){
		reportJson = new Gson().fromJson(getJson(jsonFile), JsonObject.class);
	}

	private String getJson(File jsonFile) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null; 
		try {
			br = new BufferedReader(new FileReader(jsonFile));
			String line = null;
			while ((line = br.readLine())!=null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}

	@Test
	public void getTestCaseList() {
		JsonReport jsonReport = getReport();
		List<ReportTestCase> testCaseArray = jsonReport.getTestCaseArray();
		assertEquals(8, testCaseArray.size());
	}

	@Test
	public void getSuiteName() {
		JsonReport jsonReport = getReport();
		String suiteName = jsonReport.getSuiteName();
		assertEquals("GoogleTestsRMR", suiteName);
	}

	@Test
	public void checkFailures() {
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int tests=0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("failure")) {
				tests++;
			}
		}
		assertEquals(2, tests);
	}

	@Test
	public void checkErrors() {
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int tests=0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("failure")) {
				tests++;
			}
		}
		assertEquals(2, tests);
	}

	@Test
	public void checkPassed() {
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int tests=0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if (result.equals("passed")) {
				tests++;
			}
		}
		assertEquals(6, tests);
	}

	@Test
	public void checkRunTime() {
		JsonReport report = getReport();
		double time = report.getTime();
		assertEquals(17.558, time, 0.0);
	}
	
	@Test
	public void checkAllTimes(){
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		for (ReportTestCase reportTestCase : testCaseArray) {
			double time = reportTestCase.getTime();
			assertTrue(time+ " is not bigger than 0", time >= 0);
		}
	}
	
	@Test
	public void checkDrivers(){
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		for (ReportTestCase reportTestCase : testCaseArray) {
			Driver driver = reportTestCase.getDriver();
			assertNotNull(driver);
		}
	}
	
	@Test
	public void getParameters(){
		JsonReport report = getReport();
		HashMap<String, String> parameters = report.getParameters();
		assertNotNull(parameters);
		assertEquals(2, parameters.size());
	}
	
	@Test
	public void getCustomSuiteName(){
		JsonReport report = getReport();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("rmreport.suitename", "customSuiteName");
		report.setParameters(params);
		String suiteName = report.getSuiteName();
		assertEquals("customSuiteName", suiteName);
	}
	
	@Test
	public void testSkippedTests(){
		JsonReport report = getReport();
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		int skipped = 0;
		for (ReportTestCase reportTestCase : testCaseArray) {
			String result = reportTestCase.getResult();
			if(result.equals("skipped")) skipped++;
		}
		assertEquals(2, skipped);
	}
	
	private JsonReport getReport(){
		return new JsonReportBuilder(reportJson).build();
	}

}
