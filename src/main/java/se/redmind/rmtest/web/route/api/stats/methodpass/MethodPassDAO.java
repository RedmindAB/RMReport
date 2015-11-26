package se.redmind.rmtest.web.route.api.stats.methodpass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;

public class MethodPassDAO extends DBBridge {

	private final String sql = "SELECT class.classname, testcase.testcasename, AVG(report.result = 'passed') * 100 AS medel "
				+"FROM report INNER JOIN class ON (class.class_id = report.class_id) INNER JOIN testcase ON (testcase.testcase_id = report.testcase_id) "
				+"WHERE "
				+"report.timestamp >= (SELECT DISTINCT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE report.suite_id = ? ORDER BY timestamp DESC LIMIT ?)) "
				+"AND "
				+"report.suite_id = ? "
				+"GROUP BY testcase.testcase_id "
				+"ORDER BY medel DESC;";
	
	public JsonArray getMethodPassRatio(int suite, int limit){
		PreparedStatement prepareStatement = prepareStatement(sql);
		JsonArray ar = new JsonArray();
		try {
			prepareStatement.setInt(1, suite);
			prepareStatement.setInt(3, suite);
			prepareStatement.setInt(2, limit);
			ResultSet rs = prepareStatement.executeQuery();
			while(rs.next()){
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("classname", rs.getString("classname"));
				jsonObject.addProperty("methodname", rs.getString("testcasename"));
				jsonObject.addProperty("averagepass", rs.getDouble("medel"));
				ar.add(jsonObject);
			}
		} catch (SQLException e) {
		}
		return ar;
	}
	
}
