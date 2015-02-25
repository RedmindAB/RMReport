package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;

public class ReadStatsFromReport extends ReadReportFromDB{

	public static final String OS = "os";
	public static final String DEVICES = "devices";
	public static final String BROWSERS = "browsers";
	public static final String TESTCASES = "testcases";
	public static final String CLASSES = "classes";
	public static String RESLIMIT = "reslimit", SUITEID = "suiteid", CLASSID = "class_id",CONDITIONS = "conditions";
	private String megaQuery = "SELECT timestamp, SUM(time) AS time, SUM(result = 'passed') AS passed, SUM(result = 'failure') AS failure,  SUM(result = 'error') AS error,  SUM(result = 'skipped') AS skipped FROM report "
									+ "WHERE timestamp >= {minTimestamp}"
									+ " AND suite_id = {suiteid} "
									+ "{conditions}"
									+ " GROUP BY timestamp ORDER BY timestamp;";
	private String SUITE = "suite_id IN ";
	private String OS_ID = "os_id IN ";
	private String DEVICE_ID = "device_id IN ";
	private String BROWSER_ID = "browser_id IN ";
	private String CLASS = "class_id IN ";
	private String TESTCASE = "testcase_id IN ";
	private String GET_TIMESTAMPS = "SELECT DISTINCT timestamp FROM report WHERE suite_id = {suite_id} ORDER BY timestamp LIMIT {reslimit};";
	
	public ResultSet getTimestamps(int reslimit, int suite_id){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("suite_id", ""+suite_id);
		map.put("reslimit", ""+reslimit);
		String sql = stringParser.getString(GET_TIMESTAMPS, map);
		return readFromDB(sql);
	}
	
	public List<HashMap<String, String>> getGraphData(JsonObject params, long minTimestamp){
		String sql = getQueryFromJsonObject(params, minTimestamp);
		ResultSet rs = readFromDB(sql,params.get("reslimit").getAsInt());
		return extractResultSetToGraphData(rs);
	}
	
	public HashMap<Long, JsonObject> getGraphDataAsHashMap(JsonObject params, long minTimestamp){
		String sql = getQueryFromJsonObject(params, minTimestamp);
//		System.out.println(sql);
		int reslimit = params.get("reslimit").getAsInt();
		ResultSet rs = readFromDB(sql, reslimit);
		return extractGraphData(rs);
	}
	
	public String getQueryFromJsonObject(JsonObject params, long minTimestamp){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("minTimestamp", ""+minTimestamp);
		map.put(SUITEID, params.get(SUITEID).getAsString());
		map.put(RESLIMIT, params.get(RESLIMIT).getAsString());
		map.put(CONDITIONS, getConditions(params));
		return stringParser.getString(megaQuery, map);
	}
	
	public String getConditions(JsonObject params){
		StringBuilder sb = new StringBuilder();
		JsonArray osArray = params.get(OS).getAsJsonArray();
		if (osArray.size() > 0) {
		sb.append("AND ");
			generateCondition(sb, osArray, OS_ID);
		}
		JsonArray deviceArray = params.get(DEVICES).getAsJsonArray();
		if (deviceArray.size() > 0) {
		sb.append(" AND ");
			generateCondition(sb, deviceArray, DEVICE_ID);
		}
		JsonArray browserArray = params.get(BROWSERS).getAsJsonArray();
		if (browserArray.size() > 0) {
		sb.append(" AND ");
			generateCondition(sb, browserArray, BROWSER_ID);
		}
		JsonArray classArray = params.get(CLASSES).getAsJsonArray();
		if (classArray.size() > 0) {
			sb.append(" AND ");
			generateCondition(sb, classArray, CLASS);
		}
		JsonArray testCaseArray = params.get(TESTCASES).getAsJsonArray();
		if (testCaseArray.size() > 0) {
			sb.append(" AND ");
			generateCondition(sb, testCaseArray, TESTCASE);
		}
		return sb.toString();
	}

	private void generateCondition(StringBuilder sb, JsonArray conditionArray, String template) {
		int index = 0;
		sb.append(template);
		sb.append("(");
		for (JsonElement condition : conditionArray) {
			String conditionToAdd = null;
			conditionToAdd = condition.getAsString();
			sb.append(conditionToAdd);
			index++;
			if (index != conditionArray.size()) {
				sb.append(",");
			}
		}
		sb.append(")");
	}
}
