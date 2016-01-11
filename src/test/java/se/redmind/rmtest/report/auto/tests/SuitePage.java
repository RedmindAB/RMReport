package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
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
public class SuitePage {


	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private GraphNav nav;

	    public SuitePage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
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
	     * ID: REP-A.02.20
	     * <br> Edited: 2015-06-26
	     * <br> Purpose: To verify function for order test cases by what platform they are run on.
	     */
	    @Test
	    public void test_OrderCasesByPlatform(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	String expectedBefore = "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,";
	    	String actualBefore = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "1\n", expectedBefore, actualBefore);
	    	
	    	nav.suite.clickBarsOrderByPlatform();
	    	String after1 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "2\n", actualBefore, after1);
	    	
	    	nav.suite.clickBarsOrderByPlatform();
	    	String after2 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "3\n",after1, after2);
	    	
	    	String expected2 = "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,";
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "4\n",expected2, after2);
	    	
	    	nav.suite.clickBarsOrderByPlatform();
	    	String after3 = nav.suite.getAllCaseNames() + "";
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "5\n",after1, after3);
	    }
	    
	    /**
	     * ID: REP-A.02.21
	     * <br> Edited: 2015-06-26
	     * <br> Purpose: To verify function for order test cases by what device they are run on.
	     */
	    @Test
	    public void test_OrderCasesByDevice(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	String expectedBefore = "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,";
	    	String actualBefore = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "1\n", expectedBefore, actualBefore);
	    	
	    	nav.suite.clickBarsOrderByDevice();
	    	String after1 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "2\n", actualBefore, after1);
	    	
	    	nav.suite.clickBarsOrderByDevice();
	    	String after2 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "3\n", after1, after2);
	    	
