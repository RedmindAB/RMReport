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
	
	public void init(){
		try {
			String databasePath = System.getProperty("user.dir")+"/RMTest.db";
			Statement initStatement = connection.createStatement();
			initStatement.execute("ATTACH DATABASE '"+databasePath+"' AS rmtest;");
			List<Integer> suiteids = getSuites();
			if (suiteids == null) {
				return;
			}
			for (Integer suiteid : suiteids) {
				System.out.println("Loading suite(id): "+suiteid);
				initStatement.execute("INSERT INTO report SELECT * FROM rmtest.report WHERE suite_id = "+suiteid+" AND rmtest.report.timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM rmtest.report WHERE suite_id = "+suiteid+" ORDER BY timestamp DESC LIMIT "+timestamplimit+"));");
			}
			initStatement.execute("INSERT INTO class SELECT * FROM rmtest.class;");
			initStatement.execute("INSERT INTO suite SELECT * FROM rmtest.suite;");
			initStatement.execute("INSERT INTO testcase SELECT * FROM rmtest.testcase;");
			initStatement.execute("INSERT INTO os SELECT * FROM rmtest.os;");
			initStatement.execute("INSERT INTO browser SELECT * FROM rmtest.browser;");
			initStatement.execute("INSERT INTO device SELECT * FROM rmtest.device;");
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
	
}
