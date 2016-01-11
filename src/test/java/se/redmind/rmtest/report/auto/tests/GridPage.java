package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.report.auto.nav.GridNav;
import se.redmind.report.auto.nav.NavbarNav;
import se.redmind.report.auto.nav.StartNav;
import se.redmind.report.auto.nav.VisualNav;
import se.redmind.rmtest.report.auto.utils.ErrorMsg;
import se.redmind.rmtest.selenium.framework.RMReportScreenshot;
import se.redmind.rmtest.selenium.grid.DriverNamingWrapper;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.selenium.grid.Parallelized;
import test.java.se.redmind.suite.RMReportAutoSuite;

@RunWith(Parallelized.class)
public class GridPage {

	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private GridNav nav;
	    

	    public GridPage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
	        this.urlContainer = driverWrapper;
	        this.driverDescription = driverDescription;
	        this.rmrScreenshot = new RMReportScreenshot(urlContainer);
	    }
	    
	    private static Object[] getDrivers() {
//	        return DriverProvider.getDrivers("rmDeviceType", "mobile");
//	    	return DriverProvider.getDrivers(Platform.ANDROID);
	    	return DriverProvider.getDrivers(Platform.MAC);

	    }

	    @Parameterized.Parameters(name = "{1}")
	    public static Collection<Object[]> drivers() {
	        ArrayList<Object[]> returnList = new ArrayList<Object[]>();
	        Object[] wrapperList = getDrivers();
	        for (int i = 0; i < wrapperList.length; i++) {
	            returnList.add(new Object[]{wrapperList[i], wrapperList[i].toString()});
	        }

	        return returnList;
	    }

	    @AfterClass
	    public static void afterTest(){
//	    	DriverProvider.stopDrivers();
	    }
	    

	    @Before
	    public void beforeTest(){
	    	RMReportAutoSuite.startWebServer();
	    	this.tDriver = this.urlContainer.startDriver();
	    	this.nav = new GridNav(this.tDriver);
	    }
	    
	    /**
	     * ID: GRID-A.01.01
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that you are able to open the json modal/grid registration info modal
	     */
	    @Test
	    public void test_gridGetJson(){
	    	nav.clickOnLogo();
	    	String actual = nav.getJsonHeader();
	    	String expected = "Grid Registration info";
	    	assertEquals(ErrorMsg.JsonModalTextIsDifferent + "1 \n", expected, actual);
	    }
	    
}