package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ReadStatsFromReport extends DBBridge{

	Logger log = LogManager.getLogger(ReadStatsFromReport.class);
	
	String GET_REPORTS_BY_SUITEID = "SELECT timestamp, result, time FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC;";
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
		//Build the MegaQuery from Mordor. (One dose not simply ask the database for data) 
		String sql = getQueryFromJsonObject(params, minTimestamp);
		log.debug("SQL: {}", sql);
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
	
	@Deprecated
	protected List<HashMap<String, String>> extractResultSetToGraphData(ResultSet rs) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
    	try {
    		String currentTimestamp = null;
    		float time = 0;
    		int fail = 0;
    		int pass = 0;
    		int error = 0;
			while (rs.next()) {
				boolean isCurrentTimestampNull = currentTimestamp == null;
				if (isCurrentTimestampNull || !currentTimestamp.equals(rs.getString("timestamp"))) {
					if (!isCurrentTimestampNull) {
						HashMap<String, String> hashMap = new HashMap<String,String>();
						hashMap.put("timestamp", currentTimestamp);
						hashMap.put("time", ""+time);
						hashMap.put("pass", ""+pass);
						hashMap.put("fail", ""+fail);
						hashMap.put("error", ""+error);
						result.add(hashMap);
					}
					currentTimestamp = rs.getString("timestamp");
					fail = 0;
					pass = 0;
					error = 0;
					time = 0f;
				}
				String res = rs.getString("result");
				switch (res) {
				case "passed":
					pass+=1;
					break;
				case "error":
					error+=1;
					break;
				case "failure":
					fail+=1;
				default:
					break;
				}
				time+=rs.getFloat("time");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return result;
    }
	
	@Deprecated
	public List<HashMap<String, String>> getReportListData(int suiteid){
	    	HashMap<String, String> map = new HashMap<>();
	    	map.put("suiteid", ""+suiteid);
	    	String sql = stringParser.getString(GET_REPORTS_BY_SUITEID, map);
	    	ResultSet rs = readFromDB(sql);
	    	List<HashMap<String, String>> result = extractResultSetToGraphData(rs);
	    	return result;
	    }
	 
	 protected HashMap<Long, JsonObject> extractGraphData(ResultSet rs) {
	    	HashMap<Long, JsonObject> graphMap = new HashMap<Long, JsonObject>();
	    	try {
				while (rs.next()) {
					JsonObject timestamp = new JsonObject();
					long timestampValue = rs.getLong("timestamp");
					timestamp.add("timestamp", new JsonPrimitive(timestampValue));
					timestamp.add("time", new JsonPrimitive(rs.getDouble("time")));
					timestamp.add("pass", new JsonPrimitive(rs.getInt("passed")));
					timestamp.add("fail", new JsonPrimitive(rs.getInt("failure")));
					timestamp.add("error", new JsonPrimitive(rs.getInt("error")));
					timestamp.add("skipped", new JsonPrimitive(rs.getInt("skipped")));
					graphMap.put(timestampValue, timestamp);
				}
			} catch (SQLException e) {
				return null;
			}
	    	return graphMap;
	    }
}
