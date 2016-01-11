package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.report.auto.nav.Chart;
import se.redmind.report.auto.nav.GraphNav;
import se.redmind.rmtest.report.auto.utils.ErrorMsg;
import se.redmind.rmtest.selenium.framework.RMReportScreenshot;
import se.redmind.rmtest.selenium.grid.DriverNamingWrapper;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.selenium.grid.Parallelized;
import test.java.se.redmind.suite.RMReportAutoSuite;



@RunWith(Parallelized.class)
public class GraphPage {


	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private GraphNav nav;

	    public GraphPage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
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
	    	this.nav = new GraphNav(tDriver);
	    }
	    
	    /**
	     * ID: REP-A.01.01
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that the "Choose data to display" function "Pass/Fail" displays the correct data and that the chart title is the same as the option
	     */
	    @Test
	    public void test_ShowPassFail() {
	    	String expected = "Percentage of passed tests";
	    	WebElement chartTitle1 = nav.getChartTitle();
	    	assertEquals(ErrorMsg.ChartTitleIsDifferent + "1 \n", expected, chartTitle1.getText());
	    	nav.option.changeDisplayType("Total Fail");
	    	WebElement chartTitle2 = nav.getChartTitle();
	    	assertNotEquals(ErrorMsg.ChartTitleIsSame+"2 \n", expected, chartTitle2.getText());
	    	nav.option.changeDisplayType("Pass/Fail");
	    	WebElement chartTitle3 = nav.getChartTitle();
	    	assertEquals(ErrorMsg.ChartTitleIsDifferent+"3 \n", expected, chartTitle3.getText());
	    }
	    
	    /**
	     * ID: REP-A.01.02
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that the "Choose data to display" function "Total Pass" displays the correct data and that the chart title is the same as the option
	     */
	    @Test
	    public void test_TotalPass(){
	    	nav.option.changeDisplayType("Total Pass");
	    	String expected = "Passed tests";
	    	WebElement chartTitle1 = nav.getChartTitle();
	    	assertEquals(ErrorMsg.ChartTitleIsDifferent + "1 \n", expected, chartTitle1.getText());
	    }
	    
	    /**
	     * ID: REP-A.01.03
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that the "Choose data to display" function "Total Failed" displays the correct data and that the chart title is the same as the option
	     */
	    @Test
	    public void test_TotalFailed(){
	    	nav.option.changeDisplayType("Total Fail");
	    	String expected = "Failed tests";
	    	WebElement chartTitle1 = nav.getChartTitle();
	    	assertEquals(ErrorMsg.ChartTitleIsDifferent + "1 \n", expected, chartTitle1.getText());
	    }
	    
	    /**
	     * ID: REP-A.01.04
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that the "Choose data to display" function "Run time" displays the correct data and that the chart title is the same as the option
	     */
	    @Test
	    public void test_RunTime(){
	    	nav.option.changeDisplayType("Run Time");
	    	String expected = "Time to run in seconds";
	    	WebElement chartTitle1 = nav.getChartTitle();
	    	assertEquals(ErrorMsg.ChartTitleIsDifferent + "1 \n", expected, chartTitle1.getText());
	    }
	    
	    /**
	     * ID: REP-A.01.05
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that the graph displays a line when "Series criteria" function is set to "None"
	     */
	    @Test
	    public void test_SeriesCriteria(){
	    	nav.option.changeBreakPoint("None");
	    	String actualName1 = nav.graph.getLegendListItem(0).getText();
	    	String expectedName1 = "MockedTestSuite";
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName1, actualName1);
	    	nav.option.reloadGraph();
	    	String expectedName2 = nav.graph.getProjectName(0).getText();
	    	String actualName2 = nav.graph.getLegendListItem(0).getText();
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "2 \n", expectedName2, actualName2);
	    	int expectedSize = 1;
	    	int actualSize = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "3 \n", expectedSize, actualSize);
	    }
	    
	    /**
	     * ID: REP-A.01.06
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that "Create a line for each" function displays a line for each browser
	     */
	    @Test
	    public void test_BreakOnBrowser(){
	    	nav.option.changeBreakPoint("Browser");
	    	nav.option.reloadGraph();
	    	String expectedName = "firefox v.31";
	    	String actualName = nav.graph.getLegendListItem(0).getText();
	    	int expectedSize = 2;
	    	int actualSize = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
    	}
	 	    
	    /**
	     * ID: REP-A.01.07
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that "Create a line for each" function displays a line for each version
	     */
        @Test
	    public void test_BreakOnVersion(){
	    	nav.option.changeBreakPoint("Version");
	    	nav.option.reloadGraph();
	    	String expectedName = "Android 4.4.4";
	    	String actualName = nav.graph.getLegendListItem(0).getText();
	    	int expectedSize = 4;
	    	int actualSize = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }
	    
        /**
	     * ID: REP-A.01.08
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that "Create a line for each" function displays a line for each device
	     */
        @Test
	    public void test_BreakOnDevice(){
	    	nav.option.changeBreakPoint("Device");
	    	nav.option.reloadGraph();
	    	String expectedName = "HTC ONE";
	    	String actualName = nav.graph.getLegendListItem(0).getText();
	    	int expectedSize = 4;
	    	int actualSize = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }
	    
        /**
	     * ID: REP-A.01.09
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that "Create a line for each" function displays a line for each platform
	     */
        @Test
	    public void test_BreakOnPlatform(){
        	nav.option.changeBreakPoint("Platform");
        	nav.option.reloadGraph();
        	String expectedName = "Android";
	    	String actualName = nav.graph.getLegendListItem(0).getText();
	    	int expectedSize = 3;
	    	int actualSize = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }
	    
        /**
	     * ID: REP-A.01.10
	     * <br> Edited: 2015-06-09
	     * <br> Purpose: Makes sure that "Choose a line to remove" function removes a line in the graph
	     */
        @Test
	    public void test_RemoveALine(){
        	nav.option.changeBreakPoint("Platform");
        	nav.option.reloadGraph();
        	nav.option.removeGraphLine("Android");
        	String expectedName1 = "IOS";
        	String actualName1 = nav.graph.getLegendListItem(0).getText();
        	String expectedName2 = "Ubuntu";
        	String actualName2 = nav.graph.getLegendListItem(1).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	int expectedSize = 2;
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName1, actualName1);
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "2 \n",expectedName2, actualName2);
        	assertEquals(ErrorMsg.LegendListSize + "3 \n",expectedSize, actualSize);	
	    }    
	    
        /**
	     * ID: REP-A.01.19
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per device is created for a selected device defined in specifications
	     */
        @Test
	    public void test_SpecificationsDevice_CheckDevice(){
        	nav.option.changeBreakPoint("Device");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.checkDevice("HTC ONE");
        	nav.option.reloadGraph();
        	String expectedName = "HTC ONE";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }

        /**
	     * ID: REP-A.01.20
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per device is created for a selected version defined in specifications
	     */
        @Test
	    public void test_SpecificationsDevice_CheckVersion(){
        	nav.option.changeBreakPoint("Device");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.checkVersion("4.4.4");
        	nav.option.reloadGraph();
        	String expectedName = "HTC ONE";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }

        /**
	     * ID: REP-A.01.21
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per device is created for a selected platform defined in specifications
	     */
        @Test
        public void test_SpecificationsDevice_CheckPlatform(){
        	nav.option.changeBreakPoint("Device");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.reloadGraph();
        	String expectedName1 = "HTC ONE";
        	String expectedName2 = "Nexus 6";
        	int expectedSize = 2;
        	String actualName1 = nav.graph.getLegendListItem(0).getText();
        	String actualName2 = nav.graph.getLegendListItem(1).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName1, actualName1);
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "2 \n",expectedName2, actualName2);
        	assertEquals(ErrorMsg.LegendListSize + "3 \n",expectedSize, actualSize);	
        	
        }

        /**
	     * ID: REP-A.01.22
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line is created per version defined by a device selected in specifications
	     */
        @Test
	    public void test_Specifications_CheckDeviceGetVersion(){
        	nav.option.changeBreakPoint("Version");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.checkDevice("HTC ONE");
        	nav.option.reloadGraph();
        	String expectedName = "Android 4.4.4";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }

        /**
	     * ID: REP-A.01.22
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per version is created per version defined by a device selected in specifications
	     */
        @Test
	    public void test_Specifications_Version(){
        	nav.option.changeBreakPoint("Version");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.checkVersion("4.4.4");
        	nav.option.reloadGraph();
        	String expectedName = "Android 4.4.4";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent  + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }

        /**
	     * ID: REP-A.01.24
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per version is created per version defined by a platform selected in specifications
	     */
        @Test
  	    public void test_Specifications_VersionPlatform(){
          	nav.option.changeBreakPoint("Version");
          	nav.option.clickSpecifications();
          	nav.option.clickPlatform("Android");
          	nav.option.reloadGraph();
          	String expectedName1 = "Android 4.4.4";
        	String expectedName2 = "Android 5.1";
        	int expectedSize = 2;
        	String actualName1 = nav.graph.getLegendListItem(0).getText();
        	String actualName2 = nav.graph.getLegendListItem(1).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName1, actualName1);
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "2 \n",expectedName2, actualName2);
        	assertEquals(ErrorMsg.LegendListSize + "3 \n",expectedSize, actualSize);	         	
  	    }

        /**
	     * ID: REP-A.01.25
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per platform is created per device defined by a device selected in specifications
	     */       
        @Test
	    public void test_Specifications_CheckDeviceGetPlatform(){
        	nav.option.changeBreakPoint("Platform");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.checkDevice("HTC ONE");
        	nav.option.reloadGraph();
         	String expectedName = "Android";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }

        /**
	     * ID: REP-A.01.26
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per platform is created per platform defined by a platform selected in specifications
	     */  
        @Test
	    public void test_Specifications_Platform(){
        	nav.option.changeBreakPoint("Platform");
        	nav.option.clickSpecifications();
        	nav.option.clickPlatform("Android");
        	nav.option.reloadGraph();
         	String expectedName = "Android";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
	    }
        
        /**
	     * ID: REP-A.01.27
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per platform is created per platform defined by a version selected in specifications
	     */ 
        @Test
 	    public void test_Specifications_PlatformVersion(){
         	nav.option.changeBreakPoint("Platform");
         	nav.option.clickSpecifications();
         	nav.option.clickPlatform("Android");
         	nav.option.checkVersion("4.4.4");
         	nav.option.reloadGraph();
         	String expectedName = "Android";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
        /**
	     * ID: REP-A.01.28
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per platform is created per platform defined by multiple platforms selected in specifications
	     */ 
        @Test
 	    public void test_SpecificationsPlatform_CheckAndroidIOS(){
         	nav.option.changeBreakPoint("Platform");
         	nav.option.clickSpecifications();
         	nav.option.clickPlatform("Android");
         	nav.option.clickPlatform("IOS");
         	nav.option.reloadGraph();
         	String expectedName1 = "Android";
         	String expectedName2 = "IOS";
        	int expectedSize = 2;
        	nav.graph.waitForLegendListSize(expectedSize);
        	String actualName1 = nav.graph.getLegendListItem(0).getText();
        	String actualName2 = nav.graph.getLegendListItem(1).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName1, actualName1);
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "2 \n",expectedName2, actualName2);
        	assertEquals(ErrorMsg.LegendListSize + "3 \n",expectedSize, actualSize);	
        }
        
        /**
	     * ID: REP-A.01.29
	     * <br> Edited: 2015-06-10
	     * <br> Purpose: Makes sure that a line per browser is created per browser defined by a browser selected in specifications
	     */ 
        @Test
        public void test_SpecificationsBrowser_CheckChrome(){
        	nav.option.changeBreakPoint("Browser");
        	nav.option.clickSpecifications();
        	nav.option.checkBrowser("chrome");
        	nav.option.reloadGraph();
        	String expectedName = "chrome v.42";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
	    	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
//      Ignored for time being because it does the same thing as ID: VIS-A.01.02, waiting for more fluctuent test data
        
        /**
         * ID: REP-A.01.34
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure you are able to change timestamp
         */
		
        @Test
        public void test_ChooseTimeStampFromDropDrown() {
        	nav.chooseProject(0);
        	nav.chooseTimestampFromDropdown("20150210080040");
        	WebElement currentTimeStamp = nav.getElementByID("choose_timestamp");
        	assertEquals("2015-02-10 08:00", currentTimeStamp.getText());
        }
        
        /**
         * ID: REP-A.01.31
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the "Clear all checkboxes" button works as intended after a browser has been selected in the specifications menu
         */
	    @Test
	    public void test_ClearCheckBoxesBrowser(){
	    	nav.option.changeBreakPoint("Browser");
	    	nav.option.clickSpecifications();
	    	nav.option.checkBrowser("chrome");
	    	nav.option.clickClearCheckBoxes();
	    	nav.option.reloadGraph();
	    	int expected = 2;
	    	int actual = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.32
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the "Clear all checkboxes" button works as intended after a platform has been selected in the specifications menu
         */
	    @Test
	    public void test_ClearCheckBoxesPlatform(){
	    	nav.option.changeBreakPoint("Platform");
	    	nav.option.clickSpecifications();
	    	nav.option.clickPlatform("Android");
	    	nav.option.clickClearCheckBoxes();
	    	nav.option.reloadGraph();
	    	int expected = 3;
	    	int actual = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.38
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the "Clear all checkboxes" button works as intended after a version has been selected in the specifications menu
         */
	    @Test
	    public void test_ClearCheckBoxesVersion(){
	    	nav.option.changeBreakPoint("Version");
	    	nav.option.clickSpecifications();
	    	nav.option.clickPlatform("Android");
	    	nav.option.checkVersion("4.4.4");
	    	nav.option.clickClearCheckBoxes();
	    	nav.option.reloadGraph();
	    	int expected = 4;
	    	int actual = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.33
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the "Clear all checkboxes" button works as intended after a device has been selected in the specifications menu
         */
	    @Test
	    public void test_ClearCheckBoxesDevice(){
	    	nav.option.changeBreakPoint("Device");
	    	nav.option.clickSpecifications();
	    	nav.option.clickPlatform("Android");
	    	nav.option.checkDevice("HTC ONE");
	    	nav.option.clickClearCheckBoxes();
	    	nav.option.reloadGraph();
	    	int expected = 4;
	    	int actual = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n",expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.11
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the "Choose amount to load" option works as intended and that the chart sub title is changed
         */
	    @Test
	    public void test_changeRunLimit_10(){
	    	nav.chooseProject(0);
	    	nav.option.changeChartSuiteRunLimit("10");
	    	nav.option.reloadGraph();
	    	String expected = "Showing 10 results";
	    	String actual = nav.option.checkShowingNumberResults();
	    	assertEquals(ErrorMsg.HighchartsSubTitle + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.12
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the "Choose amount to load" option works as intended and that the chart sub title is changed
         */
	    @Test
	    public void test_changeRunLimit_20(){
	    	nav.chooseProject(0);
	    	nav.option.changeChartSuiteRunLimit("20");
	    	nav.option.reloadGraph();
	    	String expected = "Showing 20 results";
	    	String actual = nav.option.checkShowingNumberResults();
	    	assertEquals(ErrorMsg.HighchartsSubTitle + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.13
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the "Choose amount to load" option works as intended and that the chart sub title is changed
         */
	    @Test
	    public void test_changeRunLimit_50(){
	    	nav.chooseProject(0);
	    	nav.option.changeChartSuiteRunLimit("50");
	    	nav.option.reloadGraph();
	    	String expected = "Showing 50 results";
	    	String actual = nav.option.checkShowingNumberResults();
	    	assertEquals(ErrorMsg.HighchartsSubTitle + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.14
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the "Choose amount to load" option works as intended and that the chart sub title is changed
         */
	    @Test
	    public void test_changeRunLimit_100(){
	    	nav.chooseProject(0);
	    	nav.option.changeChartSuiteRunLimit("100");
	    	nav.option.reloadGraph();
	    	String expected = "Showing 50 results";
	    	String actual = nav.option.checkShowingNumberResults();
	    	assertEquals(ErrorMsg.HighchartsSubTitle + "1 \n", expected, actual);
	    }
	    
	    /**
	     * ID: REP-A.01.15
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the "Choose amount to load" option works as intended and that the chart sub title is changed
         */
	    @Test
	    public void test_changeRunLimit_500(){
	    	nav.chooseProject(0);
	    	new RMReportScreenshot(urlContainer).takeScreenshot("BeforeSearch");
	    	nav.option.changeChartSuiteRunLimit("500");
	    	nav.option.reloadGraph();
//	    	nav.option.checkShowingNumberResults();
	    	String expected = "Showing 50 results";
	    	String actual = nav.option.checkShowingNumberResults();
	    	new RMReportScreenshot(urlContainer).takeScreenshot("AfterSearch");
	    	assertEquals(ErrorMsg.HighchartsSubTitle + "1 \n", expected, actual);
	    }

	    /**
	     * ID: REP-A.01.16
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the down button in the legend list works as intended when the amount of entries does not fit in the list
         */
	    @Test
	    public void test_clickLegendListDownButton(){
	    	for (int i = 0; i < 19; i++) {
	    		nav.option.addToGraph();
			}
	    	String before = nav.graph.getListNumber();
	    	nav.graph.clickDownArrow();
	    	String after = nav.graph.getListNumber();
	    	assertNotEquals(ErrorMsg.LegendListDown + "1 \n", before, after);
	    }

	    /**
	     * ID: REP-A.01.17
         * <br> Edited: 2015-06-10
         * <br> Purpose: Makes sure that the up button in the legend list works as intended when the amount of entries does not fit in the list
         */
	    @Test
	    public void test_clickLegendListUpButton(){
	    	for (int i = 0; i < 19; i++) {
	    		nav.option.addToGraph();
			}
	    	nav.graph.clickDownArrow();
	    	String before = nav.graph.getListNumber();
	    	nav.graph.clickUpArrow();
	    	String after = nav.graph.getListNumber();
	    	assertNotEquals(ErrorMsg.LegendListUp + "1 \n", before, after);
	    }
	    
	    /**
	     * ID: REP-A.01.35
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the "Add choices to graph" button works as intended
         */
	    @Test
	    public void test_AddChoicesToGraph(){
	    	nav.option.addToGraph();
	    	int expected = 2;
	    	int actual = nav.graph.getLegendList().size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n",expected, actual);
	    }

	    /**
	     * ID: REP-A.01.36
         * <br> Edited: 2015-06-15
         * <br> Purpose: Makes sure that each column has a different color in the graph when "Choose data to display" is set to "Total Pass"
         */
	    @Test
	    public void test_totalPassDifferentColors(){
	    	nav.option.changeGraphType("Column");
	    	nav.option.changeBreakPoint("Browser");
	    	nav.option.addToGraph();
	    	nav.option.changeDisplayType("Total Pass");
	    	List<String> colors = nav.graph.getLegendListColors();
	    	System.out.println(colors.toString());
	    	int expectedsize = 3;
	    	int actualsize = colors.size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n", expectedsize, actualsize);
	    	HashSet<String> colorset = new HashSet<String>(colors);
	    	assertEquals(ErrorMsg.HashSetSizeIsDifferent + "2 \n", expectedsize,colorset.size());
	    }

	    /**
	     * ID: REP-A.01.37
         * <br> Edited: 2015-06-15
         * <br> Purpose: Makes sure that each column has a different color in the graph when "Choose data to display" is set to "Total Fail"
         */
	    @Test
	    public void test_totalFailDifferentColors(){
	    	nav.option.changeGraphType("Column");
	    	nav.option.changeBreakPoint("Platform");
	    	nav.option.addToGraph();
	    	nav.option.changeDisplayType("Total Fail");
	    	List<String> colors = nav.graph.getLegendListColors();
	    	System.out.println(colors.toString());
	    	int expectedsize = 4;
	    	int actualsize = colors.size();
	    	assertEquals(ErrorMsg.LegendListSize + "1 \n", expectedsize, actualsize);
	    	HashSet<String> colorset = new HashSet<String>(colors);
	    	assertEquals(ErrorMsg.HashSetSizeIsDifferent + "2 \n", expectedsize,colorset.size());
	    	
	    }
}