package se.redmind.rmtest.db.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInserter {

	Connection connection;
	
	public DBInserter() {
		connection = DBCon.getDbInstance().getConnection();
	}
	
	public boolean insertToDB(String sql){
		boolean result;
		try {
			Statement statement = connection.createStatement();
			result = statement.execute(sql);
		} catch (SQLException e) {
			return false;
		}
		
		return result;
	}
	public void insertToDB(Statement statement){
		try {
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertToDB(PreparedStatement preparedStatement){
		try {
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
