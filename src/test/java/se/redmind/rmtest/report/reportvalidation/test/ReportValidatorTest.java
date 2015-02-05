package se.redmind.rmtest.report.reportvalidation.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;

public class ReportValidatorTest {
 	private static String filename = "TEST-test.java.se.redmind.rmtest.selenium.example.CreateLogTests-20150202-140728.xml";

	
	@Test
	public void insertReport() {
//		DBCon.getDbTestInstance().dropDatabase();
		ReportValidator reportValidator = new ReportValidator(filename);
		for (int i = 0; i < 10; i++) {
			boolean exists = reportValidator.reportExists();
			if (exists) {
				reportValidator.saveReport();
			}
		}
		
	}

}
