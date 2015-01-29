package se.redmind.rmtest.db.test;

import org.junit.Test;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.read.ReadReportFromDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johan on 15-01-27.
 */
public class ReadFromDBTest {
    Connection conn = DBCon.getDbInstance().getConnection();

    @Test
    public void readFromTestcases(){
        Statement stat = null;

        try {
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from testcase;");
            while(rs.next()){
            	
                System.out.println("testcase name = " + rs.getString("name"));
                System.out.println("testcase id = " + rs.getString("id"));

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void readFromReports(){

        Statement stat = null;
        try {
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from reports;");
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
            	columnNames.add(meta.getColumnLabel(i));
			}
            while (rs.next()) {
            	
                System.out.println("Report name = " + rs.getString("name"));
                System.out.println("Report suitename = " + rs.getString("suitename"));
                System.out.println("Report timestamp = " + rs.getString("timestamp"));
                System.out.println("Report tests = " + rs.getString("tests"));
                System.out.println("Report skipped = " + rs.getString("skipped"));
                System.out.println("Report failures = " + rs.getString("failures"));
                System.out.println("Report time = " + rs.getString("time"));
                System.out.println("Report id = " + rs.getString("id"));

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getMaxIdTest(){
        new ReadReportFromDB(conn).getMaxID();
    }
    @Test
    public void reportExistsTest(){
        new ReadReportFromDB(conn).reportExists("20150121-160906");
    }
    @Test
    public void getAllReportNamesTest(){
        new ReadReportFromDB(conn).getAllReportNames();
    }
}
