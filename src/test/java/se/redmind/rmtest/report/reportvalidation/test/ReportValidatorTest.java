package se.redmind.rmtest.report.reportvalidation.test;

import static org.junit.Assert.*;

import org.junit.Test;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;

public class ReportValidatorTest {
 	private static String filename = "TEST-test.java.se.redmind.rmtest.selenium.example.CreateLogTests-20150121-160906.xml";

	
	@Test
	public void insertReport() {
		DBCon.getDbInstance().dropDatabase();
		ReportValidator reportValidator = new ReportValidator(filename);
		for (int i = 0; i < 10; i++) {
			boolean exists = reportValidator.reportExists();
//			if (exists) {
				reportValidator.saveReport();
//			}
		}
		
	}

}
