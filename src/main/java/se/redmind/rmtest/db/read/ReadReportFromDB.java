package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by johan on 15-01-26.
 */
public class ReadReportFromDB {

	public static Connection conn;

    String GET_MAX_ID_FROM_REPORT = "select * from report order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from report where timestamp =";
    String GET_ALL_REPORT_NAMES = "select name from report";
    String GET_SUITE_CLASS_CASE_ID = "select suite_id, class_id, testcase_id from report";
    String GET_ID_FROM_REPORTNAME = "select id from report where name =";
    String GET_DATE_AND_TIME_FROM_REPORT_AFTER = "select * from report where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORT_BEFORE = "select * from report where timestamp <";
    String GET_RUNTIME_FROM_REPORT = "select time from reports where name =";
    String GET_RUNTIME_FROM_ALL_REPORT = "";

    public ReadReportFromDB(Connection connection){
        conn=connection;


    }
    
    public Integer getMaxID(){
    	ResultSet rs = getResultSet(GET_MAX_ID_FROM_REPORT);
        try {
            System.out.println("Max id: "+rs.getString("id"));
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    
    //TODO: Return a boolean if the report exits, try to limit the result to 1.

    public boolean reportExists(String reportTimeStamp){
        ResultSet rs = getResultSet(REPORT_EXISTS + "'" + reportTimeStamp + "'" + "limit 1");
        System.out.println(REPORT_EXISTS+reportTimeStamp);
        try {
            System.out.println(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashSet getAllReportNames(){
        HashSet hs = new HashSet();
        ResultSet rs = getResultSet(GET_ALL_REPORT_NAMES);
        try {
            while(rs.next())
                hs.add(rs.getString(1));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }
    public HashSet getSuiteClassCaseId(){
        HashSet hs = new HashSet();
        ResultSet rs = getResultSet(GET_SUITE_CLASS_CASE_ID);
        try {
            while(rs.next())
                hs.add(rs.getString(3));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResultSet(String query){
        Statement stat;
            try {
            	stat = conn.createStatement();
				return stat.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
    }
    


    

    
}
