package se.redmind.rmtest.db.read;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.DBBridge;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestcaseFromDB extends DBBridge {

    public static Connection conn;
    String GET_TESTCASE_ID = "select testcase_id from testcase where name= ";
    String GET_TESTCASE_FROM_CLASS_ID = "SELECT name, testcase_id FROM testcase WHERE class_id = ";
    String GET_DRIVER_BY_TESTCASE_ID = "SELECT DISTINCT driver FROM REPORT WHERE testcase_id = ";
    String GET_ALL_FROM_TESTCASE = "SELECT * FROM testcase";
    String GET_DRIVER_AND_MESSAGE_ = "select driver, result, message from report where testcase_id = ";
    String FROM_LAST_RUN = " and timestamp = (select max(timestamp) from report);";
    String GET_DRIVER_ = "select distinct driver from report where testcase_id =  ";
    String AND_TIMESTAMP_FROM_HISTORY_ = " and timestamp != (select max(timestamp) from report)";
    
    public int getTestCaseID(String testCaseName){
        ResultSet rs = readFromDB(GET_TESTCASE_ID+"'"+testCaseName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
    }
    
    public List<HashMap<String, String>> getTestCasesFromClassID(int id){
    	ResultSet rs = readFromDB(GET_TESTCASE_FROM_CLASS_ID+id);
    	List<HashMap<String, String>> result = new ArrayList<>();
    	try {
			while (rs.next()) {
				HashMap<String,String> row = new HashMap<>();
				row.put("name", rs.getString("name"));
				row.put("id", rs.getString("testcase_id"));
				result.add(row);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return result;
    }
    public List<HashMap<String, String>> getDriverFromTestcaseID(int id){
        ResultSet rs = readFromDB(GET_DRIVER_BY_TESTCASE_ID +id);
        List<HashMap<String, String>> result = new ArrayList<>();
        try {
            while (rs.next()) {
                HashMap<String,String> row = new HashMap<>();
                row.put("driver", rs.getString("driver"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
    
    public HashMap<String,Integer> getAllFromTestcaseConcat(){
    	ResultSet rs = readFromDB(GET_ALL_FROM_TESTCASE);
    	HashMap<String, Integer> hs = new HashMap<>();
    	try {
			while(rs.next()){
				String nameAndClassId = rs.getString(1)+rs.getInt(3);
				hs.put(nameAndClassId, rs.getInt(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hs;
    }
    public JsonArray getDriverAndMessageFromLastRun(int testcaseId){
    	ResultSet rs = readFromDB(GET_DRIVER_AND_MESSAGE_+testcaseId+FROM_LAST_RUN);
    	ResultSet rs2 = readFromDB(GET_DRIVER_+testcaseId+AND_TIMESTAMP_FROM_HISTORY_);
    	JsonArray array = new JsonArray();
    	try {
			while(rs.next()){
				while(rs2.next()){
				
				JsonObject jsonObject = new JsonObject();
				
				jsonObject.add("driver", new JsonPrimitive(rs.getString("driver")));
				jsonObject.add("result", new JsonPrimitive(rs.getString("result")));
				jsonObject.add("message", new JsonPrimitive(rs.getString("message")));
				jsonObject.add("driverHistory", new JsonPrimitive(rs2.getString("driver")));
				array.add(jsonObject);
				
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
    	  	
    }

}
