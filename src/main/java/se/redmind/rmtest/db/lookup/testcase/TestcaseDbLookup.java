package se.redmind.rmtest.db.lookup.testcase;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

/**
 * Created by johan on 15-01-29.
 */
public class TestcaseDbLookup extends DBBridge {

    public static Connection conn;
    String GET_TESTCASE_ID = "SELECT testcase_id FROM testcase WHERE testcasename= ";
    String AND_CLASS_ID = " AND class_id= ";
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

    public int getTestCaseID(String testCaseName, String class_id){
        ResultSet rs = readFromDB(GET_TESTCASE_ID+"'"+testCaseName+"'"+AND_CLASS_ID+"'"+class_id+"'");
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
