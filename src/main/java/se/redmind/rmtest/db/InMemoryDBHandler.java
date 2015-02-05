package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class InMemoryDBHandler {
	
	Connection connection = DBCon.getDbInstance().getInMemoryConnection();
	
	public void init(){
		try {
			String databasePath = System.getProperty("user.dir")+"/RMTest.db";
			System.out.println(databasePath);
			Statement initStatement = connection.createStatement();
			initStatement.execute("ATTACH DATABASE '"+databasePath+"' AS rmtest;");
			initStatement.execute("INSERT INTO report SELECT * FROM rmtest.report WHERE rmtest.report.timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM rmtest.report ORDER BY timestamp DESC LIMIT 150));");
			initStatement.execute("INSERT INTO class SELECT * FROM rmtest.class;");
			initStatement.execute("INSERT INTO suite SELECT * FROM rmtest.suite;");
			initStatement.execute("INSERT INTO testcase SELECT * FROM rmtest.testcase;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
