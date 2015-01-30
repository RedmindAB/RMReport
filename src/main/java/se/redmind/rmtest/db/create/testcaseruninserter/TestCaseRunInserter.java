package se.redmind.rmtest.db.create.testcaseruninserter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.create.DBInserter;
import se.redmind.rmtest.db.create.testcaseinserter.TestCaseInserter;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.util.StringKeyValueParser;

public class TestCaseRunInserter extends DBInserter {
	
	private TestCaseInserter testCaseInserter;
	private ReadTestcaseFromDB readTestcaseFromDB;
	
	
	private final static String INSERT_TESTCASERUN = "INSERT INTO report (suite_id, class_id, testcase_id, timestamp, result, message, name, driver, time) "
																	+ "VALUES ({suite_id},{class_id},{testcase_id},'{timestamp'},'{result}','{message}','{name}','{driver}',{time})";

	public TestCaseRunInserter() {
		testCaseInserter = new TestCaseInserter();
		readTestcaseFromDB = new ReadTestcaseFromDB(connection);
	}
	
	public void insertTestCases(Report report, int suiteID, HashMap<String, Integer> classIDs, int testCaseID){
		try {
			PreparedStatement pStatement = connection.prepareStatement(INSERT_TESTCASERUN);
			List<ReportTestCase> testCaseArray = report.getTestCaseArray();
			StringKeyValueParser parser = new StringKeyValueParser(INSERT_TESTCASERUN);
			for (ReportTestCase testCase : testCaseArray) {
				HashMap<String, String> map = new HashMap<String,String>();
				map.put("suite_id", ""+suiteID);
				Integer classID = classIDs.get(testCase.getClassName());
				map.put("class_id", ""+classID);
				map.put("testcase_id", ""+getTestCaseID(testCase.getMethodName()));
				map.put("timestamp", report.getTimestamp());
				map.put("result", testCase.getResult());
				map.put("message", testCase.getMessage());
				map.put("name", testCase.getMethodName());
				map.put("driver", testCase.getDriverName());
				map.put("time", ""+testCase.getTime());
				String sql = parser.getString(map);
				pStatement.addBatch(sql);
			}
			pStatement.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public int getTestCaseID(String testCaseName){
		int testCaseID = readTestcaseFromDB.getTestCaseID(testCaseName);
		if (testCaseID < 0) {
			testCaseID = insertTestCase(testCaseName);
		}
		return testCaseID;
	}
	
	
	private int insertTestCase(String testCaseName) {
		boolean success = testCaseInserter.insertTestCase(testCaseName);
		if (success) {
			return readTestcaseFromDB.getTestCaseID(testCaseName);
		}
		return 0;
	}
}
