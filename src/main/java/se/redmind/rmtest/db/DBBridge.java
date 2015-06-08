package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;

import se.redmind.rmtest.util.StringKeyValueParser;

public abstract class DBBridge {

	protected Connection connection;
	protected StringKeyValueParser stringParser;
	private static Date lastUpdated;
	
	public DBBridge() {
		connection = DBCon.getDbInstance().getConnection();
		this.stringParser = new StringKeyValueParser();
		if (lastUpdated == null) {
			lastUpdated = new Date();
		}
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
	
	
	/**
	 * inserts the sql into the database, SQLite only supports one thread to write to the database at the time so the method is synchronized.
	 * @param sql
	 * @return
	 */
	protected synchronized boolean insertToDB(String sql){
		int result;
		try {
			Statement statement = connection.createStatement();
			result = statement.executeUpdate(sql);
			updateLastUpdated();
		} catch (SQLException e) {
			return false;
		}
		
		return result > 0;
	}
	
	/**
	 * inserts the statement into the database, SQLite only supports one thread to write to the database at the time so the method is synchronized.
	 * @param statement
	 */
	protected synchronized void insertToDB(Statement statement){
		try {
			statement.executeBatch();
			updateLastUpdated();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * inserts the prepared statement into the database, SQLite only supports one thread to write to the database at the time so the method is synchronized.
	 * @param preparedStatement
	 */
	protected synchronized void insertToDB(PreparedStatement preparedStatement){
		try {
			preparedStatement.executeBatch();
			updateLastUpdated();
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
	
	public static void updateLastUpdated(){
		lastUpdated = new Date();
	}
	
	public static Date getLastUpdated(){
		return lastUpdated;
	}
	
}
