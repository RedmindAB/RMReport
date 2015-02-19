package se.redmind.rmtest.report.init;

import java.io.File;
import java.util.List;

import se.redmind.rmtest.report.reporthandler.ReportHandler;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;

public class ReportInit {

	
	
	private ReportHandler reportHandler;

	public ReportInit() {
		reportHandler = new ReportHandler();
	}
	
	public ReportInit(String reportPath){
		reportHandler = new ReportHandler(reportPath);
	}
	
	public int initReports(){
		System.out.println("Checking reports!");
		List<File> reportFiles = reportHandler.getReportFiles();
		System.out.println("Found "+reportFiles.size()+" reports");
		int addedReports = 0;
		for (File file : reportFiles) {
			ReportValidator reportValidator = new ReportValidator(file.getName());
			boolean existsInDB = reportValidator.reportExists();
			if (!existsInDB) {
				reportValidator.saveReport();
				addedReports++;
			}
		}
		return addedReports;
	}
	
}
