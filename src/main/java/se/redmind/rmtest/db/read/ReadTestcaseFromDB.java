package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestcaseFromDB extends DBBridge {

    public static Connection conn;
    String GET_TESTCASE_ID = "select testcase_id from testcase where name= ";
    String GET_TESTCASE_FROM_CLASS_ID = "SELECT name, testcase_id FROM testcase WHERE class_id = ";


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
    	List<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
    	try {
			while (rs.next()) {
				HashMap<String,String> row = new HashMap<String,String>();
				row.put("name", rs.getString("name"));
				row.put("id", rs.getString("testcase_id"));
				result.add(row);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return result;
    }


}
