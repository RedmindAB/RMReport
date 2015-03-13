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
	
	protected PreparedStatement prepareStatement(String sql){
		try {
			PreparedStatement prep = connection.prepareStatement(sql);
			return prep;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
	protected ResultSet readFromDB(String sql, int timestampRequestSize){
		try {
			Statement statement = getConnection(timestampRequestSize).createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected ResultSet readFromInMemoryDB(String sql, int timestampRequestSize){
		return readFromDB(sql, 1);
	}
	
	private Connection getConnection(int timestampRequestSize){
		Connection connection;
		if (timestampRequestSize > InMemoryDBHandler.timestamplimit || InMemoryDBHandler.updatingIMDB) {
//			System.out.println("Using normal connection");
			connection = DBCon.getDbInstance().getConnection();
		}
		else {
//			System.out.println("Using imdb connection");
			connection = DBCon.getDbInstance().getInMemoryConnection();
		}
		return connection;
	}
}
