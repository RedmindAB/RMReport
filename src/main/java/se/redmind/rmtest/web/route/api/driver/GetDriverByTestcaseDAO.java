package se.redmind.rmtest.web.route.api.driver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

public class GetDriverByTestcaseDAO {

	public String getDriverByTestcaseId(int testCaseId, String timestamp){
		ReadTestcaseFromDB readTestCase = new ReadTestcaseFromDB();
		JsonArray jsonArray  = readTestCase.getDriverAndMessageFromLastRun(testCaseId, timestamp);
		return new Gson().toJson(jsonArray);
		
	}
}
