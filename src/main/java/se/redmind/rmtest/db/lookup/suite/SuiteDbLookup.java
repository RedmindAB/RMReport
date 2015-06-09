package se.redmind.rmtest.db.lookup.suite;

import java.sql.ResultSet;
import java.sql.SQLException;

import se.redmind.rmtest.db.DBBridge;


/**
 * Created by johan on 15-01-29.
 */
public class SuiteDbLookup extends DBBridge{

    String GET_SUIT_ID = "select suite_id from suite where suitename= ";
    String GET_SUITE_SPECIFIC = "select report.class_id, class.classname, report.testcase_id, testcase.testcasename, result, timestamp, time from report inner join class on report.class_id = class.class_id INNER JOIN testcase ON report.testcase_id = testcase.testcase_id where timestamp = '";
    String AND_SUITEID_ ="' AND suite_id = ";
    String GROUP_BY_ = " GROUP BY report.testcase_id;";

    public int getSuiteID(String suitName){
        ResultSet rs = readFromDB(GET_SUIT_ID+"'"+suitName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
    }
    
    
    
}
