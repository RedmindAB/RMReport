package se.redmind.rmtest.report.reporthandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportHandler {

	private ReportLoader loader;
	private ReportXMLParser parser;
	
	public ReportHandler() {
		String whereAmI = System.getProperty("user.dir");
		this.loader = new ReportLoader(whereAmI+"/testfiles", false);
		this.parser = new ReportXMLParser();
	}
	
	public JsonArray getLogList(){
		 List<File> files = loader.getXMLReports();
		 JsonArray reports = new JsonArray();
		 for (File file : files) {
			 reports.add(parser.getReportFromFile(file).getAsJsonObject());
		 }
		 return reports;
	}
	
	public JsonObject getReportByTimestamp(String timestamp){
		return null;
	}
	
}
