package se.redmind.rmtest.report.init;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.lookup.report.ReportExist;
import se.redmind.rmtest.report.parser.xml.XMLReport;
import se.redmind.rmtest.report.reporthandler.ReportHandler;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.report.sysout.ReportSystemOutPrintFile;
import se.redmind.rmtest.util.TimeEstimator;

public class ReportInit {
	
	Logger log = LogManager.getLogger(ReportInit.class);

	private String reportPath;
	private ReportHandler reportHandler;
	private ArrayList<String> brokenReports;
	private int addedReports = 0;
	private ReportExist reportExist;
	private TimeEstimator estimator;

	public ReportInit(String reportPath){
		this.reportPath = reportPath;
		reportHandler = new ReportHandler(reportPath);
		this.brokenReports = new ArrayList<String>();
		reportExist = new ReportExist();
	}
	
	public int initReports(){
		List<File> reportFiles = null;
		try {
			reportFiles = reportHandler.getReportFiles();
		} catch (Exception e) {
			return 0;
		}
		estimator =  new TimeEstimator(reportFiles.size());
		System.out.println("Checking "+reportPath);
		System.out.println("Found "+reportFiles.size()+" reports");
		Connection connection = DBCon.getDbInstance().getConnection();
		try {
			connection.setAutoCommit(false);
			System.out.println(estimator.getTopMeter());
			System.out.print(" ");
			estimator.start();
			for (File file : reportFiles) {
				try {
					insertReports(file);
				} catch (Exception e) {
					continue;
				}
			}
			System.out.print("\n");
			connection.setAutoCommit(true);
		} catch (Exception e) {
			try {
				log.error("Rolling back database "+e.getMessage());
				connection.rollback();
				connection.commit();
				connection.setAutoCommit(true);
			} catch (Exception e1) {
				log.error("Could not rollback database: "+e1.getMessage());
			}
			e.printStackTrace();
		}
		printBrokenReports();
		return addedReports;
	}

	private void insertReports(File file) throws Exception{
			ReportValidator reportValidator = getReportValidator(file);
			XMLReport report = reportValidator.getReport();
			boolean existsInDB = reportExist.reportExists(report.getTimestamp(), report.getSuiteName());
			if (!existsInDB) {
				boolean saveReport = reportValidator.saveReport();
				if (!saveReport) {
					brokenReports.add(file.getAbsolutePath());
					estimator.addTick();
					estimator.meassure();
					return;
				}
				ReportSystemOutPrintFile sysoFile = new ReportSystemOutPrintFile(reportValidator);
				sysoFile.copyReportOutputFile();
				addedReports++;
			}
			estimator.addTick();
			estimator.meassure();
	}
	

	private void printBrokenReports() {
		if (brokenReports.size() > 0) {
			log.info("Broken reports: "+brokenReports.size());
			for (String reportname : brokenReports) {
				log.info(reportname);
			}
		}
		else System.out.println("No broken reports");
	}

	private ReportValidator getReportValidator(File file){
		if (reportPath != null) {
			return new ReportValidator(file, new ReportLoader(reportPath, false));
		}
		return new ReportValidator(file.getName(), reportPath);
	}
	
}
