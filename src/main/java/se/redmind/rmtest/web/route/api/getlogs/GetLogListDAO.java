package se.redmind.rmtest.web.route.api.getlogs;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.redmind.rmtest.report.gson.ReportSerializer;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.Reports;
import se.redmind.rmtest.report.reporthandler.ReportHandler;

public class GetLogListDAO {

	public GetLogListDAO() {
		
	}
	
	public String getReports(){
		List<Report> reportList = new ReportHandler().getLogList();
		Reports reports = new Reports();
		reports.setReports(reportList);
		Gson g = new GsonBuilder().registerTypeAdapter(Reports.class, new ReportSerializer()).create();
		String json = g.toJson(reports);
		return json;
	}
}
