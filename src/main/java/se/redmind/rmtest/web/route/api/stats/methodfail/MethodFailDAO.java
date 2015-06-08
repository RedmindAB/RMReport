package se.redmind.rmtest.web.route.api.stats.methodfail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.JsonArray;

import se.redmind.rmtest.db.DBBridge;

public class MethodFailDAO extends DBBridge{
	
	private HashMap<String, String> map;
	private String sql = "SELECT report.timestamp, testcase.testcasename, testcase.class_id, report.result, class.classname FROM report INNER JOIN testcase ON (report.testcase_id = testcase.testcase_id) INNER JOIN class ON report.class_id = class.class_id WHERE report.suite_id = {suiteid} AND report.timestamp >= {timestamp};";
	private int maxResults;
	
	public MethodFailDAO(int suiteid, long minTimestamp, int limit, int maxResults) {
		this.maxResults = maxResults;
		map = new HashMap<String,String>();
		map.put("suiteid", ""+suiteid);
		map.put("timestamp", ""+minTimestamp);
	}
	
	public JsonArray getResult(){
		String sql = stringParser.getString(this.sql, map);
		ResultSet rs = readFromDB(sql);
		MethodFailJsonBuilder builder = new MethodFailJsonBuilder();
		try {
			while (rs.next()) {
				String classname = rs.getString("classname");
				String testcaseName = rs.getString("testcasename");
				String result = rs.getString("result");
				builder.addTestCase(testcaseName, classname, result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.build(maxResults);
	}

	
	
}
