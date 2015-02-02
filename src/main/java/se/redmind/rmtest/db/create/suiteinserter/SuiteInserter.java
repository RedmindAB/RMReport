package se.redmind.rmtest.db.create.suiteinserter;

import java.util.HashMap;

import se.redmind.rmtest.db.create.DBBridge;
import se.redmind.rmtest.util.StringKeyValueParser;

public class SuiteInserter extends DBBridge {

	private final static String INSERT_SUITE = "INSERT INTO suite (name) VALUES ('{suitename}')";
	
	public SuiteInserter() {
	}
	
	public boolean insertSuite(String suiteName){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("suitename", suiteName);
		String sql = stringParser.getString(INSERT_SUITE, map);
		return insertToDB(sql);
	}
	
}
