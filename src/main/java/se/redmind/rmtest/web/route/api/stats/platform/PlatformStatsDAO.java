package se.redmind.rmtest.web.route.api.stats.platform;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.route.api.util.timestamp.TimestampUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class PlatformStatsDAO extends DBBridge{

	private String sql = "SELECT DISTINCT os.osname FROM report NATURAL JOIN os WHERE report.os_id = os.os_id AND report.timestamp >= {timestamp} AND report.suite_id = {suiteid} GROUP BY report.os_id;";
	
	private long timestamp;
	private int suiteid;

	private int limit;

	public PlatformStatsDAO(int suiteid, int limit) {
		this.suiteid = suiteid;
		this.limit = limit;
		this.timestamp = TimestampUtil.getInstance().getMinTimestamp(suiteid, limit);
	}
	
	public JsonArray getResult(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("timestamp", ""+timestamp);
		map.put("suiteid", ""+suiteid);
		String sql = stringParser.getString(this.sql, map);
		ResultSet rs = readFromDB(sql, this.limit);
		return handleResul(rs);
	}

	private JsonArray handleResul(ResultSet rs) {
		JsonArray array = new JsonArray();
		try {
			while (rs.next()) {
				array.add(new JsonPrimitive(rs.getString("osname")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
}
