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
                PreparedStatement prep = conn.prepareStatement("insert into testcases (name, driver, error, time, failures, passed) values (?,?,?,?,?,?);");

                for(ReportTestCase testcase: testcases){

                    String testCaseError="";
                    if(testcase.getErrorMessage()!=null){
                        testCaseError=testcase.getErrorMessage();
                    }
                    prep.setString (1, testcase.getName());
                    prep.setString (2, testcase.getDriverName());
                    prep.setString (3, testCaseError);
                    prep.setDouble (4, testcase.getTime());
                    prep.setString (5, testcase.getFailureMessage());
                    prep.setBoolean(6, testcase.isPassed());
                    prep.addBatch();

                }
                return prep;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
}
