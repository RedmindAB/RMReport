package se.redmind.rmtest.web.route.api.suite.byid;

import java.sql.ResultSet;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.api.suite.SuiteJsonBuilder;

public class GetLatestSuiteDAO extends DBBridge{

	public String getLatestSuite(int suite_id){
		JsonArray array = getLastestSuiteRunFromID(suite_id);
		return new Gson().toJson(array);
	}
	 public JsonArray getLastestSuiteRunFromID(int suiteid){
		 	String GET_SUITE_MAX = "SELECT report.class_id, class.classname, report.testcase_id, testcase.testcasename, COUNT(result) AS totalresult, SUM(result = 'error' OR result = 'failure') AS fail, SUM(result = 'skipped') AS skipped, SUM(time) AS time FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = (SELECT MAX(timestamp) FROM report WHERE suite_id = {suite_id}) AND suite_id = {suite_id} GROUP BY report.testcase_id;";
	    	HashMap<String, String> map = new HashMap<String, String>();
	    	map.put("suite_id", ""+suiteid);
	    	ResultSet rs = readFromDB(stringParser.getString(GET_SUITE_MAX, map));
	    	return new SuiteJsonBuilder(rs).build().getSuite();
	    }
	
}
