package se.redmind.rmtest.db.create.testcaseruninserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import se.redmind.rmtest.db.create.DBInserter;
import se.redmind.rmtest.report.parser.Report;

public class TestCaseRunInserter extends DBInserter {
	
	private final static String INSERT_TESTCASERUN = "INSERT INTO testcaserun (suite_id, class_id, testcase_id, )";

	public void insertTestCases(Report report, int suiteID, int classID, int testCaseID){
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_TESTCASERUN);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
