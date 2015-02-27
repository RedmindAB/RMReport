package se.redmind.rmtest.report.init;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.report.reporthandler.ReportHandler;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.util.TimeEstimator;

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
		Connection connection = DBCon.getDbInstance().getConnection();
		try {
			connection.setAutoCommit(false);
			TimeEstimator estimator = new TimeEstimator(reportFiles.size(), 20);
			estimator.start();
			for (File file : reportFiles) {
					ReportValidator reportValidator = new ReportValidator(file.getName());
					boolean existsInDB = reportValidator.reportExists();
					if (!existsInDB) {
						reportValidator.saveReport();
						addedReports++;
					}
					estimator.addTick();
					if (estimator.isMeassure()) {
						System.out.println("Estimated time left: "+estimator.getEstimatedTimeLeftDouble()+" sec");
					}
			}
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
	
}
