package se.redmind.rmtest.report.reportvalidation;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class DriverValidationTest {

	static DBCon dbc;
	static String reportPath = System.getProperty("user.dir")+"/reports_for_tests";
	static File file = new ReportLoader(reportPath, false).getXMLReports().get(0);
	static ReportXMLParser parser = new ReportXMLParser();
	static DriverValidation driverValidation;
	
	@BeforeClass
	public static void beforeClass(){
		dbc = DBCon.getDbTestInstance();
		dbc.dropDatabase(dbc.getConnection());
		Report report = parser.getReportFromFile(file);
		driverValidation = new DriverValidation(report);
	}
	
	@Test
	public void test() {
		
	}

}
