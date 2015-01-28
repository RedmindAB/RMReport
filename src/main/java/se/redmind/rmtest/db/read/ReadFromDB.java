package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-26.
 */
public class ReadFromDB {

	public static 
	
    Connection conn;

    String GET_MAX_ID_FROM_REPORTS = "select * from reports order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from reports where timestamp=";



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
    public boolean reportExists(String reportTimeStamp){
        ResultSet rs = getResulSet(REPORT_EXISTS+"'"+reportTimeStamp+"'");
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

    public ResultSet getResulSet(String query){
        Statement stat = null;
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
