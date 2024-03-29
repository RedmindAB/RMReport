package test.se.redmind.rmtest.testrun;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.liveteststream.TestRun;

public class TestRunTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	public TestRun testRun;
	
	private JsonObject getSuite(){
		JsonObject suite = new JsonObject();
		suite.addProperty("suite", "suiteclass");
		suite.addProperty("totalTests", 4);
		suite.addProperty("timestamp", "20150622104141");
		JsonArray tests = new JsonArray();
		for (int i = 0; i < 4; i++) {
			tests.add(getTestCase(""+(i+1)));
		}
		suite.add("tests", tests);
		return suite;
	}
	
	private JsonObject getTestCase(String id){
		JsonObject test = new JsonObject();
		test.addProperty("id", id);
		test.addProperty("method", "test"+id);
		test.addProperty("testclass", "testclass"+id);
		JsonObject deviceInfo = new JsonObject();
		deviceInfo.addProperty("os", "OSX");
		deviceInfo.addProperty("osver", "UNKNOWN");
		deviceInfo.addProperty("device", "aDevice");
		deviceInfo.addProperty("browser", "chrome");
		deviceInfo.addProperty("browserVer", "UNKNOWN");
		test.add("deviceinfo", deviceInfo);
		return test;
	}
	
	@Before
	public void before(){
		this.testRun = new TestRun("123123", getSuite());
		System.out.println(testRun.getSuite());
	}

	@Test
	public void test_getUUID() {
		String actual = testRun.getUUID();
		assertEquals("123123", actual);
	}
	
	@Test
	public void test_startTestAndCheckHistory(){
		//Starts test
		testRun.startTest("1");
		
		//Gets the testcase and checks if it is running
		JsonObject test = testRun.getTestCase("1");
		String actual = test.get("status").getAsString();
		assertEquals("running", actual);
		
		//Gets the history and checks if its running
		JsonArray history = testRun.getHistory(0);
		JsonObject testHistory = history.get(0).getAsJsonObject();
		String type = testHistory.get("type").getAsString();
		assertEquals("running", type);
		test.addProperty("result", "passed");
		test.addProperty("runTime", 2.5);
		testRun.finishTest("1", test);
		JsonObject testCase = testRun.getTestCase("1");
		assertEquals("done", testCase.get("status").getAsString());
		assertEquals("passed", testCase.get("result").getAsString());
	}
	
}
