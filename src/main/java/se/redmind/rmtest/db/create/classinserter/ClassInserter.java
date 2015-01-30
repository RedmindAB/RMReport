package se.redmind.rmtest.db.create.classinserter;

import java.util.HashMap;

import se.redmind.rmtest.db.create.DBInserter;
import se.redmind.rmtest.util.StringKeyValueParser;

public class ClassInserter extends DBInserter {

	private final static String INSERT_SUITE = "INSERT INTO class (name) VALUES ('{classname}')";
	
	public ClassInserter() {
	}
	
	public boolean insertTestClass(String className){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("classname", className);
		String sql = stringParser.getString(INSERT_SUITE, map);
		return insertToDB(sql);
	}
	
}
