package se.redmind.rmtest.report.reportvalidation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.create.osinserter.OSInserter;
import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

public class DriverValidation {

	private Report report;
	private List<Driver> osArray;
	
	public DriverValidation(Report report) {
		this.report = report;
		fillOsArray(report);
	}
	
	private void fillOsArray(Report report){
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		HashSet<String> osNames = new HashSet<String>();
		for (ReportTestCase reportTestCase : testCaseArray) {
			String os = reportTestCase.getDriver().getOs();
			String osVer = reportTestCase.getDriver().getOsVer();
			String osConcat = os+osVer;
			if (!osNames.contains(osConcat)) {
				osNames.add(osConcat);
				osArray.add(reportTestCase.getDriver());
			}
		}
	}
	
	public HashMap<String, Integer> getOSMap(){
		//Get componenets to compare
		HashMap<String, Integer> osIds = new HashMap<String, Integer>();
		OSInserter osInserter = new OSInserter();
		HashSet<String> addedOS = new HashSet<String>();
		boolean addedNew = false;
		//see if the os with version exists in db, if not add it to the batch.
		for (Driver driver : osArray) {
			String osNameAndVer = driver.getOs()+driver.getOsVer();
			if (!osIds.containsKey(osNameAndVer) && !addedOS.contains(osNameAndVer)) {
				osInserter.addOsToBatch(driver.getOs(), driver.getBrowserVer());
				addedOS.add(osNameAndVer);
				addedNew = true;
			}
		}
		if (addedNew) {
			return osIds;
		}
		return osIds;
	}
	
}
