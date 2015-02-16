package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

public class ReadDeviceFromDB extends DBBridge {
	
	String GET_ALL_FROM_DEVICE = "select * from device";
	

	public HashMap<String, Integer> getDeviceNameAndId(){
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		ResultSet rs = readFromDB(GET_ALL_FROM_DEVICE);
		
		try {
			while(rs.next()){
				hm.put(rs.getString("devicename"), rs.getInt("device_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
	

}
