package se.redmind.rmtest.web.route.api.classes.passfail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;

public class PassFailDAO extends DBBridge {

	String query = "SELECT SUM(result == 'passed') AS passed, SUM(result == 'error') AS error, SUM(result == 'failure') AS failure, SUM(result == 'skipped')AS skipped, COUNT(result) AS total FROM report WHERE report.timestamp = {timestamp} AND report.class_id = {classid}";
	
	public JsonObject getPassFail(String timestamp, String classid, String testcaseid){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("classid", classid);
		map.put("timestamp", timestamp);
		String sql = stringParser.getString(query, map);
		return runSqlQuery(sql);
	}
	
	public JsonObject runSqlQuery(String sql){
		JsonObject json = new JsonObject();
		ResultSet rs = readFromDB(sql);
		try {
			while (rs.next()) {
				json.addProperty("passed", rs.getString("passed"));
				json.addProperty("error", rs.getString("error"));
				json.addProperty("failure", rs.getString("failure"));
				json.addProperty("skipped", rs.getString("skipped"));
				json.addProperty("total", rs.getString("total"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}
}
