package se.redmind.rmtest.report.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import se.redmind.rmtest.report.reportloader.ReportLoader;

public class ReportLoaderTest {
	
	public static String path = System.getProperty("user.dir")+"/reports_for_test";
	public static String specificReportFileName = "TEST-test.java.se.redmind.rmtest.selenium.example.MockedTestSuite-20150101-080000.xml";
	private static ReportLoader loader;
	
	@BeforeClass
	public static void beforeClass(){
		loader = getReportLoader();
	}
	
	@Test
	public void createObjectWithSpecificPath(){
		ReportLoader loader = getReportLoader();
		assertEquals(path, loader.getReportFolderPath());
	}
	
	//The output file is not used at this moment.
	@Ignore 
	@Test
	public void getOutputFileFromReport(){
		ArrayList<File> files = loader.getXMLReports();
		File report = files.get(0);
		File outputFile = loader.getSystemOutFile(report);
		assertTrue(outputFile != null);
	}
	
	//The output text file is not user at the moment.
	@Ignore
	@Test
	public void getMavenTestOutputFileFromReport(){
		ArrayList<File> files = loader.getXMLReports();
		File report = files.get(0);
		File outputFile = loader.getOutputFile(report);
		assertTrue(outputFile != null);
	}
	
	@Test
	public void getXMLFiles(){
		ReportLoader loader = getReportLoader();
		ArrayList<File> xmlFiles = loader.getXMLReports();
		assertEquals(10, xmlFiles.size());
	}
	
	@Test
	public void getSpecificXMLReport(){
		File file = loader.getReportByFileName(specificReportFileName);
		assertEquals(specificReportFileName, file.getName());
	}
	
	
	private static ReportLoader getReportLoader(){
		ReportLoader loader = new ReportLoader(path, false);
		return loader;
	}
}
