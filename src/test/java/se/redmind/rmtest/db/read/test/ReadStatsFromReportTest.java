package se.redmind.rmtest.db.read.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.read.ReadStatsFromReport;

public class ReadStatsFromReportTest {

	static ReadStatsFromReport readStatsFromReport;
	static JsonObject params;
	@BeforeClass
	public static void beforeclass(){
		readStatsFromReport = new ReadStatsFromReport();
		params = new JsonObject();
		params.add(ReadStatsFromReport.SUITEID, new JsonPrimitive(1));
		params.add(ReadStatsFromReport.RESLIMIT, new JsonPrimitive(50));
		
		JsonArray driverArray = new JsonArray();
		driverArray.add(new JsonPrimitive("OSX chrome"));
		driverArray.add(new JsonPrimitive("OSX firefox"));
		params.add(ReadStatsFromReport.DRIVERS, driverArray);
		
		JsonArray classArray = new JsonArray();
		classArray.add(new JsonPrimitive(2));
		params.add(ReadStatsFromReport.CLASSES, classArray);
		
		JsonArray testcaseArray = new JsonArray();
		testcaseArray.add(new JsonPrimitive(3));
		params.add(ReadStatsFromReport.TESTCASES, testcaseArray);
	}
	
	
	@Test
	public void test() {
		String queryFromJsonObject = readStatsFromReport.getQueryFromJsonObject(params);
		assertEquals("SELECT timestamp, time, result, report.class_id FROM report WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50)) AND suite_id = 1 AND (driver = 'OSX chrome' OR driver = 'OSX firefox') AND (class_id = 2) AND (testcase_id = 3)ORDER BY timestamp DESC;", queryFromJsonObject);
	}
	
	@Test
	public void getConditions(){
		String s = readStatsFromReport.getConditions(params);
		assertEquals("AND (driver = 'OSX chrome' OR driver = 'OSX firefox') AND (class_id = 2) AND (testcase_id = 3)", s);
	}

}
