package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestcaseFromDB extends DBBridge {

    public static Connection conn;
    String GET_TESTCASE_ID = "select testcase_id from testcase where name= ";

    public ReadTestcaseFromDB(Connection connection){
        conn=connection;
    }


    public int getTestCaseID(String testCaseName){
        ResultSet rs = readFromDB(GET_TESTCASE_ID+"'"+testCaseName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
    }


}
