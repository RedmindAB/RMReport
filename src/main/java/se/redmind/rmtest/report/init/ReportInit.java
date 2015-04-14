package se.redmind.rmtest.report.init;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.lookup.report.ReportExist;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.reporthandler.ReportHandler;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.report.sysout.ReportSystemOutPrintFile;
import se.redmind.rmtest.util.TimeEstimator;

public class ReportInit {
	
	Logger log = LogManager.getLogger(ReportInit.class);

	private String reportPath;
	private ReportHandler reportHandler;

	public ReportInit(String reportPath){
		this.reportPath = reportPath;
		reportHandler = new ReportHandler(reportPath);
	}
	
	public int initReports(){
		List<File> reportFiles = null;
		try {
			reportFiles = reportHandler.getReportFiles();
		} catch (Exception e) {
			return 0;
		}
		System.out.println("Checking "+reportPath);
		System.out.println("Found "+reportFiles.size()+" reports");
		int addedReports = 0;
		ReportExist reportExist = new ReportExist();
		Connection connection = DBCon.getDbInstance().getConnection();
		File currentFile = null;
		try {
			connection.setAutoCommit(false);
			TimeEstimator estimator = new TimeEstimator(reportFiles.size());
			System.out.println(estimator.getTopMeter());
			System.out.print(" ");
			estimator.start();
			for (File file : reportFiles) {
					currentFile = file;
					ReportValidator reportValidator = getReportValidator(file);
					Report report = reportValidator.getReport();
					boolean existsInDB = reportExist.reportExists(report.getTimestamp(), report.getSuiteName());
					if (!existsInDB) {
						reportValidator.saveReport();
						ReportSystemOutPrintFile sysoFile = new ReportSystemOutPrintFile(reportValidator);
						sysoFile.copyReportOutputFile();
						addedReports++;
					}
					estimator.addTick();
					estimator.meassure();
			}
			System.out.print("\n");
			connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error("Could not rollback database: "+e1.getMessage());
			}
			log.error("Error inserting report: "+currentFile.getAbsolutePath());
			System.err.println("Error inserting report, check if the file contains errors: "+currentFile.getAbsolutePath());
		}
		return addedReports;
	}
	
	private ReportValidator getReportValidator(File file){
		if (reportPath != null) {
			return new ReportValidator(file, new ReportLoader(reportPath, false));
		}
		return new ReportValidator(file.getName(), reportPath);
	}
	
}
