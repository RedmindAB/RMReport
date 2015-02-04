package se.redmind.rmtest.db.create.testcaseinserter;

import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.util.StringKeyValueParser;

public class TestCaseInserter extends DBBridge {

	private final static String INSERT_SUITE = "INSERT INTO testcase (name,class_id) VALUES ('{testcasename}',{classid})";
	
	public TestCaseInserter() {
	}
	
	public boolean insertTestCase(String testCaseName, int classid){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("testcasename", testCaseName);
		map.put("classid", ""+classid);
		String sql = stringParser.getString(INSERT_SUITE, map);
		return insertToDB(sql);
	}
	
}
