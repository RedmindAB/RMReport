package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.stats.methodfail.MethodFailJsonBuilder;

public class MethodFailJsonBuilderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void overallTest() {
		HashSet<String> hashSet = getHashSet(2, 2);
		MethodFailJsonBuilder builder = new MethodFailJsonBuilder(hashSet);
		builder.addTestCase("method0", "class0", "failure", 123L);
		builder.addTestCase("method1", "class1", "failure", 123L);
		builder.addTestCase("method1", "class3", "passed", 123L);
		JsonArray build = builder.build(200);
		assertEquals(2, build.size());
	}
	
	
	@Test
	public void lastFail(){
		MethodFailJsonBuilder builder = getMethodFailJsonBuilder(1,1);
		builder.addTestCase("method0", "class0", "error", 1L);
		builder.addTestCase("method0", "class0", "passed", 2L);
		builder.addTestCase("method0", "class0", "failure", 3L);
		builder.addTestCase("method0", "class0", "passed", 4L);
		builder.addTestCase("method1", "class0", "passed", 4L);
		JsonArray build = builder.build(1);
		JsonObject res = build.get(0).getAsJsonObject();
		assertEquals(res.get("lastfail").getAsLong(), 3L);
	}
	
	@Test
	public void orderedList(){
		MethodFailJsonBuilder builder = getMethodFailJsonBuilder(3,2);
		builder.addTestCase("method0", "class0", "passed", 1L);
		builder.addTestCase("method0", "class0", "error", 2L);
		builder.addTestCase("method0", "class0", "passed", 3L);
		builder.addTestCase("method0", "class0", "error", 4L);
		builder.addTestCase("method0", "class0", "passed", 5L);
		builder.addTestCase("method0", "class0", "error", 6L);
		builder.addTestCase("method1", "class0", "error", 1L);
		builder.addTestCase("method1", "class0", "passed", 2L);
		builder.addTestCase("method1", "class0", "error", 3L);
		builder.addTestCase("method1", "class0", "passed", 4L);
		builder.addTestCase("method1", "class0", "passed", 5L);
		builder.addTestCase("method1", "class0", "passed", 6L);
		builder.addTestCase("method2", "class0", "passed", 1L);
		builder.addTestCase("method2", "class0", "passed", 2L);
		builder.addTestCase("method2", "class0", "passed", 3L);
		builder.addTestCase("method2", "class0", "passed", 4L);
		builder.addTestCase("method2", "class0", "passed", 5L);
		builder.addTestCase("method2", "class0", "passed", 6L);
		JsonArray build = builder.build(123);
		JsonObject mostFailed = build.get(0).getAsJsonObject();
		JsonObject middleFailed = build.get(1).getAsJsonObject();
		JsonObject noFail = build.get(2).getAsJsonObject();
		assertTrue("most failed was not higher than middle failed", mostFailed.get("ratioFail").getAsDouble()>middleFailed.get("ratioFail").getAsDouble());
		assertTrue("middle failed were not higher than no fails", middleFailed.get("ratioFail").getAsDouble()>noFail.get("ratioFail").getAsDouble());
	}

	private MethodFailJsonBuilder getMethodFailJsonBuilder(int methods, int classes) {
		HashSet<String> hashSet = getHashSet(methods, classes);
		MethodFailJsonBuilder methodFailJsonBuilder = new MethodFailJsonBuilder(hashSet);
		return methodFailJsonBuilder;
	}
	
	private HashSet<String> getHashSet(int methods, int classes){
		HashSet<String> set = new HashSet<String>(); 
		for (int clazz = 0; clazz < classes; clazz++) {
			for (int method = 0; method < methods; method++) {
				set.add("class"+clazz+"method"+method);
			}
		}
		return set;
	}

}
