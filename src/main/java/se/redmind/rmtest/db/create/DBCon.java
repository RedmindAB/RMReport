package se.redmind.rmtest.db.create;

import java.sql.*;

/**
 * Created by johan on 15-01-26.
 */
public class DBCon {

    private static DBCon dbInstance = null;
    private static Connection conn;
    private static boolean testMode;
    
    
    private DBCon(){
        conn = null;
    }

    public Connection getConnection() {
        return conn;
    }

    public static DBCon getDbInstance()  {
        if(dbInstance == null){
            dbInstance = new DBCon();
                dbInstance.connect("RMTest.db");
                dbInstance.create(conn);
                testMode = true;
        }
        return dbInstance;
    }
    
    public static DBCon getDbTestInstance()  {
        if(dbInstance == null){
            dbInstance = new DBCon();
                dbInstance.connect("testRMTest.db");
                dbInstance.create(conn);
                testMode = true;
        }
        return dbInstance;
    }

    private Connection connect(String dbname) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+dbname);
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


            stat.executeUpdate("create table if not exists suite (name, suite_id integer primary key autoincrement)");

            stat.executeUpdate("create table if not exists class (name, class_id integer primary key autoincrement)");

            stat.executeUpdate("create table if not exists testcase (name, testcase_id integer primary key autoincrement, class_id integer, foreign key (class_id) references class (class_id))");

            stat.executeUpdate("create table if not exists report (suite_id integer, class_id integer, testcase_id integer, timestamp, result, message, name, driver, time float, " +
                    "foreign key (suite_id) references suite (suite_id), foreign key (class_id) references class (class_id), foreign key (testcase_id) references testcase (testcase_id))");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void dropDatabase(){
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.execute("DELETE FROM report");
			stat.execute("DELETE FROM class");
			stat.execute("DELETE FROM suite");
			stat.execute("DELETE FROM testcase");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
