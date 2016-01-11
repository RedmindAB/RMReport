package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

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
import se.redmind.report.auto.nav.VisualNav;
import se.redmind.rmtest.report.auto.utils.ErrorMsg;
import se.redmind.rmtest.selenium.framework.RMReportScreenshot;
import se.redmind.rmtest.selenium.grid.DriverNamingWrapper;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.selenium.grid.Parallelized;
import test.java.se.redmind.suite.RMReportAutoSuite;

@RunWith(Parallelized.class)
public class VisualPage {

	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private VisualNav nav;

	    public VisualPage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
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
	    	this.nav = new VisualNav(this.tDriver);
	    }
	    
	    /**
	     * ID: VIS-A.01.03
	     * <br> Edited: 2015-06-09 
	     * <br> Purpose: Makes sure that you can open the "Console prints" modal
	     */
	    @Test
	    public void openSysos(){
	    	nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			nav.openMethod();
	    	nav.openSysos();
	    	assertTrue(ErrorMsg.SysoDidNotOpen + "1 \n", nav.isSysosOpen());
	    }	    

	    /**
	     * ID: VIS-A.01.06
		 * <br> Edited: 2015-06-09
		 * <br> Purpose: Makes sure that you can close the "Console prints" modal
		 */
		@Test
		public void openCloseSyso(){
			nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			nav.openSysos();
			nav.closeSysos();
			assertTrue(ErrorMsg.SysoDidNotClose + "1 \n",nav.isSysosClosed());
		}
		
		/**
		 * ID: VIS-A.01.01
		 * <br> Edited: 2015-06-09
		 * <br> Purpose: Makes sure that "Classes" text/button (in "Home/Classes/Methods") redirects you to classes in visualizer
		 */
		@Test
		public void chooseAnotherScope(){
			nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			String methods = nav.getMethodTitle();
			nav.goToScopeByID("1");
			String classes = nav.getClassTitle();
			assertNotEquals(ErrorMsg.ScopeUnableToChange + "1 \n", methods, classes);
		}
		
		/**
		 * ID: VIS-A.01.02
		 * <br> Edited: 2015-06-09
		 * <br> Purpose: Makes sure that you are able to change timestamp
		 */
		@Test
		public void chooseTimestamp(){
			nav.changeProject("MockedTestSuite");
			nav.changeTimestamp("2015-01-01 08:00");
			assertEquals(ErrorMsg.TimestampIsDifferent + "1 \n", "2015-01-01 08:00" ,nav.getCurrentTimestamp());	
		}
		
	    /**
	     * ID: VIS-A.01.05
		 * <br> Edited: 2015-06-09
		 * <br> Purpose: Makes sure you are able to change project
		 */
	    @Test
	    public void changeProject(){
	    	nav.changeProject("customSuiteName");
			assertEquals(ErrorMsg.ProjectNameIsDifferent + "1 \n", "customSuiteName", nav.getCurrentProjectName());	
	    }
		
	    /**
	     * ID: VIS-A.01.04
		 * <br> Edited: 2015-06-09
		 * <br> Purpose: Makes sure that there is a thumbnail of a screenshot
		 */
		@Test
		public void isThumbnailPresent(){
			nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			nav.openMethod();
			assertTrue(ErrorMsg.ScreenshotThumbnailNotPresent + "1 \n", nav.isThumbnailPresent("0"));
		}
		
		@Test
		public void isScreenShotPresent(){
			nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			nav.openMethod();
			nav.openScreenshot("0");
			nav.waitForSlideAnimation();
			assertTrue(nav.isScreenshotPresent());
		} 
		
		@Test
		public void isScreenShotSwitched(){
			nav.changeProject("customSuiteName");
			nav.chooseClass("se.redmind.rmtest.selenium.example.RandomClass0");
			nav.openMethod();
			nav.openScreenshot("0");
			assertTrue(nav.isScreenShotSwitched());
		}
}


	    	
	    
	    

