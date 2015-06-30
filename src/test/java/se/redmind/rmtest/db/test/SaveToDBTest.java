package se.redmind.rmtest.db.test;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.report.parser.ReportXMLParser;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.test.ReportLoaderTest;

import java.io.File;
import java.sql.Connection;

/**
 * Created by johan on 15-01-27.
 */
public class SaveToDBTest {

    Connection con = DBCon.getDbTestInstance().getConnection();

    private static ReportLoader loader;
    private static ReportXMLParser parser;
    private static final String testFileName = "TEST-test.java.se.redmind.rmtest.selenium.example.CreateLogTests-20150202-140728.xml";
    private static File file;

    @BeforeClass
    public static void beforeClass(){
        loader = new ReportLoader(ReportLoaderTest.path, false);
        parser = new ReportXMLParser();
        file = loader.getReportByFileName(testFileName);
    }

    @Test
    public void createDbTest(){
        DBCon.getDbInstance();
    }


}
