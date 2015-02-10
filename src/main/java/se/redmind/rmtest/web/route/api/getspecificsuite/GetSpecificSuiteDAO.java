package se.redmind.rmtest.web.route.api.getspecificsuite;



import se.redmind.rmtest.db.read.ReadSuiteFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetSpecificSuiteDAO {

	public String getSpecificSuite(int suiteid,String timestamp ){
		ReadSuiteFromDB readSuite = new ReadSuiteFromDB();
		JsonArray jsonArray  = readSuite.getSpecificSuiteRunFromIdAndTimestamp(suiteid, timestamp);
		return new Gson().toJson(jsonArray);
		
	}
}
