package se.redmind.rmtest.db.test;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.rules.Timeout;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.read.ReadClassFromDB;
import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.db.read.ReadSuiteFromDB;

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
    public void getClassNameOnTestcaseIdTest(){ 
    	HashMap<String, String> classNameOnTestcaseId = new ReadClassFromDB().getClassNameOnTestcaseId();
    	Set<String> keySet = classNameOnTestcaseId.keySet();
    	for (String string : keySet) {
			System.out.println("Key: "+string);
		}
    }
}
