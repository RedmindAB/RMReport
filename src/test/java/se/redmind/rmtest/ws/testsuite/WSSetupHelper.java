package se.redmind.rmtest.ws.testsuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.init.ReportInit;

public class WSSetupHelper{

	private static String reportPath = System.getProperty("user.dir")+"/reports_for_test";
	private static String dbPath = System.getProperty("user.dir")+"/testRMtest.db";
	
	public WSSetupHelper() {
		beforeClass();
	}
	
	public static void beforeClass(){
		boolean testmode = DBCon.isTestmode();
		if (!testmode) {
			deleteOldDatabase();
			DBCon.getDbTestInstance();
			new ReportInit(reportPath).initReports();
			new InMemoryDBHandler("testRMTest").init();
		}
	}
	
	public static void deleteOldDatabase(){
		//remove testdb
		File dbFile = new File(dbPath);
		try {
			Files.delete(dbFile.toPath());
			System.out.println("Test DB deleted");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
