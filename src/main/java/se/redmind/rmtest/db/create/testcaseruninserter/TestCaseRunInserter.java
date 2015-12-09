package se.redmind.rmtest.db.create.testcaseruninserter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.db.create.testcaseinserter.TestCaseInserter;
import se.redmind.rmtest.db.jdbm.message.MessageDAO;
import se.redmind.rmtest.db.lookup.testcase.TestcaseDbLookup;
import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.reportvalidation.DriverValidation;
import se.redmind.rmtest.util.StringKeyValueParser;

public class TestCaseRunInserter extends DBBridge {
	
	Logger log = LogManager.getLogger(TestCaseRunInserter.class);
	
	private TestCaseInserter testCaseInserter;
	private TestcaseDbLookup readTestcaseFromDB;
	private MessageDAO messageDAO;
	
	
	private final static String INSERT_TESTCASERUN = "INSERT INTO report (suite_id, class_id, testcase_id, timestamp, result, message, os_id, browser_id, device_id, time) "
																	+ "VALUES ({suite_id},{class_id},{testcase_id},'{timestamp}','{result}', '{message}',{os_id},{browser_id},{device_id},{time})";

	public TestCaseRunInserter() {
		testCaseInserter = new TestCaseInserter();
		readTestcaseFromDB = new TestcaseDbLookup();
		messageDAO = MessageDAO.getInstance();
	}
	
	public boolean insertTestCases(Report report, int suiteID, HashMap<String, Integer> classIDs, HashMap<String,Integer> testCases, DriverValidation driverValidation){
		String sql = "";
		try {
			Statement pStatement = connection.createStatement();
			List<ReportTestCase> testCaseArray = report.getTestCaseArray();
			StringKeyValueParser parser = new StringKeyValueParser(INSERT_TESTCASERUN);
			for (ReportTestCase testCase : testCaseArray) {
				try {
					
				HashMap<String, String> map = new HashMap<String,String>();
				Driver driver = testCase.getDriver();
				map.put("suite_id", ""+suiteID);
				Integer classID = classIDs.get(testCase.getClassname());
				map.put("class_id", ""+classID);
				map.put("testcase_id", ""+testCases.get(testCase.getMethodName()+classID));
				map.put("timestamp", ""+report.getTimestamp());
				map.put("result", testCase.getResult());
				map.put("message", extractMessage(testCase.getMessage()));
				
				String osName = driver.getOs();
				String osVer = driver.getOsVer();
				map.put("os_id", ""+driverValidation.getOsID(osName, osVer));
				
				String deviceName = driver.getDevice();
				map.put("device_id", ""+driverValidation.getDeviceID(deviceName));
				
				String browserName = driver.getBrowser();
				String browserVer = driver.getBrowserVer();
				map.put("browser_id", ""+driverValidation.getBrowserID(browserName, browserVer));
				
				map.put("time", ""+testCase.getTime());
				sql = parser.getString(map);
				pStatement.addBatch(sql);
				} catch (Exception e) {
					log.error("Could not insert testcase: "+testCase.getMethodName());
					e.printStackTrace();
					return false;
				}
			}
			long start = System.currentTimeMillis();
			int[] executeBatch = pStatement.executeBatch();
			messageDAO.commit();
			return true;
//			System.out.println(System.currentTimeMillis() - start);
		} catch (SQLException e) {
			try {
				connection.rollback();
				messageDAO.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("Could not insert report. suite name: "+report.getSuiteName()+" timestamp: "+report.getTimestamp()+" message: "+e.getMessage());
			return false;
		}
		
		
	}
	
	private String extractMessage(String message) {
		if (message != null && !message.isEmpty()) {
			return String.valueOf(messageDAO.save(message));
		}
		return "";
	}

	public int getTestCaseID(String testCaseName, int classid){
		int testCaseID = readTestcaseFromDB.getTestCaseID(testCaseName);
		if (testCaseID < 0) {
			testCaseID = insertTestCase(testCaseName, classid);
		}
		return testCaseID;
	}
	
	
	private int insertTestCase(String testCaseName, int classid) {
		boolean success = testCaseInserter.insertTestCase(testCaseName, classid);
		if (success) {
			return readTestcaseFromDB.getTestCaseID(testCaseName);
		}
		return 0;
	}
}
