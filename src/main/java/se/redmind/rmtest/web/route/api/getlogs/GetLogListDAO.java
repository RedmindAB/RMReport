package se.redmind.rmtest.web.route.api.getlogs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import se.redmind.rmtest.report.reporthandler.ReportHandler;

public class GetLogListDAO {

	public GetLogListDAO() {
		
	}
	
	public String getReports(){
		JsonArray reportList = new ReportHandler().getLogList();
		return new Gson().toJson(reportList);
	}
}
