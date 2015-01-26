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
            stat.executeUpdate("drop table if exists people;");
            stat.executeUpdate("create table people (name, title);");
            PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");

            prep.setString(1, "Gustav");
            prep.setString(2, "optimeringfascist");
            prep.addBatch();
            prep.setString(1, "Lukas");
            prep.setString(2, "in your face");
            prep.addBatch();
            prep.setString(1, "Mattias");
            prep.setString(2, "mr javascript");
            prep.addBatch();

            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);

            ResultSet rs = stat.executeQuery("select * from people;");
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("title = " + rs.getString("title"));
            }
            rs.close();
            conn.close();


        }





}
