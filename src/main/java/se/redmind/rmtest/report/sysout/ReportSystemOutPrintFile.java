package se.redmind.rmtest.report.sysout;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;

public class ReportSystemOutPrintFile {
	
	private Logger log = LogManager.getLogger(ReportSystemOutPrintFile.class);
	public static final String BASEDIR = System.getProperty("user.dir")+"/systemout";
	
	private ReportValidator reportValidator;
	private File reportFile;
	private File sysOutputFile;
	private Report report;
	
	public ReportSystemOutPrintFile(ReportValidator reportValidator) {
		this.reportValidator = reportValidator;
		this.report = reportValidator.getReport();
		this.reportFile = reportValidator.getReportFile();
	}
	
	public void copyReportOutputFile(){
		sysOutputFile = this.reportValidator.getReportLoader().getSystemOutFile(reportFile);
		if (sysOutputFile == null) {
			return;
		}
		int suiteid = reportValidator.getSuiteID(report.getSuiteName());
		long timestamp = report.getTimestamp();
		File folder = new File(BASEDIR+"/"+suiteid);
		folder.mkdirs();
		File saveFile = new File(folder.getAbsolutePath()+timestamp+".txt");
		try {
			Files.copy(sysOutputFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}
	}
	
}
