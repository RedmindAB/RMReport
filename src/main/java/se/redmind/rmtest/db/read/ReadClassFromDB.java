package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johan on 15-01-29.
 */
public class ReadClassFromDB extends DBBridge{

    String GET_SUITE_CLASS_CASE_ID = "select class.name from class inner join testcase on testcase.testcase_id = class.class_id";
    String GET_CLASS_ID = "select class_id from class where name =";
    String GET_CLASS_FROM_SUITE_ID = "SELECT DISTINCT class.name, class.class_id FROM report INNER JOIN class ON class.class_id = report.class_id WHERE suite_id = ";

    public int getClassID(String className){
        ResultSet rs = readFromDB(GET_CLASS_ID+"'"+className+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
           // e.printStackTrace();
        }
            return -1;
    }

    public HashMap<String, String> getClassNameOnTestcaseId(){
        HashMap<String, String> hm = new HashMap<String, String>();
        ResultSet rs = readFromDB(GET_SUITE_CLASS_CASE_ID);
        try {
            while(rs.next()) {
                hm.put("Name", "name");
            }
            return hm;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<HashMap<String, Object>> getAllClassNames(int suiteID){
        ResultSet rs = readFromDB(GET_CLASS_FROM_SUITE_ID+suiteID);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            while(rs.next()) {
            	HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", rs.getString("name"));
                hm.put("id", rs.getString("class_id"));
                result.add(hm);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
