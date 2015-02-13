package se.redmind.rmtest.db.read.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.read.ReadStatsFromReport;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

public class ReadStatsFromReportTest {

	static ReadStatsFromReport readStatsFromReport;
	static JsonObject params;
	@BeforeClass
	public static void beforeclass(){
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
		System.out.println(queryFromJsonObject);
		assertEquals("SELECT timestamp, time, result, report.class_id FROM report WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50)) AND suite_id = 1 AND (driver = 'OSX chrome' OR driver = 'OSX firefox') AND (class_id = 2) AND (testcase_id = 3)ORDER BY timestamp DESC;", queryFromJsonObject);
	}
	
	@Test
	public void getConditions(){
		String s = readStatsFromReport.getConditions(params);
		System.out.println(s);
		assertEquals("AND (driver = 'OSX chrome' OR driver = 'OSX firefox') AND (class_id = 2) AND (testcase_id = 3)", s);
	}
	
//	@Test
//	public void getTestCases(){
//		ReadTestcaseFromDB readTestcaseFromDB = new ReadTestcaseFromDB();
//		System.out.println(readTestcaseFromDB.getAllFromTestcaseConcat());
//	}

}
