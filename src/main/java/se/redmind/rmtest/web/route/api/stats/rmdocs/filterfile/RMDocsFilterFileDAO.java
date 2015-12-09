package se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;

public class RMDocsFilterFileDAO extends DBBridge {

	private final String sql = 	"SELECT class.classname, testcase.testcasename, AVG(report.result = 'passed') * 100 AS medel, SUM(report.result != 'passed') AS totalfail, COUNT(report.result) AS totaltests, browser.browsername "
								+"FROM report INNER JOIN class ON (class.class_id = report.class_id) INNER JOIN testcase ON (testcase.testcase_id = report.testcase_id) INNER JOIN browser ON (report.browser_id = browser.browser_id) "
								+"WHERE "
								+"report.timestamp >= (SELECT DISTINCT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE report.suite_id = ? ORDER BY timestamp DESC LIMIT ?)) "
								+"AND "
								+"report.suite_id = ? "
								+"GROUP BY testcase.testcase_id, report.browser_id "
								+"ORDER BY class.classname ,testcase.testcasename DESC;";
	
	
	public String getResultsAsText(int suiteid, int limit, int threshhold){
		JsonArray result = getResult(suiteid, limit);
		return new RMDocsFilterfFileBuilder(result, threshhold).getAsText();
	}
	
	public byte[] getResultsAsBytes(int suiteid, int limit, int threshhold){
		return getResultsAsText(suiteid, limit, threshhold).getBytes();
	}


	private JsonArray getResult(int suiteid, int limit) {
		PreparedStatement prepareStatement = prepareStatement(sql);
		JsonArray jArray = new JsonArray();
		try {
			prepareStatement.setInt(1, suiteid);
			prepareStatement.setInt(2, limit);
			prepareStatement.setInt(3, suiteid);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("classname", rs.getString(1));
				jsonObject.addProperty("testcasename", rs.getString(2));
				jsonObject.addProperty("medel", rs.getDouble(3));
				jsonObject.addProperty("totalfail", rs.getInt(4));
				jsonObject.addProperty("totaltests", rs.getInt(5));
				jsonObject.addProperty("browsername", rs.getString(6));
				jArray.add(jsonObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jArray;
	}
	
}
