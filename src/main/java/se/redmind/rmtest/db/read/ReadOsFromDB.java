package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import se.redmind.rmtest.db.DBBridge;

public class ReadOsFromDB extends DBBridge {
	
	String GET_ALL_FROM_OS = "select * from os";
	
	public HashMap<String, String> getOsVersionAndId(){
		HashMap<String, String> hm = new HashMap<String, String>();
		ResultSet rs = readFromDB(GET_ALL_FROM_OS);
		
		try {
			while(rs.next()){
				hm.put("os_id", rs.getString("os_id"));
				hm.put("OSversion", rs.getString("name")+rs.getString("version"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
	
}
