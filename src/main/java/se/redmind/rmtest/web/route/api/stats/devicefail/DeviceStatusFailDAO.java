package se.redmind.rmtest.web.route.api.stats.devicefail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.route.api.util.timestamp.TimestampUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class DeviceStatusFailDAO extends DBBridge{
	
	Logger log = LogManager.getLogger(DeviceStatusFailDAO.class);
	
	public static String DEVICENAME = "devicename", RESULT="result";
	
	String sql = "SELECT device.devicename, result FROM report NATURAL JOIN device, os WHERE report.suite_id = {suiteid}  AND os.os_id = report.os_id AND report.timestamp >= {timestamp} AND os.osname LIKE '{osname}'";
	HashMap<String, String> map;

	private int limit;

	private String os_name; 
	
	public DeviceStatusFailDAO(int suiteid, String os_name, int limit) {
		this.os_name = os_name;
		this.limit = limit;
		map = new HashMap<String, String>();
		map.put("suiteid", ""+suiteid);
		map.put("timestamp", ""+TimestampUtil.getInstance().getMinTimestamp(suiteid, limit));
		map.put("osname", os_name);
	}
	
	public JsonArray getResult(){
		String sql = stringParser.getString(this.sql, map);
		ResultSet rs = readFromDB(sql, limit);
		JsonArray resArray = new JsonArray();
		try {
			while (rs.next()) {
				JsonObject result = new JsonObject();
				result.add(DEVICENAME, new JsonPrimitive(rs.getString(DEVICENAME)));
				result.add(RESULT, new JsonPrimitive(rs.getString(RESULT)));
				resArray.add(result);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return extractResult(resArray);
	}

	private JsonArray extractResult(JsonArray resArray) {
		DeviceFailResultBuilder deviceFailResultBuilder = new DeviceFailResultBuilder(resArray);
		return deviceFailResultBuilder.getResults();
	}
	
}
