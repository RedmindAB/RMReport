package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-29.
 */
public class ReadSuiteFromDB {

    public static Connection conn;
    String GET_SUIT_ID = "select id from suit where name= ";

    public ReadSuiteFromDB(Connection connection){
        conn=connection;
    }
    public int getSuitID(String suiteName){
        ResultSet rs = getResulSet(GET_SUIT_ID);
        try {
            System.out.println("suit Id: "+rs.getString("id"));
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
