package se.redmind.rmtest.report.reporthandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Report> getLogList(){
		 List<File> files = loader.getXMLReports();
		 List<Report> reports = new ArrayList<Report>();
		 for (File file : files) {
			 reports.add(parser.getReportFromFile(file));
		 }
		 return reports;
	}
	
}