//	    	System.out.println(nav.suite.getAllCaseNames());
	    	String expected2 = "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,";
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "4\n", expected2, after2);
	    	
	    	nav.suite.clickBarsOrderByDevice();
	    	String after3 = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "5\n", after1, after3);
	    }
	    
	    /**
	     * ID: REP-A.02.22
	     * <br> Edited: 2015-06-26
	     * <br> Purpose: To verify function for order test cases by what browser they are run on.
	     */
	    @Test
	    public void test_OrderCasesByBrowser(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	String expectedBefore = "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,";
	    	String actualBefore = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "1\n", expectedBefore, actualBefore);
	    	
	    	nav.suite.clickBarsOrderByBrowser();
	    	String after1 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "2\n", actualBefore, after1);
	    	
	    	nav.suite.clickBarsOrderByBrowser();
	    	String after2 = nav.suite.getAllCaseNames();
	    	assertNotEquals(ErrorMsg.CaseBarNameIsSame + "3\n", after1, after2);
	    	
	    	System.out.println(after2);
	    	String expected2 = "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,";
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "4\n", expected2, after2);
	    	
	    	nav.suite.clickBarsOrderByBrowser();
	    	String after3 = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "5\n", after1, after3);
	    }
	    
	    /** 
	     * ID: REP-A.02.02
	     * <br> Edited: 2015-06-26
	     * <br> Purpose: To verify function for order test cases by what runtime they had.
	     */
	    @Test
	    public void test_OrderCasesByRuntime(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	String expectedBefore = "3.0,3.0,3.0,3.0,1.0,1.0,1.0,1.0,2.5,2.5,2.5,2.5,2.0,2.0,2.0,2.0,";
	    	String actualBefore = nav.suite.getAllCaseRuntimes();
	    	assertEquals(ErrorMsg.RuntimeIsDifferent + "1\n", expectedBefore, actualBefore);
	    	
	    	nav.suite.clickBarsOrderByRuntime();
	    	String after1 = nav.suite.getAllCaseRuntimes();
	    	assertNotEquals(ErrorMsg.RuntimeIsSame + "2\n", actualBefore, after1);
	    	
	    	nav.suite.clickBarsOrderByRuntime();
	    	String after2 = nav.suite.getAllCaseRuntimes();
	    	assertNotEquals(ErrorMsg.RuntimeIsSame + "3\n", after1, after2);
	    	
//	    	System.out.println(nav.suite.getAllCaseRuntimes());
	    	String expected2 = "1.0,1.0,1.0,1.0,2.0,2.0,2.0,2.0,2.5,2.5,2.5,2.5,3.0,3.0,3.0,3.0,";
	    	assertEquals(ErrorMsg.RuntimeIsSame + "4\n", expected2, after2);
	    	
	    	nav.suite.clickBarsOrderByRuntime();
	    	String after3 = nav.suite.getAllCaseRuntimes();
	    	assertEquals(ErrorMsg.RuntimeIsDifferent + "5\n", after1, after3);
	    }
	    
	    /**
	     * ID: REP-A.02.23
	     * <br> Edited: 2015-06-26
	     * <br> Purpose: To verify function for order test cases by what status they received after test run.
	     */
	    @Test
	    public void test_OrderCasesByPassFail(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	String expectedBefore = "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,";
	    	String actualBefore = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "1\n", expectedBefore, actualBefore);
	    	
	    	nav.suite.clickBarsOrderByPassFail();
	    	String actualAfter = nav.suite.getAllCaseNames();
	    	String expectedAfter = "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - firefox - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "Android - 5.1 - Nexus 6 - chrome - passed,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - firefox - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "IOS - 8.1 - iPhone 6 - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - firefox - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Android - 4.4.4 - HTC ONE - chrome - failure,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - firefox - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,"
	    			+ "Ubuntu - 14.04 - UNKNOWN - chrome - error,";
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "2\n", actualAfter, expectedAfter);
	    	
	    	nav.suite.clickBarsOrderByPassFail();
	    	String actualAfter2 = nav.suite.getAllCaseNames();
	    	assertEquals(ErrorMsg.CaseBarNameIsDifferent + "3\n", actualBefore, actualAfter2);
	    }
	    
	    /**
	     * ID: REP-A.02.17
	     * <br> Edited: 2015-06-25
	     * <br> Purpose: To verify function for order test methods by their name.
	     * Takes screenshots
	     */
        @Test
        public void test_OrderMethodsByName(){
        	nav.suite.clickOnBar("class", "0");
        	String expectedBefore1 = "random1,random3,random2,random4,";
        	String actualBefore1 = nav.suite.getAllBarNames("method");
//        	System.out.println("All bar names before rearrange1: \n" + actualBefore1);
        	assertEquals(ErrorMsg.MethodBarNameIsDifferent + "1 \n",expectedBefore1, actualBefore1);
        	new RMReportScreenshot(urlContainer).takeScreenshot("Before");
        	
        	nav.suite.clickBarsOrderByName();
        	String after1 = nav.suite.getAllBarNames("method");
//        	System.out.println("All bar names after rearrange1: \n" + after1);
        	assertNotEquals(ErrorMsg.MethodBarNameIsSame + "2 \n",actualBefore1, after1);
        	new RMReportScreenshot(urlContainer).takeScreenshot("After1");
        	
        	nav.suite.clickBarsOrderByName();
        	String after2 = nav.suite.getAllBarNames("method");
//        	System.out.println("All bar names after rearrange2: \n" + after2);
        	assertNotEquals(ErrorMsg.MethodBarNameIsSame + "3 \n",after1, after2);
        	
        	String expected2 = "random4,random3,random2,random1,";
        	assertEquals(ErrorMsg.MethodBarNameIsDifferent + "4 \n", expected2, after2);
        	
        	nav.suite.clickBarsOrderByName();
        	String after3 = nav.suite.getAllBarNames("method");
//        	System.out.println("All bar names after rearrange3: \n" + after3);
        	assertEquals(ErrorMsg.MethodBarNameIsDifferent + "5 \n",after1, after3);
        }
        
        /**
         * ID: REP-A.02.18
	     * <br> Edited: 2015-06-25
	     * <br> Purpose: To verify function for ordering test methods by passed ones
	     */
        @Test
        public void test_OrderMethodsByPassed(){
        	nav.suite.clickOnBar("class", "0");
        	String expectedBefore1 = "4,8,12,12,";
        	String actualBefore1 = nav.suite.getAllBarStats("method", 1);
        	System.out.println("Passed order before rearrange: \n" + actualBefore1);
        	assertEquals(ErrorMsg.MethodBarTextIsDifferent + "1 \n" ,expectedBefore1, actualBefore1);
        	
        	nav.suite.clickBarsOrderByPassed();
        	nav.suite.clickBarsOrderByPassed();
        	String after1 = nav.suite.getAllBarStats("method", 1);
        	System.out.println("Passed order after rearrange: \n" + after1);
        	assertNotEquals(ErrorMsg.MethodBarTextIsSame + "2 \n", actualBefore1, after1);
        	
        	nav.suite.clickBarsOrderByPassed();
        	String after2 = nav.suite.getAllBarStats("method", 1);
        	System.out.println("Passed order after rearrange2: \n" + after2);
        	assertNotEquals(ErrorMsg.MethodBarTextIsSame + "3 \n", after1, after2);
        	
        	String expected2 = "4,8,12,12,";
        	assertEquals(ErrorMsg.MethodBarTextIsDifferent + "4 \n" ,expected2, after2);
        }
        
        /** 
         * ID: REP-A.02.19
	     * <br> Edited: 2015-06-25
	     * <br> Purpose: To verify function for ordering test methods by failed ones
	     */
        @Test
        public void test_OrderMethodsByFailed(){
        	nav.suite.clickOnBar("class", "0");
        	String expectedBefore1 = "12,8,4,4,";
        	String actualBefore1 = nav.suite.getAllBarStats("method", 3);
        	System.out.println(actualBefore1);
        	System.out.println("Failed order before rearrange: \n" + actualBefore1);
        	assertEquals(ErrorMsg.MethodBarTextIsDifferent + "1 \n" ,expectedBefore1, actualBefore1);
        	
        	nav.suite.clickBarsOrderByFailed();
        	String after1 = nav.suite.getAllBarStats("method", 3);
        	System.out.println("Failed order after rearrange1: \n" + after1);
        	assertNotEquals(ErrorMsg.MethodBarTextIsSame + "2 \n", actualBefore1, after1);
        	
        	nav.suite.clickBarsOrderByFailed();
        	String after2 = nav.suite.getAllBarStats("method", 3);
        	System.out.println("Failed order after rearrange2: \n" + after2);
        	assertNotEquals(ErrorMsg.MethodBarTextIsSame + "3 \n", after1, after2);
        	
        	String expected2 = "12,8,4,4,";
        	assertEquals(ErrorMsg.MethodBarTextIsDifferent + "4 \n" ,expected2, after2);
        }
        
        /** 
         * ID:REP-A.02.16
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for navigating to test methods
	     */
        @Test
        public void test_GoToMethods(){
        	String className  = nav.suite.getNameFrom("class", "0");
        	nav.suite.clickOnBar("class", "0");
        	assertTrue(ErrorMsg.ClassNameIsDifferent + "1 \n", className.endsWith(nav.suite.getCurrentPossition().getText()));
        }
        
        /**
         * ID:REP-A.02.15
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for navigating to test cases.
	     */
        @Test
        public void test_GoToCases(){
        	nav.suite.clickOnBar("class", "0");
        	String methodName = nav.suite.getNameFrom("method", "0");
        	nav.suite.clickOnBar("method", "0");
        	assertEquals(ErrorMsg.MethodNameIsDifferent + "1 \n", methodName, nav.suite.getCurrentPossition().getText());
        	nav.suite.getAllCaseNames();
        }
        
        /** 
         * ID:REP-A.02.04
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for clicking on a specific test.
	     */
        @Test
        public void test_ClickOnThisTestOnly(){
        	nav.suite.clickOnBar("class", "0");
        	nav.suite.clickOnBar("method", "0");
        	//System.out.println(nav.suite.getNameFrom("case", "0"));
        	nav.suite.clickThisTestOnly("0");
        	String expectedName = "Ubuntu-14.04-UNKNOWN-chrome";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n",expectedName, actualName);
        	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
        /**
         * ID:REP-A.02.14
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for fetching error stack trace. 
	     */
        @Test
        public void test_getErrorCase(){
        	nav.suite.clickOnBar("class", "0");
        	nav.suite.clickOnBar("method", "0");
        	nav.suite.clickOnBar("case", "0");
        	String stackTrace = nav.suite.getStackTrace();
        	int	colon = stackTrace.indexOf(":");
        	stackTrace = stackTrace.substring(0, colon);
        	String expected = " java.lang.AssertionError";
        	assertEquals(ErrorMsg.StackTraceIsDifferent + "1 \n", expected, stackTrace);
        }
        
        /** 
         * ID:REP-A.02.13
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for clicking on last stack trace.
	     */
        @Test
        public void test_ClickOnLastCase(){
        	nav.suite.clickOnBar("class", "0");
        	nav.suite.clickOnBar("method", "0");
        	nav.suite.clickOnBar("case", "15");
        	String stackTrace = nav.suite.getStackTrace();
        	String expected = "This test passed";
        	assertEquals(ErrorMsg.StackTraceIsDifferent + "1 \n", expected, stackTrace);
        }
        
        /**
         * ID:REP-A.02.07
	     * <br> Edited: 2015-04-21
	     * <br> Purpose: To verify function for checking a class.
	     */
        @Test 
        public void test_CheckClass(){
        	nav.suite.checkBoxOn("class", "0");
        	nav.option.reloadGraph();
        	String expectedName = "MockedTestSuite";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
        	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
        /**
         * ID:REP-A.02.03
	     * <br> Edited: 2015-04-21
	     * <br> Purpose: To verify function for checking a method..
	     */
        @Test 
        public void test_CheckMethod(){
        	nav.suite.clickOnBar("class", "0");
        	nav.suite.checkBoxOn("method", "0");
        	String beforeReload = nav.graph.getLegendListItem(0).getText();
        	int expectedSize = 1;
        	nav.option.reloadGraph();
        	String afterReload = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertNotEquals(ErrorMsg.LegendListTextIsSame + "1 \n",beforeReload, afterReload);
        	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
        /**
         * ID:REP-A.02.10
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for reloading a class.
	     */
        @Test 
        public void test_ClickOnClassReload(){
        	nav.suite.clickOnBar("class", "0");
        	nav.option.reloadGraph();
        	String expectedName = "RandomClass0";
        	int expectedSize = 1;
        	String actualName = nav.graph.getLegendListItem(0).getText();
        	int actualSize = nav.graph.getLegendList().size();
        	assertEquals(ErrorMsg.LegendListTextIsDifferent + "1 \n", expectedName, actualName);
        	assertEquals(ErrorMsg.LegendListSize + "2 \n", expectedSize, actualSize);
        }
        
        /**
         * ID:REP-A.02.11
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for cleaning up search filters.
	     */
        @Test
        public void test_FilterCleanUp(){
        	nav.suite.filterOn("random");
        	nav.suite.clickOnBar("class", "0");
        	String expected = "";
        	String actual = nav.suite.getFilterFieldText();
        	assertEquals(ErrorMsg.FilterFieldTextIsDifferent + "1 \n", expected, actual);
        }
        
        /**
         * ID:REP-A.02.12
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for filtering on Chrome.
	     */
        @Test
        public void test_FilterOnChrome(){
        	nav.suite.clickOnBar("class", "0");
        	nav.suite.clickOnBar("method", "0");
        	nav.suite.filterOn("chrome");
        	int expected = 8;
        	int actual = nav.suite.getCaseList().size();
        	nav.suite.waitForCaseListSize(expected);
        	assertEquals(ErrorMsg.CaseListSizeIsDifferent + "1 \n", expected, actual);
        }
        
        /**
         * ID:REP-A.02.08
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for clicking on class linktext.
	     */
	    @Test
	    public void test_ClickOnClassLink(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.ClickOnSuiteLinkText();
	    	String actual = nav.graph.getUrl();
	    	assertTrue(ErrorMsg.PageRedirect + "1 \n", actual.endsWith("/#/reports/classes"));
	    }
	    
	    /**
	     * ID:REP-A.02.09
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for clicking on method link text.
	     */
	    @Test
	    public void test_clickOnMethodLink(){
	    	nav.suite.clickOnBar("class", "0");
	    	nav.suite.clickOnBar("method", "0");
	    	nav.suite.ClickOnSuiteLinkText();
	    	String actual = nav.graph.getUrl();
	    	assertTrue(ErrorMsg.PageRedirect + "1 \n", actual.endsWith("/#/reports/methods"));
	    }
	    
	    /**
	     * ID:REP-A.02.05
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for getting passed, failed and skipped classes.
	     */
	    @Test
	    public void test_getPassFailTextClass(){
	    	String expectedTotal = "64";
	    	String expectedPassed = "36";
	    	String expectedSkipped = "0";
	    	String expectedFailed = "28";
	    	String expectedAll = expectedTotal + expectedPassed + expectedSkipped + expectedFailed;
	    	String actualAll = nav.suite.getPassedSkippedFailed("class", "1");
	    	assertEquals(ErrorMsg.ClassBarTextIsDifferent + "1 \n", expectedAll, actualAll);
	    }
	    
	    /**
	     * ID:REP-A.02.06
	     * <br> Edited: 2015-06-11
	     * <br> Purpose: To verify function for getting passed, failed and skipped methods.
	     */
	    @Test
	    public void test_getPassFailTextMethod(){
	    	nav.suite.clickOnBar("class", "0");
	    	String expectedTotal = "16";
	    	String expectedPassed = "4";
	    	String expectedSkipped = "0";
	    	String expectedFailed = "12";
	    	String expectedAll = expectedTotal + expectedPassed + expectedSkipped + expectedFailed;
	    	String actualAll = nav.suite.getPassedSkippedFailed("method", "0");
	    	System.out.println(actualAll);
	    	assertEquals(ErrorMsg.MethodBarTextIsDifferent + "1 \n", expectedAll, actualAll);
	    }
	    
	    /**
         * ID: REP-A.02.24
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that the projects name in the suite part redirects back to the start/home page
         */
        @Test
        public void test_ClickOnSuiteLink(){
        	nav.suite.ClickOnSuiteLinkText();
        	String actual = nav.graph.getUrl();
        	assertTrue(ErrorMsg.PageRedirect + "1 \n", actual.endsWith("/#/home"));
        }
	     
}
