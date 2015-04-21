package se.redmind.rmtest.ws.testsuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.webservicetests.GetClassesWSTest;
import se.redmind.rmtest.webservicetests.GetDriverByTestcaseWSTest;
import se.redmind.rmtest.webservicetests.GetGraphDataWSTest;
import se.redmind.rmtest.webservicetests.GetGraphOptionsWSTest;
import se.redmind.rmtest.webservicetests.GetLatestSuiteWSTest;
import se.redmind.rmtest.webservicetests.GetMethodsWSTest;
import se.redmind.rmtest.webservicetests.GetSuiteByTimestampWSTest;
import se.redmind.rmtest.webservicetests.GetSuiteSysoWSTest;
import se.redmind.rmtest.webservicetests.GetSuitesWSTest;
import se.redmind.rmtest.webservicetests.PassFailClassWSTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({GetMethodsWSTest.class,
					GetSuitesWSTest.class,
					GetLatestSuiteWSTest.class,
					GetDriverByTestcaseWSTest.class,
					GetClassesWSTest.class, 
					GetGraphDataWSTest.class,
					GetGraphOptionsWSTest.class,
					GetSuiteByTimestampWSTest.class,
					PassFailClassWSTest.class,
					GetSuiteSysoWSTest.class})

public class WSTestSuite{

	private static String reportPath = System.getProperty("user.dir")+"/reports_for_test";
	private static String dbPath = System.getProperty("user.dir")+"/testRMtest.db";
	
	@BeforeClass()
	public static void beforeClass(){
		DBCon.getDbTestInstance();
		new ReportInit(reportPath).initReports();
		new InMemoryDBHandler("testRMTest").init();
//		new RMTRoute(4567);
	}
	
	@AfterClass()
	public static void afterClass(){
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
