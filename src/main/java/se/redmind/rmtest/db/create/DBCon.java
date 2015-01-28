package se.redmind.rmtest.db.create;

import java.sql.*;

/**
 * Created by johan on 15-01-26.
 */
public class DBCon {

    private static DBCon dbInstance = null;
    private static Connection conn;

    private DBCon(){
        conn = null;
    }

    public Connection getConnection() {
        return conn;
    }

    public static DBCon getDbInstance()  {
        if(dbInstance == null){
            dbInstance = new DBCon();
                dbInstance.connect();
                dbInstance.create(conn);
        }
        return dbInstance;
    }

    private Connection connect() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:RMtest.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }


    private void create(Connection conn)  {
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.executeUpdate("create table if not exists reports (name, suitename, timestamp, tests, skipped, failures, time, id integer primary key autoincrement)");
            stat.executeUpdate("create table if not exists testcases (name, driver, error, time, failures, passed, reportid, id integer primary key autoincrement)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
