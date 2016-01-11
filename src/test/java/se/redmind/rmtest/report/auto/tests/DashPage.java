package se.redmind.rmtest.report.auto.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import se.redmind.report.auto.nav.DashNav;
import se.redmind.rmtest.report.auto.utils.ErrorMsg;
import se.redmind.rmtest.selenium.framework.RMReportScreenshot;
import se.redmind.rmtest.selenium.grid.DriverNamingWrapper;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.selenium.grid.Parallelized;
import test.java.se.redmind.suite.RMReportAutoSuite;



@RunWith(Parallelized.class)
public class DashPage {


	   private WebDriver tDriver;
	    private final DriverNamingWrapper urlContainer;
	    private final String driverDescription;
	    private final RMReportScreenshot rmrScreenshot;
	    private DashNav nav;

	    public DashPage(final DriverNamingWrapper driverWrapper, final String driverDescription) {
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
	    	this.nav = new DashNav(tDriver);
	    }
	    
	    /**
	     * ID: DASH-A.01.01
         * <br> Edited: 2015-06-11
         * <br> Purpose: Makes sure that only the selected tests defined by passed/skipped/failed are displayed in the graph
         */
	    @Test
	    public void test_filterOnGraph(){
	    	
	    	assertTrue(ErrorMsg.ShouldBeEnabled + "1 \n", nav.dash.isEnabled("passed"));
	    	nav.dash.clickLegend("passed");
	    	assertTrue(ErrorMsg.ShouldNotBeEnabled + "2 \n", !nav.dash.isEnabled("passed"));
	    	
	    	assertTrue(ErrorMsg.ShouldBeEnabled + "3 \n", nav.dash.isEnabled("skipped"));
	    	nav.dash.clickLegend("skipped");
	    	assertTrue(ErrorMsg.ShouldNotBeEnabled + "4 \n", !nav.dash.isEnabled("skipped"));
	    	
	    	assertTrue(ErrorMsg.ShouldBeEnabled + "5 \n", nav.dash.isEnabled("failed"));
	    	nav.dash.clickLegend("failed");
	    	assertTrue(ErrorMsg.ShouldNotBeEnabled + "6 \n", !nav.dash.isEnabled("failed"));
	    }
	    
	    /**
	     * ID: DASH-A-01-02
	     * <br> Edited: 2015-06-22
	     * <br> Purpose: Makes sure that if you load another project the new project is displayed on the Dashboard page and that you are not redirected
	     */
	    @Test
	    public void test_loadAnotherProject(){
	    	String nameBefore = nav.dash.getProjectName();
	    	nav.chooseProject(2);
	    	String nameAfter = nav.dash.getProjectName();
	    	assertNotEquals(ErrorMsg.ChartTitleIsSame + "1 \n",nameBefore, nameAfter);
	    	assertTrue(ErrorMsg.PageRedirect + "2 \n", nav.dash.getUrl().endsWith("/#/dashboard"));
	    }
	    
	    /**
	     * ID: DASH-A-01-03
	     * <br> Edited: 2015-06-22
	     * <br> Purpose: Makes sure that you are redirected to reports when you click on a column in the graph
	     */
	    @Test
	    public void test_clickOnGraphColumn(){
	    	String nameBefore = nav.dash.getProjectName();
//	    	System.out.println(nav.dash.getProjectName());
	    	nav.getFirstSuiteSection();
	    	String nameAfter = nav.dash.getProjectNameInReports();
//	    	System.out.println(nav.dash.getProjectNameInReports());
	    	assertEquals(ErrorMsg.ProjectNameIsDifferent + "1 \n",nameBefore, nameAfter);
//	    	project name in reports page has a white space after it, thus: + " ".
	    	assertTrue(ErrorMsg.PageRedirect + "2 \n",nav.dash.getUrl().endsWith("/#/reports/classes"));
	    }
	    
}