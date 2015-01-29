package se.redmind.rmtest.db.create;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import se.redmind.rmtest.util.StringKeyValueParser;

public class DBInserter {

	protected Connection connection;
	protected StringKeyValueParser stringParser;
	
	public DBInserter() {
		connection = DBCon.getDbInstance().getConnection();
		this.stringParser = new StringKeyValueParser();
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
