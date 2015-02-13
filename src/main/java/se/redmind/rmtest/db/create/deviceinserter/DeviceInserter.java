package se.redmind.rmtest.db.create.deviceinserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.util.StringKeyValueParser;

public class DeviceInserter extends DBBridge {

	private final static String INSERT_DEVICE = "INSERT INTO device (name) VALUES (?)";
	private PreparedStatement statement;
	boolean batchNotEmpty;
	
	public DeviceInserter() {
		createStatement();
	}

	protected void createStatement() {
		try {
			statement = connection.prepareStatement(INSERT_DEVICE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertOs(String deviceName){
		try {
			statement.setString(1, deviceName);
			statement.addBatch();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		insertToDB(statement);
	}
	
	public void addOsToBatch(String deviceName){
		try {
			statement.setString(1, deviceName);
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
