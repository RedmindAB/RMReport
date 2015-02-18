package se.redmind.rmtest.db.read.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.db.read.ReadStatsFromReport;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

public class ReadStatsFromReportTest {

	static ReadStatsFromReport readStatsFromReport;
	static JsonObject params;
	@BeforeClass
	public static void beforeclass(){
		new InMemoryDBHandler().init();
		readStatsFromReport = new ReadStatsFromReport();
		params = new JsonObject();
		params.add(ReadStatsFromReport.SUITEID, new JsonPrimitive(1));
		params.add(ReadStatsFromReport.RESLIMIT, new JsonPrimitive(50));
		
		JsonArray osArray = new JsonArray();
		osArray.add(new JsonPrimitive(1));
		osArray.add(new JsonPrimitive(2));
		params.add(ReadStatsFromReport.OS, osArray);
		
		JsonArray deviceArray = new JsonArray();
		deviceArray.add(new JsonPrimitive(1));
		deviceArray.add(new JsonPrimitive(2));
		params.add(ReadStatsFromReport.DEVICES, deviceArray);
		
		JsonArray browserArray = new JsonArray();
		browserArray.add(new JsonPrimitive(1));
		browserArray.add(new JsonPrimitive(2));
		params.add(ReadStatsFromReport.BROWSERS, browserArray);
		
		JsonArray classArray = new JsonArray();
		classArray.add(new JsonPrimitive(2));
		params.add(ReadStatsFromReport.CLASSES, classArray);
		
		JsonArray testcaseArray = new JsonArray();
		testcaseArray.add(new JsonPrimitive(3));
		params.add(ReadStatsFromReport.TESTCASES, testcaseArray);
		System.out.println(params.toString());
	}
	
	
	@Test
	public void test() {
		String queryFromJsonObject = readStatsFromReport.getQueryFromJsonObject(params);
		assertEquals("SELECT timestamp, SUM(time) AS time, SUM(result = 'passed') AS passed, SUM(result = 'failure') AS failure,  SUM(result = 'error') AS error FROM report WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50)) AND suite_id = 1 AND os_id IN (1,2) AND device_id IN (1,2) AND browser_id IN (1,2) AND class_id IN (2) AND testcase_id IN (3) GROUP BY timestamp ORDER BY timestamp;", queryFromJsonObject);
	}
	
	@Test
	public void getConditions(){
		String s = readStatsFromReport.getConditions(params);
		assertEquals("AND os_id IN (1,2) AND device_id IN (1,2) AND browser_id IN (1,2) AND class_id IN (2) AND testcase_id IN (3)", s);
	}
	
	
	@Test()
	public void getStats(){
		JsonArray reportListData = readStatsFromReport.getGraphDataAsJson(getParams());
		System.out.println(reportListData);
	}
	
	private JsonObject getParams(){
		JsonObject params = new JsonObject();
		params.add(ReadStatsFromReport.SUITEID, new JsonPrimitive(1));
		params.add(ReadStatsFromReport.RESLIMIT, new JsonPrimitive(50));
		
		JsonArray osArray = new JsonArray();
		params.add(ReadStatsFromReport.OS, osArray);
		
		JsonArray deviceArray = new JsonArray();
		params.add(ReadStatsFromReport.DEVICES, deviceArray);
		
		JsonArray browserArray = new JsonArray();
		params.add(ReadStatsFromReport.BROWSERS, browserArray);
		
		JsonArray classArray = new JsonArray();
		params.add(ReadStatsFromReport.CLASSES, classArray);
		
		JsonArray testcaseArray = new JsonArray();
		params.add(ReadStatsFromReport.TESTCASES, testcaseArray);
		return params;
	}
}
