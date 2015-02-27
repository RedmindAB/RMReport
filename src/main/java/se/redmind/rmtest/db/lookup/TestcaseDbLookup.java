package se.redmind.rmtest.db.lookup;



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
public class TestcaseDbLookup extends DBBridge {

    public static Connection conn;
    String GET_TESTCASE_ID = "select testcase_id from testcase where testcasename= ";
   
    String GET_DRIVER_BY_TESTCASE_ID = "SELECT DISTINCT driver FROM REPORT WHERE testcase_id = ";
    
    String GET_ALL_FROM_TESTCASE = "SELECT * FROM testcase";

    public int getTestCaseID(String testCaseName){
        ResultSet rs = readFromDB(GET_TESTCASE_ID+"'"+testCaseName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
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

   

}
