package se.redmind.rmtest.db.create.browserinserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.util.StringKeyValueParser;

public class BrowserInserter extends DBBridge {

	private final static String INSERT_OS = "INSERT INTO browser (browsername, browserversion) VALUES (?,?)";
	private PreparedStatement statement;
	boolean batchNotEmpty;
	
	public BrowserInserter() {
		createStatement();
	}

	protected void createStatement() {
		try {
			statement = connection.prepareStatement(INSERT_OS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertBrowser(String browserName, String ver){
		try {
			statement.setString(1, browserName);
			statement.setString(2, ver);
			statement.addBatch();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		insertToDB(statement);
	}
	
	public void addBrowserToBatch(String browserName, String ver){
		try {
			statement.setString(1, browserName);
			statement.setString(2, ver);
			statement.addBatch();
			batchNotEmpty = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public int executeBatch(){
		if (batchNotEmpty) {
			try {
				return statement.executeBatch().length;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}
	
}
