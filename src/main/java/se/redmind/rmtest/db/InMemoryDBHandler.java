package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InMemoryDBHandler {
	
	Connection connection = DBCon.getDbInstance().getInMemoryConnection();
	
	public void init(){
		try {
			connection.setAutoCommit(false);
			Statement initStatement = connection.createStatement();
			initStatement.addBatch("ATTACH");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
