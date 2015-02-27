package se.redmind.rmtest.db.lookup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import se.redmind.rmtest.db.DBBridge;

public class OsDbLookup extends DBBridge {
	
	String GET_ALL_FROM_OS = "select * from os";
	

	public HashMap<String, Integer> getOsVersionAndId(){
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		ResultSet rs = readFromDB(GET_ALL_FROM_OS);
		
		try {
			while(rs.next()){
				hm.put(rs.getString("osname")+rs.getString("osversion"), rs.getInt("os_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
	
}
