package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-26.
 */
public class DBCon {
	
	private Long lastUpdated;
	public static enum instance {DISK, INMEMORY};
    private static DBCon dbInstance = null;
    private static Connection conn;
    private static Connection imConnection;
    private static volatile boolean testMode;
    private static String currentDBName;
    
    
    public Connection getConnection() {
        return conn;
    }

    public static DBCon getDbInstance()  {
        try {
			if(dbInstance == null|| dbInstance.getConnection().isClosed()){
			    dbInstance = new DBCon();
			    currentDBName = "RMTest.db";
		        conn = dbInstance.connect(currentDBName);
		        dbInstance.create(conn);
		        testMode = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return dbInstance;
    }
    
    public static DBCon getDbTestInstance()  {
        try {
			if(dbInstance == null || testMode == false || dbInstance.getConnection().isClosed()){
			    dbInstance = new DBCon();
			    currentDBName = "testRMTest.db";
		        conn = dbInstance.connect(currentDBName);
		        dbInstance.create(conn);
		        testMode = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return dbInstance;
    }
    
    public Connection getInMemoryConnection(){
    	try {
			if (imConnection == null || imConnection.isClosed()) {
				imConnection = connect(":memory:");
				create(imConnection);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return imConnection;
    }

    private Connection connect(String dbname) {
        try {
            return DriverManager.getConnection("jdbc:sqlite:"+dbname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void create(Connection conn)  {
        Statement stat = null;
        try {
            stat = conn.createStatement();

            stat.executeUpdate("CREATE TABLE IF NOT EXISTS suite (suitename, suite_id INTEGER PRIMARY KEY AUTOINCREMENT)");

            stat.executeUpdate("CREATE TABLE IF NOT EXISTS class (classname, class_id INTEGER PRIMARY KEY AUTOINCREMENT)");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS device (devicename, device_id INTEGER PRIMARY KEY AUTOINCREMENT)");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS browser (browsername, browserversion, browser_id INTEGER PRIMARY KEY AUTOINCREMENT)");
            
            stat.executeUpdate("CREATE TABLE IF NOT EXISTS os (osname, osversion, os_id INTEGER PRIMARY KEY AUTOINCREMENT)");

            stat.executeUpdate("CREATE TABLE IF NOT EXISTS testcase (testcasename, testcase_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "class_id INTEGER, is_gherkin INTEGER DEFAULT 0, FOREIGN KEY (class_id) REFERENCES class (class_id))");

            stat.executeUpdate("CREATE TABLE IF NOT EXISTS report (suite_id INTEGER, class_id INTEGER, testcase_id INTEGER, " +
                    "device_id INTEGER, browser_id INTEGER, os_id INTEGER, timestamp INTEGER, result, message, time FLOAT, " +
                    "FOREIGN KEY (os_id) REFERENCES os (os_id), FOREIGN KEY (browser_id) REFERENCES browser (browser_id), " +
                    "FOREIGN KEY (device_id) REFERENCES device (device_id),FOREIGN KEY (suite_id) REFERENCES suite (suite_id), " +
                    "FOREIGN KEY (class_id) REFERENCES class (class_id), FOREIGN KEY (testcase_id) REFERENCES testcase (testcase_id))");

			stat.executeUpdate("CREATE TABLE IF NOT EXISTS steps (stepname, testcase_id INTEGER, timestamp INTEGER, " +
					"step_id INTEGER, result, PRIMARY KEY(testcase_id, timestamp, step_id), FOREIGN KEY (testcase_id) " +
					"REFERENCES testcase(testcase_id), FOREIGN KEY (timestamp) REFERENCES report(timestamp)) ");

			stat.executeUpdate("CREATE INDEX IF NOT EXISTS reportindex ON report (timestamp DESC)");

			stat.executeUpdate("CREATE INDEX IF NOT EXISTS osindex ON os (osname)");

			stat.executeUpdate("CREATE TABLE IF NOT EXISTS parameters (timestamp INTEGER, suite_id INTEGER, parameter, value, " +
                    "PRIMARY KEY (timestamp, suite_id, parameter))");

			stat.executeUpdate("CREATE INDEX IF NOT EXISTS parametersindex ON parameters (timestamp DESC)");


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
			stat.execute("DELETE FROM parameters");
			stat.execute("DELETE FROM steps");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public synchronized void refreshLastUpdated(){
    	this.lastUpdated = System.currentTimeMillis();
    	this.lastUpdated.notifyAll();
    }
    
    public synchronized Long getLastUpdated(){
    	return lastUpdated;
    }
    
    public void dropIMDB(){
    	try {
    		if(imConnection != null){
    			imConnection.close();
    			imConnection = null;
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static boolean isTestmode(){
    	return testMode;
    }
    
    public String getCurrentDBName(){
    	return currentDBName;
    }

	public static void closeAllConnections() {
		testMode = false;
		DBCon dbInstance = DBCon.getDbInstance();
		dbInstance.dropDatabase(dbInstance.getConnection());
		dbInstance.dropIMDB();
	}
}
