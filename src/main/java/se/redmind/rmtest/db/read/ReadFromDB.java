package se.redmind.rmtest.db.read;

import se.redmind.rmtest.util.StringKeyValueParser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Created by johan on 15-01-26.
 */
public class ReadFromDB {

	public static 
	
    Connection conn;

    String GET_MAX_ID_FROM_REPORTS = "select * from reports order by id desc limit 1";
    String SUITE_EXISTS = "select name from suite where name=";
    String GET_ALL_REPORT_NAMES = "select name from reports";
    String GET_ID_FROM_REPORTNAME = "select id from reports where name =";
    String GET_DATE_AND_TIME_FROM_REPORTS_AFTER = "select * from reports where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORTS_BEFORE = "select * from reports where timestamp <";
    String GET_RUNTIME_FROM_REPORT = "select time from reports where name =";
    String GET_RUNTIME_FROM_ALL_REPORTS = "";

    public ReadFromDB(Connection connection){
        conn=connection;


    }
    
    public Integer getMaxID(){
    	ResultSet rs = getResulSet(GET_MAX_ID_FROM_REPORTS);
        try {
            System.out.println("Max id: "+rs.getString("id"));
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean reportExists(String reportName){
        ResultSet rs = getResulSet(SUITE_EXISTS+"'"+reportName+"'");
        System.out.println(SUITE_EXISTS+reportName);
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
        ResultSet rs = getResulSet(GET_ALL_REPORT_NAMES);
        try {
            while(rs.next())
                hs.add(rs.getString(1));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }

    public ResultSet getResulSet(String query){
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
