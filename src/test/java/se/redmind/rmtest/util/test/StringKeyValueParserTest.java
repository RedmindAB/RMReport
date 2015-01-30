package se.redmind.rmtest.util.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.util.StringKeyValueParser;

public class StringKeyValueParserTest {

	static StringKeyValueParser parser;
	
	@BeforeClass
	public static void beforeClass(){
		parser = new StringKeyValueParser();
	}
	
	@Test
	public void test() {
		String template = "Hello my name is {name}";
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("name", "foo");
		String result = parser.getString(template, map);
		assertEquals("Hello my name is foo", result);
	}
	
	@Test
	public void sqlStringReplacement(){
		String template = "INSERT INTO report (suite_id, class_id, testcase_id, timestamp, result, message, name, driver, time) "
				+ "VALUES ({suite_id},{class_id},{testcase_id},'{timestamp}','{result}','{message}','{name}','{driver}',{time})";
		StringKeyValueParser parser = new StringKeyValueParser(template);
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("suite_id", ""+1);
		Integer classID = 1;
		map.put("class_id", ""+classID);
		map.put("testcase_id", ""+1);
		map.put("timestamp", "20150130-1126");
		map.put("result", "error");
		map.put("message", "\"<>:![].");
		map.put("name", "failmethod");
		map.put("driver", "OSX chrome");
		map.put("time", ""+0.123);
		String res = parser.getString(map);
		String expected = "INSERT INTO report (suite_id, class_id, testcase_id, timestamp, result, message, name, driver, time) "
				+ "VALUES (1,1,1,'20150130-1126','error','\"<>:![].','failmethod','OSX chrome',0.123)";
		assertEquals(expected, res);
	}

}
