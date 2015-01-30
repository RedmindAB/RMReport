package se.redmind.rmtest.db.test;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.update.ReportStatementBuilder;
import se.redmind.rmtest.db.update.TestCaseStatementBuilder;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.test.ReportLoaderTest;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by johan on 15-01-27.
 */
public class SaveToDBTest {

    Connection con = DBCon.getDbInstance().getConnection();

    private static ReportLoader loader;
    private static ReportXMLParser parser;
    private static final String testFileName = "TEST-test.java.se.redmind.rmtest.selenium.example.CreateLogTests-20150121-160906.xml";
    private static File file;

    @BeforeClass
    public static void beforeClass(){
        loader = new ReportLoader(ReportLoaderTest.path, false);
        parser = new ReportXMLParser();
        file = loader.getXMLReportByFileName(testFileName);
    }

    @Test
    public void saveReportToDBTest(){
        Report report = parser.getReportFromFile(file);
        try {
            new ReportStatementBuilder(con).reportStatement(report).executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void saveTestCaseToDBtest(){
        Report report = parser.getReportFromFile(file);
        try {
            new TestCaseStatementBuilder(con).testCaseStatement(report.getTestCaseArray()).executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}