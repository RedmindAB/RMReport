package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.DBBridge;

public class ReadOsFromDB extends DBBridge {
	
	String GET_ALL_FROM_OS = "select * from os";
	
	public HashMap<String, Integer> getOsVersionAndId(){
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		ResultSet rs = readFromDB(GET_ALL_FROM_OS);
		
		try {
			while(rs.next()){
				hm.put(rs.getString("name")+rs.getString("version"), rs.getInt("os_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
	
}
