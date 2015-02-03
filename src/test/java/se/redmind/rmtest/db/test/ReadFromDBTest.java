package se.redmind.rmtest.db.test;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.rules.Timeout;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.read.ReadClassFromDB;
import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.read.ReadSuiteFromDB;
import se.redmind.rmtest.db.read.ReadTestcaseFromDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by johan on 15-01-27.
 */
public class ReadFromDBTest {
    Connection conn = DBCon.getDbInstance().getConnection();
    
    @Rule
    public Timeout timeout = new Timeout(200);

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
    public void reportExistsTest(){
        boolean b = new ReadReportFromDB().reportExists("20150121-160906");
        assertTrue(b);
    }
    @Test
    public void getAllReportNamesTest(){
        HashSet<String> allReportNames = new ReadReportFromDB().getAllReportNames();
        for (String string : allReportNames) {
			System.out.println("reportname: "+string);
		}
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
    
    @Test
    public void getDriverByTestcase(){
        List<HashMap<String, String>> classname = new ReadTestcaseFromDB().getDriverFromTestcaseID(1);
        for (HashMap hashMap : classname) {
            System.out.println(hashMap);
        }
    }
}
