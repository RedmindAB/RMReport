package se.redmind.rmtest.db;

import java.sql.*;

/**
 * Created by johan on 15-01-26.
 */
public class DBCon {

        public void connect() throws ClassNotFoundException, SQLException {

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("drop table if exists reports");
            stat.executeUpdate("drop table if exists testcases");
            stat.executeUpdate("create table reports (name, suitename, timestamp, tests, skipped, failures, time, id integer primary key autoincrement)");
            stat.executeUpdate("create table testcases (driver, name, error, time, passed, reportid)");
            PreparedStatement prep = conn.prepareStatement("insert into testcases values (?,?,?,?,?,?);");

            prep.setString(1, "Gustav");
            prep.setString(2, "optimeringfascist");
            prep.setString(3, "Gustav");
            prep.setString(4, "Gustav");
            prep.setString(5, "Gustav");
            prep.setString(6, "Gustav");
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            ResultSet rs = stat.executeQuery("select * from testcases;");
            while (rs.next()) {
                System.out.println("driver = " + rs.getString("driver"));
                System.out.println("name = " + rs.getString("name"));
                System.out.println("error = " + rs.getString("error"));
                System.out.println("time = " + rs.getString("time"));
                System.out.println("passed = " + rs.getString("passed"));
                System.out.println("values = " + rs.getString("values"));
            }
            rs.close();
            conn.close();


        }





}
