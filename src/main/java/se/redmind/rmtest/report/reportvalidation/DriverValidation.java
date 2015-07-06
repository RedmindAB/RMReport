package se.redmind.rmtest.report.reportvalidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.create.browserinserter.BrowserInserter;
import se.redmind.rmtest.db.create.deviceinserter.DeviceInserter;
import se.redmind.rmtest.db.create.osinserter.OSInserter;
import se.redmind.rmtest.db.lookup.browser.BrowserDdLookup;
import se.redmind.rmtest.db.lookup.device.DeviceDdLookup;
import se.redmind.rmtest.db.lookup.os.OsDbLookup;
import se.redmind.rmtest.report.parser.Driver;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.xml.XMLReport;
import se.redmind.rmtest.report.parser.xml.XMLReportTestCase;

public class DriverValidation {

	private List<Driver> driverArray;
	private HashMap<String, Integer> osMap;
	private HashMap<String, Integer> browserMap;
	private HashMap<String, Integer> deviceMap;
	
	public DriverValidation(Report report) {
		driverArray = new ArrayList<Driver>();
		fillDriverArray(report);
		osMap = getOSMap();
		browserMap = getBrowserMap();
		deviceMap = getDeviceMap();
	}
	
	private void fillDriverArray(Report report){
		List<ReportTestCase> testCaseArray = report.getTestCaseArray();
		HashSet<String> osNames = new HashSet<String>();
		for (ReportTestCase reportTestCase : testCaseArray) {
			String driverString = reportTestCase.getDriver().getDriverFullName();
			if (!osNames.contains(driverString)) {
				osNames.add(driverString);
				if (!reportTestCase.getDriver().isBroken()) {
					driverArray.add(reportTestCase.getDriver());
				}
			}
		}
	}
	
	public HashMap<String, Integer> getOSMap(){
		OsDbLookup osFromDB = new OsDbLookup();
		//Get componenets to compare
		OSInserter osInserter = new OSInserter();
		HashMap<String, Integer> osIds = osFromDB.getOsVersionAndId();
		HashSet<String> addedOS = new HashSet<String>();
		boolean addedNew = false;
		//see if the os with version exists in db, if not add it to the batch.
		for (Driver driver : driverArray) {
			String osNameAndVer = driver.getOs()+driver.getOsVer();
			//if the driver dose not exists in the db and is not added yet, add it to the batch
			if (!osIds.containsKey(osNameAndVer) && !addedOS.contains(osNameAndVer)) {
				osInserter.addOsToBatch(driver.getOs(), driver.getOsVer());
				addedOS.add(osNameAndVer);
				addedNew = true;
			}
		}
		if (addedNew) {
			int res = osInserter.executeBatch();
//			System.out.println(res);
			return osFromDB.getOsVersionAndId();
		}
		return osIds;
	}
	
	public HashMap<String, Integer> getDeviceMap(){
		DeviceDdLookup deviceFromDB = new DeviceDdLookup();
		//Get componenets to compare
		DeviceInserter inserter = new DeviceInserter();
		HashMap<String, Integer> deviceIds = deviceFromDB.getDeviceNameAndId();
		HashSet<String> addedDevice = new HashSet<String>();
		boolean addedNew = false;
		//see if the os with version exists in db, if not add it to the batch.
		for (Driver driver : driverArray) {
			String deviceName = driver.getDevice();
			//if the driver dose not exists in the db and is not added yet, add it to the batch
			if (!deviceIds.containsKey(deviceName) && !addedDevice.contains(deviceName)) {
//				System.out.println(osNameAndVer);
				inserter.addOsToBatch(driver.getDevice());
				addedDevice.add(deviceName);
				addedNew = true;
			}
		}
		if (addedNew) {
			int res = inserter.executeBatch();
//			System.out.println(res);
			return deviceFromDB.getDeviceNameAndId();
		}
		return deviceIds;
	}
	
	public HashMap<String, Integer> getBrowserMap(){
		BrowserDdLookup browserFromDB = new BrowserDdLookup();
		//Get componenets to compare
		BrowserInserter browserInserter = new BrowserInserter();
		HashMap<String, Integer> osIds = browserFromDB.getBrowserVersionAndId();
		HashSet<String> addedOS = new HashSet<String>();
		boolean addedNew = false;
		//see if the os with version exists in db, if not add it to the batch.
		for (Driver driver : driverArray) {
			String osNameAndVer = driver.getBrowser()+driver.getBrowserVer();
			//if the driver dose not exists in the db and is not added yet, add it to the batch
			if (!osIds.containsKey(osNameAndVer) && !addedOS.contains(osNameAndVer)) {
				browserInserter.addBrowserToBatch(driver.getBrowser(), driver.getBrowserVer());
				addedOS.add(osNameAndVer);
				addedNew = true;
			}
		}
		if (addedNew) {
			int res = browserInserter.executeBatch();
//			System.out.println(res);
			return browserFromDB.getBrowserVersionAndId();
		}
		return osIds;
	}
	
	public int getDeviceID(String deviceName){
		return deviceMap.get(deviceName);
	}
	
	public int getBrowserID(String browserName, String browserVer){
		return browserMap.get(browserName+browserVer);
	}
	
	public int getOsID(String osName, String osVer){
		return osMap.get(osName+osVer);
	}
	
}
