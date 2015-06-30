package se.redmind.rmtest.report.reportvalidation;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.report.test.ReportLoaderTest;

public class ValidFilenameTest {
	
	public static String path = System.getProperty("user.dir")+"/reports_for_test";
	public static String specificReportFileName = ReportLoaderTest.specificReportFileName;
	public static File file;
	public static ReportValidator val;
	private static ReportLoader loader;
	
	@BeforeClass
	public static void beforeClass(){
		loader = getReportLoader();
		file = loader.getReportByFileName(specificReportFileName);
		val = new ReportValidator(file, loader);
	}

	
	@Test
	public void validFilename_true(){
		boolean validFilename = val.isValidFilename();
		assertEquals(true, validFilename);
	}
	
	@Test
	public void almostTrueFilename(){
		String filename = "awda[awdawd_awd_awdawd].xml";
		boolean validFilename = val.isValidFilename(filename);
		assertEquals(false, validFilename);
	}
	
	@Test
	public void littleAtTheTime(){
		String filename = "TEST-abc-12341234-123456.xml";
		boolean validFilename = val.isValidFilename(filename);
		assertEquals(true, validFilename);
	}
	
	@Test
	public void matchTest(){
		String filename = "TEST-.xml";
		boolean validFilename = val.isValidFilename(filename);
		assertEquals(false, validFilename);
	}
	
	@Test
	public void reallyBadFilename(){
		String filename = "awd_awd_awdawd2.xml";
		boolean validFilename = val.isValidFilename(filename);
		assertEquals(false, validFilename);
	}
	
	@Test
	public void reallyBadFilename2(){
		String filename = "awd_awdauhawhuidwhui2189178213h_awdawd2.xml";
		boolean validFilename = val.isValidFilename(filename);
		assertEquals(false, validFilename);
	}
	
	private static ReportLoader getReportLoader(){
		ReportLoader loader = new ReportLoader(path, false);
		return loader;
	}

}
