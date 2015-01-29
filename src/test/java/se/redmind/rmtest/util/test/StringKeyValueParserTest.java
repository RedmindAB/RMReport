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

}
