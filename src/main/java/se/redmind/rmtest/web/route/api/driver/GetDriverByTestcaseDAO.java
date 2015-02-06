package se.redmind.rmtest.web.route.api.driver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

public class GetDriverByTestcaseDAO {

	public String getDriverByTestcaseId(int testCaseId){
		ReadTestcaseFromDB readTestCase = new ReadTestcaseFromDB();
		JsonArray jsonArray  = readTestCase.getDriverAndMessageFromLastRun(testCaseId);
		return new Gson().toJson(jsonArray);
		
	}
}
