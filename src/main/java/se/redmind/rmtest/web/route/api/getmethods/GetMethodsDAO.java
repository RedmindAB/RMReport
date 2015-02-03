package se.redmind.rmtest.web.route.api.getmethods;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadTestcaseFromDB;
import se.redmind.rmtest.db.read.ReadTestrunsFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetMethodsDAO {

	public String getMethods(int classid) {
		JsonArray array = new JsonArray();
		List<HashMap<String, String>> testCasesFromClassID = new ReadTestcaseFromDB().getTestCasesFromClassID(classid);
		System.out.println(testCasesFromClassID);
		return testCasesFromClassID.toString();
	}

}
