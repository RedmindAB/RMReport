package test.java.se.redmind.suite;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.BeforeClass;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.main.Main;
import se.redmind.rmtest.report.auto.tests.DashPage;
import se.redmind.rmtest.report.auto.tests.GraphPage;
import se.redmind.rmtest.report.auto.tests.GridPage;
import se.redmind.rmtest.report.auto.tests.NavbarPage;
import se.redmind.rmtest.report.auto.tests.StartPage;
import se.redmind.rmtest.report.auto.tests.SuitePage;
import se.redmind.rmtest.report.auto.tests.VisualPage;
import se.redmind.rmtest.selenium.framework.RmSuite;
import se.redmind.rmtest.selenium.grid.DriverProvider;
import se.redmind.rmtest.web.properties.ConfigDAO;
import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;




@RunWith(RmSuite.class)
@Suite.SuiteClasses( {GraphPage.class, SuitePage.class, VisualPage.class, NavbarPage.class, StartPage.class, GridPage.class, DashPage.class})
public class RMReportAutoSuite extends WSSetupHelper {
	
	private static Thread t;
	private static boolean started = false;
	
    public static synchronized void startWebServer(){
    	if(!started){
    		t = new Thread(new Runnable() {
    			@Override
    			public void run() {
    				try {
    					beforeAutoTests();
    					String[] args = {"autotest"};
    					Main.main(args);
    				} catch (ClassNotFoundException e) {
    					e.printStackTrace();
    				} catch (SQLException e) {
    					e.printStackTrace();
    				}
    			}
    		});
    		t.start();
    		started = true;
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    
    @AfterClass
    public static void afterAllTests(){
        DriverProvider.stopDrivers();
        t.stop();
    }
    
}
