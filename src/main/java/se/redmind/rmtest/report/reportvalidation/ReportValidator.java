package se.redmind.rmtest.report.reportvalidation;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.update.ReportStatementBuilder;
import se.redmind.rmtest.db.update.TestCaseStatementBuilder;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportValidator {
	
	private ReadReportFromDB readFromDB;
	private ReportLoader loader;
	private ReportXMLParser parser;
	private Connection connection;
	private String filename;
	private Report report;
	private File reportFile;
	
	public ReportValidator(String filename) {
		this.filename = filename;
		this.connection = DBCon.getDbInstance().getConnection();
		this.readFromDB = new ReadReportFromDB(connection);
		this.loader = new ReportLoader();
		this.parser = new ReportXMLParser();
		loadReport();
	}
	
	private void loadReport(){
		this.reportFile = loader.getXMLReportByFileName(filename);
		this.report = parser.getSimpleReportFromFile(reportFile);
	}
	
	public boolean reportExists(){
		return readFromDB.reportExists(report.getTimestamp());
	}
	
	public void saveReport(){
		report.convertToFullReport();
		//int id = readFromDB.getMaxID();
		ReportStatementBuilder builder = new ReportStatementBuilder(connection);
		PreparedStatement reportStatement = builder.reportStatement(report);
		
		TestCaseStatementBuilder testCaseStatementBuilder = new TestCaseStatementBuilder(connection);
		PreparedStatement testCaseStatement = testCaseStatementBuilder.testCaseStatement(report.getTestCaseArray());
		
		try {
			connection.setAutoCommit(false);
			reportStatement.executeBatch();
			testCaseStatement.executeBatch();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
