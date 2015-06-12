package se.redmind.rmtest.web.route.api.stats.methodfail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class MethodFailDAO extends DBBridge{
	
	private HashMap<String, String> map;
	private String sql = "SELECT report.timestamp, testcase.testcasename, testcase.class_id, report.result, class.classname FROM report INNER JOIN testcase ON (report.testcase_id = testcase.testcase_id) INNER JOIN class ON report.class_id = class.class_id WHERE report.suite_id = {suiteid} AND report.timestamp >= {timestamp};";
	private int maxResults;
	private int suiteid;
	
	public MethodFailDAO(int suiteid, long minTimestamp, int limit, int maxResults) {
		this.suiteid = suiteid;
		this.maxResults = maxResults;
		map = new HashMap<String,String>();
		map.put("suiteid", ""+suiteid);
		map.put("timestamp", ""+minTimestamp);
	}
	
	public JsonArray getResult(){
		String sql = stringParser.getString(this.sql, map);
		ResultSet rs = readFromDB(sql);
		HashSet<String> maxTimestampsMethodClassKeys = getMaxTimestampsMethodClassKeys(this.suiteid);
		MethodFailJsonBuilder builder = new MethodFailJsonBuilder(maxTimestampsMethodClassKeys);
		try {
			while (rs.next()) {
				long timestamp = rs.getLong("timestamp");
				String classname = rs.getString("classname");
				String testcaseName = rs.getString("testcasename");
				String result = rs.getString("result");
				builder.addTestCase(testcaseName, classname, result, timestamp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.build(maxResults);
	}
	

	private HashSet<String> getMaxTimestampsMethodClassKeys(int suiteid){
		HashSet<String> keySet = new HashSet<String>();
		String sql = "SELECT DISTINCT testcase.testcasename, class.classname FROM report INNER JOIN testcase ON (report.testcase_id = testcase.testcase_id) INNER JOIN class ON report.class_id = class.class_id WHERE report.suite_id = "+suiteid+" AND report.timestamp >= (SELECT MAX(timestamp) FROM report);";
		ResultSet rs = readFromDB(sql);
		try {
			while (rs.next()) {
				keySet.add(rs.getString("classname")+rs.getString("testcasename"));
			}
		} catch (SQLException e) {
			
		}
		return keySet;
	}
	
}
