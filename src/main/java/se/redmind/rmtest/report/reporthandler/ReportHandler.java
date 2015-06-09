package se.redmind.rmtest.report.reporthandler;

import java.io.File;
import java.util.List;

import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

import com.google.gson.JsonObject;

public class ReportHandler {

	private ReportLoader loader;
	private ReportXMLParser parser;
	
	public ReportHandler(String path){
		this.loader = new ReportLoader(path, false);
		this.parser = new ReportXMLParser();
	}
	
	public List<File> getReportFiles(){
		return loader.getXMLReports();
	}
	
//	public JsonArray getReportList(){
//		 List<File> files = loader.getXMLReports();
//		 JsonArray reports = new JsonArray();
//		 for (File file : files) {
//			 reports.add(parser.getReportFromFile(file).getAsJsonObject());
//		 }
//		 return reports;
//	}
	
	public JsonObject getReportByTimestamp(String timestamp){
		return null;
	}
	
}
