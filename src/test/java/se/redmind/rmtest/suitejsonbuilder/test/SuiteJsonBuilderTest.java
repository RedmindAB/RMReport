package se.redmind.rmtest.suitejsonbuilder.test;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.web.route.api.suite.SuiteJsonBuilder;

public class SuiteJsonBuilderTest {

	static ResultSet rs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void before(){
		rs = mock(ResultSet.class);
	}

	
	//passed test 
	@Test
	public void getExtractResult_passed() {
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		String passed = sjb.extractResult(3, 0, 0).getAsString();
		assertEquals("passed", passed);
	}
	
	
	//skipped tests
	@Test
	public void getExtractResult_skipped() {
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		String result = sjb.extractResult(3, 3, 0).getAsString();
		assertEquals("skipped", result);
	}
	
	
	//failed tests
	@Test
	public void getExtractresult_failed() {
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		String result = sjb.extractResult(3, 0, 3).getAsString();
		assertEquals("failure", result);
	}
	
	@Test
	public void extractResult_1skipped() {
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		String result = sjb.extractResult(3, 1, 2).getAsString();
		assertEquals("failure", result);
	}
	
	@Test
	public void extractResult_some_passed() {
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		String result = sjb.extractResult(3, 1, 1).getAsString();
		assertEquals("failure", result);
	}
	
	//updateClassResults tests
	@Test
	public void updateClassResults(){
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		JsonObject testClass = sjb.getTestClass(1, "test");
		sjb.updateClassResults(3, 2, 1, testClass);
		sjb.updateClassResults(4, 2, 1, testClass);
		int skipped = testClass.get(SuiteJsonBuilder.SKIPPED).getAsInt();
		assertEquals(2, skipped);
		int fail = testClass.get(SuiteJsonBuilder.FAILURE).getAsInt();
		assertEquals(4, fail);
		int passed = testClass.get(SuiteJsonBuilder.PASSED).getAsInt();
		assertEquals(1, passed);
	}
	
	//add Time
	@Test
	public void addTime(){
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		JsonObject testClass = sjb.getTestClass(1, "test");
		sjb.addTimeToTestClass(1.2f, testClass);
		sjb.addTimeToTestClass(1.2f, testClass);
		float time = testClass.get(sjb.TIME).getAsFloat();
		assertEquals(":P", 2.4, time, 0.1f);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void getTestClass_standardBasicObject(){
		SuiteJsonBuilder sjb = new SuiteJsonBuilder(rs);
		JsonObject testClass = sjb.getTestClass(1, "testClassName");
		int id = testClass.get(sjb.ID).getAsInt();
		assertEquals(1, id);
		String name = testClass.get(sjb.NAME).getAsString();
		assertEquals("testClassName", name);
		int passed = testClass.get(sjb.PASSED).getAsInt();
		assertEquals(0, passed);
		int failure = testClass.get(sjb.FAILURE).getAsInt();
		assertEquals(0, failure);
		int skipped = testClass.get(sjb.SKIPPED).getAsInt();
		assertEquals(0, skipped);
		float time = testClass.get(sjb.TIME).getAsFloat();
		assertEquals("Time not so awsome", 0, time, 0);
	}
}
