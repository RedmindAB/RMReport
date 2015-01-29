package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestrunsFromDB {

    public static Connection conn;

    String GET_ALL_TESTRUNS = "select * from testruns";
    String GET_ALL_TESTRUNS_BY_NAME = "select * from testruns";

    public ReadTestrunsFromDB(Connection connection){
        conn=connection;
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
    public HashSet getAllTestruns(){
        HashSet hs = new HashSet();
        ResultSet rs = getResulSet(GET_ALL_TESTRUNS);
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
