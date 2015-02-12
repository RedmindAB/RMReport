package se.redmind.rmtest.report.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class DriverParserTest {

	@Test
	public void driverParserTest() {
		Driver parser = new Driver("Android_4.4.4_HTC ONE_chrome_42");
		String os = parser.getOs();
		assertEquals("Android", os);
		String osVer = parser.getOsVer();
		assertEquals("4.4.4", osVer);
		String device = parser.getDevice();
		assertEquals("HTC ONE", device);
		String browser = parser.getBrowser();
		assertEquals("chrome", browser);
		String browserVer = parser.getBrowserVer();
		assertEquals("42", browserVer);
	}
	
	@Test
	public void badName(){
		Driver parser = new Driver("InitializationError");
		String os = parser.getOs();
		assertNull(os);
	}
	
	@Test
	public void shortName(){
		Driver parser = new Driver("Android_4.4.4_HTC ONE_chrome");
		String os = parser.getOs();
		assertEquals("Android", os);
		String browserVer = parser.getBrowserVer();
		assertNull(browserVer);
	}

}
