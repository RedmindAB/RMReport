package se.redmind.rmtest.db.create.osinserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.util.StringKeyValueParser;

public class OSInserter extends DBBridge {

	private final static String INSERT_OS = "INSERT INTO os (osname, osversion) VALUES (?,?)";
	private PreparedStatement statement;
	boolean batchNotEmpty;
	
	public OSInserter() {
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
	
	public void insertOs(String osname, String ver){
		try {
			statement.setString(1, osname);
			statement.setString(2, ver);
			statement.addBatch();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		insertToDB(statement);
	}
	
	public void addOsToBatch(String osname, String ver){
		try {
			statement.setString(1, osname);
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
