package se.redmind.rmtest.web.route.api.getspecificsuite;



import se.redmind.rmtest.db.read.ReadSuiteFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetSpecificSuiteDAO {

	public String getSpecificSuite(String timestamp, int suiteid){
		ReadSuiteFromDB readSuite = new ReadSuiteFromDB();
		JsonArray jsonArray  = readSuite.getSpecificSuiteRunFromIdAndTimestamp(timestamp, suiteid);
		return new Gson().toJson(jsonArray);
		
	}
}
