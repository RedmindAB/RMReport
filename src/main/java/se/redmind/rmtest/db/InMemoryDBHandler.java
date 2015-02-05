package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InMemoryDBHandler {
	
	Connection connection = DBCon.getDbInstance().getInMemoryConnection();
	
	public void init(){
		try {
			connection.setAutoCommit(false);
			String databasePath = System.getProperty("user.dir")+"/RMTest.db";
			System.out.println(databasePath);
			Statement initStatement = connection.createStatement();
			initStatement.addBatch("ATTACH DATABASE "+databasePath+" AS rmtest;");
			initStatement.addBatch("INSERT INTO report FROM SELECT * FROM rmtest.report WHERE timestamp >= (SELECT MIN(timestamp) FROM (SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50));");
			initStatement.executeBatch();
			connection.setAutoCommit(true);
//			Statement createStatement = connection.createStatement();
//			ResultSet executeQuery = createStatement.executeQuery("SELECT * FROM report;");
//			while (executeQuery.next()) {
//				System.out.println(executeQuery.getString(1));
//			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
