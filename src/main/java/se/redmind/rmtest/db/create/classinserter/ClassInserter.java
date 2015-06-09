package se.redmind.rmtest.db.create.classinserter;

import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

public class ClassInserter extends DBBridge {

	private final static String INSERT_SUITE = "INSERT INTO class (classname) VALUES ('{classname}')";
	
	public ClassInserter() {
	}
	
	public boolean insertTestClass(String className){
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("classname", className);
		String sql = stringParser.getString(INSERT_SUITE, map);
		return insertToDB(sql);
	}
	
}
