package se.redmind.rmtest.db.create.parameterinserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import se.redmind.rmtest.db.DBBridge;

public class ParameterInserter extends DBBridge {

	public void insertParameters(int suiteid, Long timestamp, HashMap<String,String> parameters){
		try {
			Set<Entry<String, String>> parameterSet = parameters.entrySet();
			PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO parameters (timestamp, suite_id, parameter, value) VALUES (?, ?, ?, ?)");
			for (Entry<String, String> entry : parameterSet) {
				prepareStatement.setLong(1, timestamp);
				prepareStatement.setInt(2, suiteid);
				prepareStatement.setString(3, entry.getKey());
				prepareStatement.setString(4, entry.getValue());
				prepareStatement.addBatch();
			}
			prepareStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
