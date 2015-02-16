package se.redmind.rmtest.db.read;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SuiteJsonBuilder {

	
	private static final String PASSED = "passed";
	private static final String RESULT = "result";
	private static final String TIME = "time";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String TESTCASES = "testcases";
	private JsonArray suiteArray;
	private HashMap<Integer, JsonObject> classMap;
	private ResultSet rs;
	
	public SuiteJsonBuilder(ResultSet rs) {
		suiteArray = new JsonArray();
		classMap = new HashMap<Integer, JsonObject>();
		this.rs = rs;
	}
	
	public SuiteJsonBuilder build(){
		try {
			while (rs.next()) {
				int classid = rs.getInt(1);
				String className = rs.getString(2);
				int testcaseId = rs.getInt(3);
				String testcaseName = rs.getString(4);
				String result = rs.getString(5);
				float time = rs.getFloat(6);
				JsonObject testClass = getTestClass(classid, className);
				addTimeToTestClass(time, testClass);
				addTestCase(testcaseId, testcaseName, result, testClass, time);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Integer id : classMap.keySet()) {
			suiteArray.add(classMap.get(id));
		}
		return this;
	}
	
	public void addTestCase(int testcase_id, String testcaseName, String result, JsonObject testClass, double time){
		JsonArray testCases = testClass.get(TESTCASES).getAsJsonArray();
		JsonObject testCase = new JsonObject();
		testCase.add(ID, new JsonPrimitive(testcase_id));
		testCase.add(NAME, new JsonPrimitive(testcaseName));
		testCase.add(RESULT, new JsonPrimitive(result));
		testCase.add(TIME, new JsonPrimitive(time));
		testCases.add(testCase);
		if (!result.equals(PASSED)) {
			testClass.addProperty(RESULT, "failure");
		}
	}

	public JsonObject getTestClass(int class_id, String className) {
		JsonObject testclass = classMap.get(class_id);
		if (testclass == null) {
			testclass = new JsonObject();
			testclass.add(ID, new JsonPrimitive(class_id));
			testclass.add(NAME, new JsonPrimitive(className));
			testclass.add(TIME, new JsonPrimitive(0f));
			testclass.add(TESTCASES, new JsonArray());
			testclass.addProperty(RESULT, PASSED);
			classMap.put(class_id, testclass);
		}
		return testclass;
	}
	
	public void addTimeToTestClass(float time, JsonObject testclass){
		double appendedTime = testclass.get(TIME).getAsDouble()+ time;
		testclass.add(TIME, new JsonPrimitive(appendedTime));
	}
	public JsonArray getSuite(){
		return suiteArray;
	}
	
}
