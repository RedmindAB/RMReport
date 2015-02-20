package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


/**
 * Created by johan on 15-01-29.
 */
public class ReadSuiteFromDB extends DBBridge{

    String GET_SUIT_ID = "select suite_id from suite where suitename= ";
    String GET_ALL_SUITS = "select * from suite";
    String GET_SUITE_MAX = "SELECT report.class_id, class.classname, report.testcase_id, testcase.testcasename, COUNT(result) AS totalresult, SUM(result = 'error' OR result = 'failure') AS fail, SUM(result = 'skipped') AS skipped, SUM(time) AS time FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = (SELECT MAX(timestamp) FROM report WHERE {suite_id}) AND suite_id = {suite_id} GROUP BY report.testcase_id;";
    String GET_SUITE_BY_TIMESTAMP = "SELECT report.class_id, class.classname, report.testcase_id, testcase.testcasename, COUNT(result) AS totalresult, SUM(result = 'error' OR result = 'failure') AS fail, SUM(result = 'skipped') AS skipped, SUM(time) AS time FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = '{timestamp}' AND suite_id = {suite_id} GROUP BY report.testcase_id;";
    
    String GET_SUITE_SPECIFIC = "select report.class_id, class.classname, report.testcase_id, testcase.testcasename, result, timestamp, time from report inner join class on report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id where timestamp = '";
    String AND_SUITEID_ ="' AND suite_id = ";
    String GROUP_BY_ = " GROUP BY report.testcase_id;";

    public int getSuiteID(String suitName){
        ResultSet rs = readFromDB(GET_SUIT_ID+"'"+suitName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
    }

    public List<HashMap<String,Object>> getAllSuites(){
        ResultSet rs = readFromDB(GET_ALL_SUITS);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            while(rs.next()) {
            	HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", rs.getString("suitename"));
                hm.put("id", rs.getString("suite_id"));
                result.add(hm);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public JsonArray getLastestSuiteRunFromID(int suiteid){
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("suite_id", ""+suiteid);
    	ResultSet rs = readFromDB(stringParser.getString(GET_SUITE_MAX, map));
    	return new SuiteJsonBuilder(rs).build().getSuite();
    }
    
    public JsonArray getSuiteRunByTimestamp(int suiteid, String timestamp){
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("suite_id", ""+suiteid);
    	map.put("timestamp", timestamp);
    	String sql = stringParser.getString(GET_SUITE_BY_TIMESTAMP, map);
    	ResultSet rs = readFromDB(sql);
    	return new SuiteJsonBuilder(rs).build().getSuite();
    }
    
    
    public JsonArray getSpecificSuiteRunFromIdAndTimestamp(int suiteid, String timestamp){
    	ResultSet rs = readFromDB(GET_SUITE_SPECIFIC+timestamp+AND_SUITEID_+suiteid+GROUP_BY_);
    	JsonArray array = new JsonArray(); 
    	try {
			while(rs.next()){
				JsonObject jsonObject = new JsonObject();
				jsonObject.add("classid", new JsonPrimitive(rs.getInt("class_id")));
				jsonObject.add("classname", new JsonPrimitive(rs.getString("classname")));
				jsonObject.add("testcaseid", new JsonPrimitive(rs.getInt("testcase_id")));
				jsonObject.add("testcasename", new JsonPrimitive(rs.getString("testcasename")));
				jsonObject.add("result", new JsonPrimitive(rs.getString("result")));
				jsonObject.add("time", new JsonPrimitive(rs.getFloat("time")));
				array.add(jsonObject);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		return array;
    }
    
}
