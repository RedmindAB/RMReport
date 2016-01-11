package se.redmind.rmtest.web.route.api.suite.parameters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.route.api.ErrorResponse;

public class SuiteParametersDAO extends DBBridge {

	public JsonObject getParameters(String suiteid, String timestamp){
		JsonObject parameters = new JsonObject();
		String sql = "SELECT parameter, value FROM parameters WHERE suite_id = "+suiteid+" AND timestamp = "+timestamp;
		ResultSet rs = readFromDB(sql);
		try {
			while (rs.next()) {
				String parameter = rs.getString("parameter");
				String value = rs.getString("value");
				parameters.addProperty(parameter, value);
			}
		} catch (SQLException e) {
			return new ErrorResponse(e.getMessage(), SuiteParametersDAO.class).getJson();
		}
		return parameters;
	}
	
}
