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
import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseDAO;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteDAO;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampDAO;

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
            	
                System.out.println("testcase name = " + rs.getString("name"));
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
        new ReadSuiteFromDB().getAllSuites();
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
    	List<HashMap<String, Object>> allSuites = new ReadSuiteFromDB().getAllSuites();
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
         List<HashMap<String, Object>> classname = new ReadClassFromDB().getAllClassNames(1);
         System.out.println(classname.toString());
        for (HashMap hashMap : classname) {
			System.out.println(hashMap);
		}
    }
    
    @Test
    public void getReportDataFromSuiteID(){
    	List<HashMap<String, String>> reportListData = new ReadReportFromDB().getReportListData(1);
    }
    
    @Ignore //driver dose not exist anymore
    @Test
    public void getDriverByTestcase(){
        List<HashMap<String, String>> classname = new ReadTestcaseFromDB().getDriverFromTestcaseID(1);
        for (HashMap hashMap : classname) {
            System.out.println(hashMap);
        }
    }
    @Test
    public void getLastestSuiteRunFromIDTest(){
    }
    @Test
    public void getSpecificSuiteRunFromIdAndTimestampTest(){
    	JsonArray array = new ReadSuiteFromDB().getSpecificSuiteRunFromIdAndTimestamp(1,"20150204-000000");
    	System.out.println(array.toString());
    }
    @Test
    public void getDriverAndMessageFromLastRunTest(){
    	String array = new GetDriverByTestcaseDAO().getDriverByTestcaseId(1, "20150204-000100");
    	System.out.println(array.toString());
    }
    
    @Test
    public void getLastestTimestamp(){
    	String array = new GetLatestSuiteDAO().getLatestSuite(1);
    	System.out.println(array);
    }
    
    @Test
    public void getLastestByTimestamp(){
    	String array = new GetSuiteByTimestampDAO().getSuiteByTimestamp(2, "20150101-080000");
    	System.out.println(array);
    }
    @Test
    public void deviceRunAmonthAgoTest(){
    	JsonArray array1 = new ReadReportFromDB().deviceRunThisMonth();
    	JsonArray array2 = new ReadReportFromDB().deviceRunAmonthAgo();
    	JsonArray array3 = new ReadReportFromDB().compareDeviceAndDate();
    	System.out.println("1: "+array1);
    	System.out.println("2: "+array2);
    	System.out.println("3: "+array3);
    }
}
