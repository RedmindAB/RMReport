package se.redmind.rmtest.web.route.api.stats.grahoptions;

import java.sql.ResultSet;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GetGraphOptionsDAO extends DBBridge{

	public String getGraphData(int suiteid, int limit){
		JsonObject json = getGraphOptions(suiteid, limit);
		return new Gson().toJson(json);
	}
	public JsonObject getGraphOptions(int suiteid, int limit){
		String GRAPH_OPTIONS = "SELECT DISTINCT  os.osname, os.osversion, os.os_id, device.devicename, device.device_id, browser.browsername, browser.browser_id, browser.browserversion FROM report NATURAL JOIN os,browser,device WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC LIMIT {limit})) AND report.device_id = device.device_id AND report.os_id = os.os_id AND browser.browser_id = report.browser_id AND report.suite_id = {suiteid};";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("suiteid", ""+suiteid);
		map.put("limit", ""+limit);
		String sql = stringParser.getString(GRAPH_OPTIONS, map);
		ResultSet readFromDB = readFromDB(sql);
		return new GraphOptionsBuilder(readFromDB).buildJson();
	}
	
}
