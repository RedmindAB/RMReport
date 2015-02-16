package se.redmind.rmtest.web.route.api.suite.byid;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.read.ReadSuiteFromDB;

public class GetLatestSuiteDAO {

	public String getLatestSuite(int suite_id, String timestamp){
		JsonArray array = new ReadSuiteFromDB().getLastestSuiteRunFromID(suite_id, timestamp);
		return new Gson().toJson(array);
	}
	
	
}
