package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestcaseFromDB {

    public static Connection conn;
    String GET_TESTCASE_ID = "select id from testcase where name= ";

    public ReadTestcaseFromDB(Connection connection){
        conn=connection;
    }
    public int getTestCaseID(String testCaseName){
        ResultSet rs = getResulSet(GET_TESTCASE_ID);
        try {
            System.out.println("testcase Id: "+rs.getString("id"));
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
