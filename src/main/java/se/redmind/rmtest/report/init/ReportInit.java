package se.redmind.rmtest.report.init;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.lookup.report.ReportExist;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.reporthandler.ReportHandler;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.util.TimeEstimator;

public class ReportInit {

	private String reportPath;
	private ReportHandler reportHandler;

	public ReportInit() {
		reportHandler = new ReportHandler();
	}
	
	public ReportInit(String reportPath){
		this.reportPath = reportPath;
		reportHandler = new ReportHandler(reportPath);
	}
	
	public int initReports(){
		System.out.println("Checking reports!");
		List<File> reportFiles = reportHandler.getReportFiles();
		System.out.println("Found "+reportFiles.size()+" reports");
		int addedReports = 0;
		ReportExist reportExist = new ReportExist();
		Connection connection = DBCon.getDbInstance().getConnection();
		try {
			connection.setAutoCommit(false);
			TimeEstimator estimator = new TimeEstimator(reportFiles.size(), 8);
			System.out.println(estimator.getTopMeter());
			System.out.print(" ");
			estimator.start();
			for (File file : reportFiles) {
					ReportValidator reportValidator = getReportValidator(file);
					Report report = reportValidator.getReport();
					boolean existsInDB = reportExist.reportExists(report.getTimestamp(), report.getSuiteName());
					if (!existsInDB) {
						reportValidator.saveReport();
						addedReports++;
					}
					estimator.addTick();
					if (estimator.isMeassure()) {
						System.out.print("#");
					}
			}
			System.out.print("\n");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return addedReports;
	}
	
	private ReportValidator getReportValidator(File file){
		if (reportPath != null) {
			return new ReportValidator(file, new ReportLoader(reportPath, false));
		}
		return new ReportValidator(file.getName());
	}
	
}
