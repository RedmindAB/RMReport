package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.redmind.rmtest.util.StringKeyValueParser;

public abstract class DBBridge {

	protected Connection connection;
	protected StringKeyValueParser stringParser;
	
	public DBBridge() {
		connection = DBCon.getDbInstance().getConnection();
		this.stringParser = new StringKeyValueParser();
	}
	
	protected boolean insertToDB(String sql){
		int result;
		try {
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		
		return result > 0;
	}
	protected void insertToDB(Statement statement){
		try {
			statement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void insertToDB(PreparedStatement preparedStatement){
		try {
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected ResultSet readFromDB(PreparedStatement preparedStatement){
		try {
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected ResultSet readFromDB(String sql){
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			return statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
