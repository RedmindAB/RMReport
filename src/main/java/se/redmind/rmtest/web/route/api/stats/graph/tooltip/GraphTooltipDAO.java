package se.redmind.rmtest.web.route.api.stats.graph.tooltip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GraphTooltipDAO extends DBBridge{

	private String sql = "SELECT DISTINCT device.devicename, os.osname FROM report INNER JOIN device ON device.device_id = report.device_id INNER JOIN os ON report.os_id = os.os_id WHERE suite_id = {suiteid} AND timestamp = {timestamp};";
	private HashMap<String, String> map;
	
	public GraphTooltipDAO(long timestamp, int suiteid) {
		map = new HashMap<String,String>();
		map.put("suiteid", ""+suiteid);
		map.put("timestamp", ""+timestamp);
	}
	
	public JsonArray getResult(){
		JsonArray array = new JsonArray();
		String sql = stringParser.getString(this.sql, map);
		System.out.println(sql);
		ResultSet rs = readFromDB(sql);
		try {
			while (rs.next()) {
				JsonObject json = new JsonObject();
				json.addProperty("devicename", rs.getString("devicename"));
				json.addProperty("osname", rs.getString("osname"));
				array.add(json);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	
	
}
