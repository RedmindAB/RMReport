package se.redmind.rmtest.db.test;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.rules.Timeout;

import com.google.gson.JsonArray;
import com.google.gson.internal.bind.JsonTreeWriter;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.read.ReadClassFromDB;
import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.read.ReadSuiteFromDB;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;
import se.redmind.rmtest.web.route.api.classes.getclasses.GetClassesDAO;
import se.redmind.rmtest.web.route.api.device.getdevices.GetDevicesAMonthAgoDAO;
import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseDAO;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteDAO;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampDAO;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by johan on 15-01-27.
 */
public class ReadFromDBTest {
    Connection conn = DBCon.getDbInstance().getConnection();
    
    //@Rule
    //public Timeout timeout = new Timeout(500);

    @Test
    public void readFromTestcases(){
        Statement stat = null;

        try {
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from testcase;");
            while(rs.next()){
            	
                System.out.println("testcase name = " + rs.getString("testcasename"));
                System.out.println("testcase id = " + rs.getString("testcase_id"));

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getClassIDTest(){new ReadClassFromDB().getClassID("name");}

    @Test
    public void getSuitIdTest(){
    new ReadSuiteFromDB().getSuiteID("name");
    }
    
    @Test
    public void getAllSuitesTest() {
        new GetSuitesDAO().getAllSuites();
    }
    @Test
    public void getClassNameOnTestcaseIdTest(){ 
    	HashMap<String, String> classNameOnTestcaseId = new ReadClassFromDB().getClassNameOnTestcaseId();
    	Set<String> keySet = classNameOnTestcaseId.keySet();
    	for (String string : keySet) {
			System.out.println("Key: "+string);
		}
    }
    
    @Test
    public void getSuiteList(){
    	List<HashMap<String, Object>> allSuites = new GetSuitesDAO().getAllSuites();
    	for (HashMap<String, Object> hashMap : allSuites) {
    		System.out.println(hashMap.get("name"));
    		System.out.println(hashMap.get("id"));
		}
    }
    
    @Ignore //driver dose not exist anymore.
    @Test
    public void getDriverFromTestcaseTest() {
        List<String> drivers = new ReadReportFromDB().getDriverFromTestcase(1, 1);
        for (int i = 0; i < drivers.size(); i++) {
            System.out.println(drivers.get(i));
        }
    }
    @Test
    public void getAllClassNamesTest(){
         List<HashMap<String, Object>> classname = new GetClassesDAO().getAllClassNames(1);
         System.out.println(classname.toString());
        for (HashMap hashMap : classname) {
			System.out.println(hashMap);
		}
    }
    
    
    @Test
    public void getLastestSuiteRunFromIDTest(){
    }
    
    @Test
    public void getDriverAndMessageFromLastRunTest(){
    	String array = new GetDriverByTestcaseDAO().getDriverByTestcaseId(1, "20150204000100");
    	System.out.println(array.toString());
    }
    
    @Test
    public void getLastestTimestamp(){
    	String array = new GetLatestSuiteDAO().getLatestSuite(1);
    	System.out.println(array);
    }
    
    @Test
    public void getLastestByTimestamp(){
    	String array = new GetSuiteByTimestampDAO().getSuiteByTimestamp(2, "20150101080000");
    	System.out.println(array);
    }
    @Test
    public void deviceRunAmonthAgoTest(){
    	JsonArray array = new GetDevicesAMonthAgoDAO().compareDeviceAndDate();
    	System.out.println("Devices not run for a month: "+array);
    }
}
