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


            //stat.executeUpdate("create table if not exists suite (name, suite_id integer primary key autoincrement)");

            //stat.executeUpdate("create table if not exists class (name, class_id integer primary key autoincrement)");

            //stat.executeUpdate("create table if not exists testcase (name, testcase_id integer primary key autoincrement, class_id integer, foreign key (class_id) references class (class_id))");

            //stat.executeUpdate("create table if not exists report (suite_id integer, class_id integer, testcase_id integer, timestamp, result, message, name, driver, time float, " +
            //        "foreign key (suite_id) references suite (suite_id), foreign key (class_id) references class (class_id), foreign key (testcase_id) references testcase (testcase_id))");

            stat.executeUpdate("insert into suite (name) values ('testsuite01')");
            stat.executeUpdate("insert into class (name) values ('testClass01')");
            stat.executeUpdate("insert into testcase (name) values ('testcase01')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
