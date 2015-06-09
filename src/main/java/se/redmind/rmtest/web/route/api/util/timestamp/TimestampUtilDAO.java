package se.redmind.rmtest.web.route.api.util.timestamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

public class TimestampUtilDAO extends DBBridge{

	private static String minTimestampSQL = "SELECT MIN(timestamp) AS timestamp FROM (SELECT DISTINCT timestamp FROM report WHERE report.suite_id = {suiteid} LIMIT {limit})";
	
	public TimestampUtilDAO() {
	}

	public long getMinTimestamp(int suiteid, int limit) {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("suiteid", ""+suiteid);
		map.put("limit", ""+limit);
		String sql = stringParser.getString(minTimestampSQL, map);
		long timestamp = 0L;
		ResultSet rs = readFromDB(sql);
		try {
			while (rs.next()) {
				timestamp = rs.getLong("timestamp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	
}
