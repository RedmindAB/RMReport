package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.util.HashMap;

import com.google.gson.JsonArray;

import se.redmind.rmtest.db.DBBridge;

public class ReadGraphOptions extends DBBridge {
	
	public static final String GRAPH_OPTIONS = "SELECT DISTINCT  os.osname, os.osversion, os.os_id, device.devicename, device.device_id, browser.browsername, browser.browser_id FROM report NATURAL JOIN os,browser,device WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC LIMIT {limit})) AND report.device_id = device.device_id AND report.os_id = os.os_id AND browser.browser_id = report.browser_id AND report.suite_id = {suiteid};";

	public JsonArray getGraphOptions(int suiteid, int limit){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("suiteid", ""+suiteid);
		map.put("limit", ""+limit);
		String sql = stringParser.getString(GRAPH_OPTIONS, map);
		ResultSet readFromDB = readFromDB(sql);
		return null;
	}
	
	private JsonArray handleGraphData(){
		
		
		return null;
	}
	
}
