package se.redmind.rmtest.web.route.api.suite.bytimestamp;

import java.sql.ResultSet;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.db.read.ReadSuiteFromDB;
import se.redmind.rmtest.db.read.SuiteJsonBuilder;

public class GetSuiteByTimestampDAO extends DBBridge{

	public String getSuiteByTimestamp(int suite_id, String timestamp){
		JsonArray array = getSuiteRunByTimestamp(suite_id, timestamp);
		return new Gson().toJson(array);
	}
	public JsonArray getSuiteRunByTimestamp(int suiteid, String timestamp){
		String GET_SUITE_BY_TIMESTAMP = "SELECT report.class_id, class.classname, report.testcase_id, testcase.testcasename, COUNT(result) AS totalresult, SUM(result = 'error' OR result = 'failure') AS fail, SUM(result = 'skipped') AS skipped, SUM(time) AS time FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = '{timestamp}' AND suite_id = {suite_id} GROUP BY report.testcase_id;";
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("suite_id", ""+suiteid);
    	map.put("timestamp", timestamp);
    	String sql = stringParser.getString(GET_SUITE_BY_TIMESTAMP, map);
    	ResultSet rs = readFromDB(sql);
    	return new SuiteJsonBuilder(rs).build().getSuite();
    }
	
	
}
