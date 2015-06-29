package se.redmind.rmtest.report.test;

import org.junit.Test;

public interface ReportInterface {
	
	@Test
	public void getTestCaseList();
	
	@Test
	public void createTestcaseFromFile();
	
	@Test
	public void createReportObject();
	
	@Test
	public void convertSimpleReportToFullReport();
	
	@Test
	public void convertPropName();
	
	@Test
	public void containsTestClasses();

	@Test
	public void getTestCaseDriverValues();
	
	@Test
	public void checkForSkippedTests();
	
	@Test
	public void getSuiteName();
	
	@Test
	public void checkFailures();
	
	@Test
	public void checkErrors();
	
	@Test
	public void checkPassed();
	
	@Test
	public void checkRunTime();
	
}
