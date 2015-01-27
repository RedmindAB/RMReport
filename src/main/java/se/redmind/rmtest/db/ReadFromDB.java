package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-26.
 */
public class ReadFromDB {

    Connection conn;

    public void readReport(){

        Statement stat = null;
        try {
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from reports;");
        while (rs.next()) {

            System.out.println("Report name = " + rs.getString("name"));
            System.out.println("Report suitename = " + rs.getString("suitename"));
            System.out.println("Report timestamp = " + rs.getString("timestamp"));
            System.out.println("Report tests = " + rs.getString("tests"));
            System.out.println("Report skipped = " + rs.getString("skipped"));
            System.out.println("Report failures = " + rs.getString("failures"));
            System.out.println("Report time = " + rs.getString("time"));
            System.out.println("Report id = " + rs.getString("id"));

            rs.close();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
