package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.db.DBBridge;

public class ReadStatsFromReport extends ReadReportFromDB{

	public static final String DRIVERS = "drivers";
	public static final String TESTCASES = "testcases";
	public static final String CLASSES = "classes";
	public static String RESLIMIT = "reslimit", SUITEID = "suiteid", CLASSID = "class_id",CONDITIONS = "conditions";
	private String megaQuery = "SELECT timestamp, time, result, report.class_id FROM report "
									+ "WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC LIMIT {reslimit}))"
									+ " AND suite_id = {suiteid} "
									+ "{conditions}"
									+ "ORDER BY timestamp DESC;";
	private String SUITE = "suite_id = ";
	private String DRIVER = "driver = ";
	private String CLASS = "class_id = ";
	private String TESTCASE = "testcase_id = ";
	
	public List<HashMap<String, String>> getGraphData(JsonObject params){
		String sql = getQueryFromJsonObject(params);
		ResultSet rs = readFromDB(sql);
		return extractResultSetToGraphData(rs);
	}
	
	public String getQueryFromJsonObject(JsonObject params){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(SUITEID, params.get(SUITEID).getAsString());
		map.put(RESLIMIT, params.get(RESLIMIT).getAsString());
		map.put(CONDITIONS, getConditions(params));
		return stringParser.getString(megaQuery, map);
	}
	
	public String getConditions(JsonObject params){
		StringBuilder sb = new StringBuilder();
		JsonArray driverArray = params.get(DRIVERS).getAsJsonArray();
		if (driverArray.size() > 0) {
		sb.append("AND ");
			generateCondition(sb, driverArray, DRIVER, true);
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
		generateCondition(sb, conditionArray, template, false);
	}
	
	private void generateCondition(StringBuilder sb, JsonArray conditionArray, String template, boolean isString) {
		sb.append("(");
		int index = 0;
		for (JsonElement condition : conditionArray) {
			String conditionToAdd = null;
			if (isString) {
				conditionToAdd = "'"+condition.getAsString()+"'";
			}else conditionToAdd = condition.getAsString();
			sb.append(template+conditionToAdd);
			index++;
			if (index != conditionArray.size()) {
				sb.append(" OR ");
			}
		}
		sb.append(")");
	}
	
}