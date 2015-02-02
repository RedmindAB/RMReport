package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by johan on 15-01-26.
 */
public class ReadReportFromDB extends DBBridge{

    String GET_MAX_ID_FROM_REPORT = "select * from report order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from report where timestamp =";
    String GET_ALL_REPORT_NAMES = "select name from report";
    String GET_ID_FROM_REPORTNAME = "select id from report where name =";
    String GET_DATE_AND_TIME_FROM_REPORT_AFTER = "select * from report where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORT_BEFORE = "select * from report where timestamp <";
    String GET_RUNTIME_FROM_REPORT = "select time from reports where name =";
    String GET_RUNTIME_FROM_ALL_REPORT = "";
    String GET_DRIVER_FROM_REPORT = "select distinct driver from report where suite_id = ";
    String AND_TESTCASE_ID = "and testcase_id =";

    public HashMap getDriverFromTestcase(Integer suite_id, Integer testcase_id){

        ResultSet rs = readFromDB(GET_DRIVER_FROM_REPORT+""+AND_TESTCASE_ID+"");

        return null;
    }

    public Integer getMaxID(){
    	ResultSet rs = readFromDB(GET_MAX_ID_FROM_REPORT);
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean reportExists(String reportTimeStamp){
        ResultSet rs = readFromDB(REPORT_EXISTS + "'" + reportTimeStamp + "'" + "limit 1");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashSet<String> getAllReportNames(){
        HashSet<String> hs = new HashSet<String>();
        ResultSet rs = readFromDB(GET_ALL_REPORT_NAMES);
        try {
            while(rs.next())
                hs.add(rs.getString(1));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }
    
}
