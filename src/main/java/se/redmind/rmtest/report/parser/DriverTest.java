package se.redmind.rmtest.report.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DriverTest {

	@Test
	public void testTrueName() {
		String driverName = "Android_4.4.4_HTC One_safari_41";
		Driver driver = new Driver(driverName);
		String browser = driver.getBrowser();
		assertEquals("safari", browser);
		String browserVer = driver.getBrowserVer();
		assertEquals("41", browserVer);
		String device = driver.getDevice();
		assertEquals("HTC One", device);
		String os = driver.getOs();
		assertEquals("Android", os);
		String osVer = driver.getOsVer();
		assertEquals("4.4.4", osVer);
	}

	
	@Test
	public void testIsBroken(){
		String driverName = "Android_4.4.4_HTC One_safari_";
		Driver driver = new Driver(driverName);
		assertTrue(driver.isBroken());
	}
	
	@Test
	public void testIsNotBroken(){
		String driverName = "Android_4.4.4_HTC One_safari_41";
		Driver driver = new Driver(driverName);
		assertFalse(driver.isBroken());
	}
}
