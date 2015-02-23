package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDBHandler {
	
	Connection connection = DBCon.getDbInstance().getInMemoryConnection();
	public static final int timestamplimit = 150;
	public static boolean updatingIMDB = false;
	
	public void init(){
		try {
			Statement initStatement = connection.createStatement();
			initStatement.execute(getAttachDatabaseSQL());
			List<Integer> suiteids = getSuites();
			if (suiteids == null) {
				return;
			}
			for (Integer suiteid : suiteids) {
				System.out.println("Loading suite: #"+suiteid);
				initStatement.execute("INSERT INTO report SELECT * FROM rmtest.report WHERE suite_id = "+suiteid+" AND rmtest.report.timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM rmtest.report WHERE suite_id = "+suiteid+" ORDER BY timestamp DESC LIMIT "+timestamplimit+"));");
			}
			updateSmallTables(initStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getSuites(){
		try {
			Statement statement = connection.createStatement();
			List<Integer> suiteIdList = new ArrayList<Integer>();
			ResultSet executeQuery = statement.executeQuery("SELECT DISTINCT suite_id FROM rmtest.report;");
			while (executeQuery.next()) {
				suiteIdList.add(executeQuery.getInt(1));
			}
			return suiteIdList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAttachDatabaseSQL(){
		String databasePath = System.getProperty("user.dir")+"/RMTest.db";
		return "ATTACH DATABASE '"+databasePath+"' AS rmtest;";
	}
	
	/**
	 * This method updates the in memory database so that the data is the same as in the rmTest.db file.
	 * this method should be called after the rmTest.db has been updated.
	 */
	public void updateInMemoryDB(){
			updatingIMDB = true;
			System.out.println("Dropping in memory database");
			DBCon.getDbInstance().dropIMDB();
			connection = DBCon.getDbInstance().getInMemoryConnection();
			System.out.println("Updating in memory database");
			init();
			updatingIMDB = false;
	}

	private void updateSmallTables(Statement updateStatement)
			throws SQLException {
		updateStatement.execute("INSERT OR IGNORE INTO class SELECT * FROM rmtest.class;");
		updateStatement.execute("INSERT OR IGNORE INTO suite SELECT * FROM rmtest.suite;");
		updateStatement.execute("INSERT OR IGNORE INTO testcase SELECT * FROM rmtest.testcase;");
		updateStatement.execute("INSERT OR IGNORE INTO os SELECT * FROM rmtest.os;");
		updateStatement.execute("INSERT OR IGNORE INTO browser SELECT * FROM rmtest.browser;");
		updateStatement.execute("INSERT OR IGNORE INTO device SELECT * FROM rmtest.device;");
	}
	
	public int getSuiteIdFromLatestTimestamp(){
		Connection connection = DBCon.getDbInstance().getConnection();
		System.out.println("Getting suite from latest timestamp");
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT DISTINCT suite_id FROM report WHERE timestamp = (SELECT MAX(timestamp) FROM report);");
			while (rs.next()) {
				//Should only return 1 row, so return after fetching the id.
				int suiteid = rs.getInt("suite_id");
				return suiteid;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean removeOldesRunFromSuiteID(int suiteid){
		String sql = "DELETE FROM report WHERE timestamp = (SELECT MIN(timestamp) FROM report WHERE suite_id = "+suiteid+");";
		try {
			return connection.createStatement().execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertLatestReport(int suiteid, Statement statement){
		try {
			statement.execute("INSERT INTO report SELECT * FROM rmtest.report WHERE rmtest.report.timestamp = (SELECT MAX(timestamp) FROM rmtest.report WHERE rmtest.report.suite_id = "+suiteid+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeOldTimestamps(int suiteid, Statement statement){
		String sql = "DELETE FROM report WHERE timestamp < (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = "+suiteid+" ORDER BY timestamp DESC LIMIT "+timestamplimit+")) AND suite_id = "+suiteid+";";
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
