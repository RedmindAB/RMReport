package se.redmind.rmtest.report.reportvalidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.create.osinserter.OSInserter;
import se.redmind.rmtest.db.read.ReadOsFromDB;
import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

public class DriverValidation {

	private List<Driver> driverArray;
	
	public DriverValidation(Report report) {
		driverArray = new ArrayList<Driver>();
		fillDriverArray(report);
	}
	
	private void fillDriverArray(Report report){
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		HashSet<String> osNames = new HashSet<String>();
		for (ReportTestCase reportTestCase : testCaseArray) {
			String os = reportTestCase.getDriver().getOs();
			String osVer = reportTestCase.getDriver().getOsVer();
			String osConcat = os+osVer;
			if (!osNames.contains(osConcat)) {
				osNames.add(osConcat);
				driverArray.add(reportTestCase.getDriver());
			}
		}
	}
	
	public HashMap<String, Integer> getOSMap(){
		ReadOsFromDB osFromDB = new ReadOsFromDB();
		//Get componenets to compare
		HashMap<String, Integer> osIds = osFromDB.getOsVersionAndId();
		OSInserter osInserter = new OSInserter();
		HashSet<String> addedOS = new HashSet<String>();
		boolean addedNew = false;
		//see if the os with version exists in db, if not add it to the batch.
		for (Driver driver : driverArray) {
			String osNameAndVer = driver.getOs()+driver.getOsVer();
			//if the driver dose not exists in the db and is not added yet, add it to the batch
			if (!osIds.containsKey(osNameAndVer) && !addedOS.contains(osNameAndVer)) {
				System.out.println(osNameAndVer);
				osInserter.addOsToBatch(driver.getOs(), driver.getOsVer());
				addedOS.add(osNameAndVer);
				addedNew = true;
			}
		}
		if (addedNew) {
			int res = osInserter.executeBatch();
			System.out.println(res);
			return osFromDB.getOsVersionAndId();
		}
		return osIds;
	}
	
}
