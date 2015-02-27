package se.redmind.rmtest.web.route.api.method.getmethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetMethodsDAO extends DBBridge{

	public String getMethods(int classid) {
		JsonArray array = new JsonArray();
		List<HashMap<String, String>> testCasesFromClassID = getTestCasesFromClassID(classid);
		for (HashMap<String, String> hashMap : testCasesFromClassID) {
			JsonObject method = new JsonObject();
			method.add("name", new JsonPrimitive(hashMap.get("name")));
			method.add("id", new JsonPrimitive(hashMap.get("id")));
			array.add(method);
		}
		return new Gson().toJson(array);
	}
	public List<HashMap<String, String>> getTestCasesFromClassID(int id){
		String GET_TESTCASE_FROM_CLASS_ID = "SELECT testcasename, testcase_id FROM testcase WHERE class_id = ";
    	ResultSet rs = readFromDB(GET_TESTCASE_FROM_CLASS_ID+id);
    	List<HashMap<String, String>> result = new ArrayList<>();
    	try {
			while (rs.next()) {
				HashMap<String,String> row = new HashMap<>();
				row.put("name", rs.getString("testcasename"));
				row.put("id", rs.getString("testcase_id"));
				result.add(row);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return result;
    }

}
