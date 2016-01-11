package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import se.redmind.report.auto.nav.StartNav;
import se.redmind.rmtest.report.auto.utils.ErrorMsg;
import se.redmind.rmtest.selenium.framework.RMReportScreenshot;
import se.redmind.rmtest.selenium.grid.DriverNamingWrapper;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.selenium.grid.Parallelized;
import test.java.se.redmind.suite.RMReportAutoSuite;



@RunWith(Parallelized.class)
public class StartPage {


	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private StartNav nav;

	    public StartPage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
	        this.urlContainer = driverWrapper;
	        this.driverDescription = driverDescription;
	        this.rmrScreenshot = new RMReportScreenshot(urlContainer);
	    }
	    
	    private static Object[] getDrivers() {
//	        return DriverProvider.getDrivers("rmDeviceType", "mobile");
//	    	return DriverProvider.getDrivers(Platform.ANDROID);
	    	return DriverProvider.getDrivers();

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
	    	this.nav = new StartNav(this.tDriver);
	    }
	    
	    /**
	     * ID: STA-A.01.01
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: Makes sure that the Redmind logo is present and takes a screenshot
	     */
	    @Test
	    public void isRedmindLogoPresent(){
	    	WebElement redmindlogo = this.nav.getRedmindLogo();
	    	new RMReportScreenshot(urlContainer).takeScreenshot("logo");
	    	assertTrue(ErrorMsg.LogoNotDisplayed + "1 \n", redmindlogo.isDisplayed());
	    }
	    
	    /**
	     * ID: STA-A.01.02
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: Makes sure that the Visualizer button is present and takes a screenshot
	     */
	    
	    @Test
	    public void isScreenshotPresent(){
	    	WebElement screenshot = this.nav.getScreenshot();
	    	new RMReportScreenshot(urlContainer).takeScreenshot("logo");
	    	assertTrue(screenshot.isDisplayed());
	    }
	    
	    /**
	     * ID: STA-A.01.03
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: Makes sure that Reports button is present and takes a screenshot
	     */
	    
	    @Test
	    public void isGraphViewPresent(){
	    	WebElement graphView = this.nav.getGraphView();
	    	new RMReportScreenshot(urlContainer).takeScreenshot("logo");
	    	assertTrue(graphView.isDisplayed());
	    }
	    

}