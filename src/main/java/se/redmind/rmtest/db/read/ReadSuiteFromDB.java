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

    String GET_SUIT_ID = "select suite_id from suite where name= ";
    String GET_ALL_SUITS = "select * from suite";
    String GET_SUITE_MAX = "SELECT report.class_id, class.name, report.testcase_id, testcase.name, result FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = (SELECT MAX(timestamp) FROM report WHERE suite_id = {suite_id}) GROUP BY report.testcase_id;";
    String GET_SUITE_BY_TIMESTAMP = "SELECT report.class_id, class.name, report.testcase_id, testcase.name, result FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = {timestamp} AND suite_id = {suite_id} GROUP BY report.testcase_id;";
    
    String GET_SUITE_SPECIFIC = "SELECT report.class_id, class.name, report.testcase_id, testcase.name, result, report.time FROM report INNER JOIN class ON report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id WHERE timestamp = (SELECT timestamp FROM report WHERE suite_id =  ";
    String AND_TIMESTAMP_ =" and timestamp =  ";
    String GROUP_BY_ = " ) GROUP BY report.testcase_id;";

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
                hm.put("name", rs.getString("name"));
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
    	ResultSet rs = readFromDB(stringParser.getString(GET_SUITE_MAX, map));
    	return new SuiteJsonBuilder(rs).build().getSuite();
    }
    
    
    public JsonArray getSpecificSuiteRunFromIdAndTimestamp(String timestamp, int suiteid){
    	ResultSet rs = readFromDB(GET_SUITE_SPECIFIC+suiteid+AND_TIMESTAMP_+timestamp+GROUP_BY_);
    	JsonArray array = new JsonArray();
    	try {
			while(rs.next()){
				JsonObject jsonObject = new JsonObject();
				jsonObject.add("driver", new JsonPrimitive(rs.getString("driver")));
				jsonObject.add("timestamp", new JsonPrimitive(rs.getString("timestamp")));
				jsonObject.add("message", new JsonPrimitive(rs.getString("message")));
				jsonObject.add("result", new JsonPrimitive(rs.getString("result")));
				jsonObject.add("time", new JsonPrimitive(rs.getString("time")));
				array.add(jsonObject);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
    }
    
}
