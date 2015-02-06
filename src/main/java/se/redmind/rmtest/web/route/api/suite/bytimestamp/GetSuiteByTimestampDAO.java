package se.redmind.rmtest.web.route.api.suite.bytimestamp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.read.ReadSuiteFromDB;

public class GetSuiteByTimestampDAO {

	public String getSuiteByTimestamp(int suite_id, String timestamp){
		JsonArray array = new ReadSuiteFromDB().getSuiteRunByTimestamp(suite_id, timestamp);
		return new Gson().toJson(array);
	}
	
	
}
