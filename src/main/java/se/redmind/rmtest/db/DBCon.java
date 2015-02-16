package se.redmind.rmtest.db;

import java.sql.*;

/**
 * Created by johan on 15-01-26.
 */
public class DBCon {

	public static enum instance {DISK, INMEMORY};
    private static DBCon dbInstance = null;
    private static Connection conn;
    private static Connection imConnection;
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
    
    public Connection getInMemoryConnection(){
    	if (imConnection == null) {
			imConnection = connect(":memory:");
			create(imConnection);
		}
    	return imConnection;
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

            stat.executeUpdate("create table if not exists suite (suitename, suite_id integer primary key autoincrement)");

            stat.executeUpdate("create table if not exists class (classname, class_id integer primary key autoincrement)");
            
            stat.executeUpdate("create table if not exists device (devicename, device_id integer primary key autoincrement)");
            
            stat.executeUpdate("create table if not exists browser (browsername, version, browser_id integer primary key autoincrement)");
            
            stat.executeUpdate("create table if not exists os (osname, version, os_id integer primary key autoincrement)");

            stat.executeUpdate("create table if not exists testcase (testcasename, testcase_id integer primary key autoincrement, class_id integer, foreign key (class_id) references class (class_id))");

            stat.executeUpdate("create table if not exists report (suite_id integer, class_id integer, testcase_id integer, device_id integer, browser_id integer, os_id integer, timestamp long, result, message, time float, " +
                    "foreign key (os_id) references os (os_id), foreign key (browser_id) references browser (browser_id), foreign key (device_id) references device (device_id),foreign key (suite_id) references suite (suite_id), foreign key (class_id) references class (class_id), foreign key (testcase_id) references testcase (testcase_id))");
            
            stat.executeUpdate("create index if not exists reportindex on report (timestamp)");
            
            stat.executeUpdate("create index if not exists osindex on os (osname)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void dropDatabase(Connection conn){
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.execute("DELETE FROM report");
			stat.execute("DELETE FROM class");
			stat.execute("DELETE FROM suite");
			stat.execute("DELETE FROM testcase");
			stat.execute("DELETE FROM os");
			stat.execute("DELETE FROM device");
			stat.execute("DELETE FROM browser");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
