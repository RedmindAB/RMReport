package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

public class ReadBrowserFromDB extends DBBridge{

String GET_ALL_FROM_BROWSER = "select * from browser";
	

	public HashMap<String, Integer> getBrowserVersionAndId(){
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		ResultSet rs = readFromDB(GET_ALL_FROM_BROWSER);
		
		try {
			while(rs.next()){
				hm.put(rs.getString("browsername")+rs.getString("browserversion"), rs.getInt("browser_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
}
