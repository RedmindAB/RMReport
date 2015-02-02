package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by johan on 15-01-29.
 */
public class ReadClassFromDB extends DBBridge{

    String GET_SUITE_CLASS_CASE_ID = "select class.name from class inner join testcase on testcase.testcase_id = class.class_id";
    String GET_CLASS_ID = "select class_id from class where name =";

    public int getClassID(String className){
        ResultSet rs = readFromDB(GET_CLASS_ID+"'"+className+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return -1;
    }

    public HashMap<String, String> getClassNameOnTestcaseId(){
        HashMap<String, String> hm = new HashMap();
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




}
