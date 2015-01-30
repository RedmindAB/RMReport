package se.redmind.rmtest.db.create.testcaseinserter;

import java.util.HashMap;

import se.redmind.rmtest.db.create.DBInserter;
import se.redmind.rmtest.util.StringKeyValueParser;

public class TestCaseInserter extends DBInserter {

	private final static String INSERT_SUITE = "INSERT INTO testcase (name) VALUES ('{testcasename}')";
	
	public TestCaseInserter() {
	}
	
	public boolean insertTestCase(String testCaseName){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("testcasename", testCaseName);
		String sql = stringParser.getString(INSERT_SUITE, map);
		return insertToDB(sql);
	}
	
}
