package se.redmind.rmtest.web.route.api.getdrivertestcase;

import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetDriverAndTestcaseDAO {

	public String getDriverAndTestcase(String driver, int id){
		ReadReportFromDB readReport = new ReadReportFromDB();
		JsonArray jsonArray  = readReport.getDriverAndTestcaseInfo(driver, id);
		return new Gson().toJson(jsonArray);
		
	}
}

