package se.redmind.rmtest.db;

import se.redmind.rmtest.report.parser.ReportTestCase;

import java.sql.*;
import java.util.List;

/**
 * Created by johan on 15-01-27.
 */
public class TestCaseStatementBuilder {

        Connection conn;

         public TestCaseStatementBuilder(Connection connection) {
         conn = connection;
         }

        public PreparedStatement testCaseStatement(List<ReportTestCase> testcases){

            try {
                Statement stat = conn.createStatement();
                PreparedStatement prep = conn.prepareStatement("insert into testcases (name, driver, error, time, failures, passed) values (?,?,?,?,?,?);");

                for(ReportTestCase testcase: testcases){

                    String testCaseError="";

                    if(testcase.getErrorMessage()!=null){
                        testCaseError=testcase.getErrorMessage();
                    }
                    System.out.println(testCaseError);

                    prep.setString (1, testcase.getName());
                    prep.setString (2, testcase.getDriverName());
                    prep.setString (3, testCaseError);
                    prep.setDouble (4, testcase.getTime());
                    prep.setString (5, testcase.getFailureMessage());
                    prep.setBoolean(6, testcase.isPassed());
                    prep.addBatch();

                }

                ResultSet rs = stat.executeQuery("select * from testcases;");
                while (rs.next()) {
                    System.out.println("name = " + rs.getString("name"));
                    System.out.println("driver = " + rs.getString("driver"));
                    System.out.println("error = " + rs.getString("error"));
                    System.out.println("time = " + rs.getString("time"));
                    System.out.println("passed = " + rs.getString("passed"));
                    System.out.println("values = " + rs.getString("values"));
                }
                rs.close();
                return prep;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
