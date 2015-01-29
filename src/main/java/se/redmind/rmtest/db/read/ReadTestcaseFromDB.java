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

    public ReadTestcaseFromDB(Connection connection){
        conn=connection;
    }
    public int getTestCaseID(String testCaseName){
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
