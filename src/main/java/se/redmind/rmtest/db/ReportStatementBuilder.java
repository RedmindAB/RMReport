package se.redmind.rmtest.db;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

/**
 * Created by johan on 15-01-26.
 */
public class ReportStatementBuilder {

    Connection conn;

    public ReportStatementBuilder(Connection connection) {
       conn = connection;
    }

    public PreparedStatement reportStatement(Report report){

        try {
            Statement stat = conn.createStatement();
            PreparedStatement prep = conn.prepareStatement("insert into reports (name, suitename, timestamp, tests, skipped, failures, time) values (?,?,?,?,?,?,?);");

            prep.setString(1, report.getName());
            prep.setString(2, report.getSuiteName());
            prep.setString(3, report.getTimestamp());
            prep.setInt   (4, report.getTests());
            prep.setInt   (5, report.getSkipped());
            prep.setInt   (6, report.getFailures());
            prep.setDouble(7, report.getTime());
            prep.addBatch();

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
            }
            rs.close();
            return prep;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}

