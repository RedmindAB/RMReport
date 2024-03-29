package se.redmind.rmtest.db.lookup.report;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import se.redmind.rmtest.db.DBBridge;

/**
 * Created by johan on 15-01-26.
 */
public class ReportDbLookup extends DBBridge{

    String GET_MAX_ID_FROM_REPORT = "select * from report order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from report where timestamp =";
    String GET_DATE_AND_TIME_FROM_REPORT_AFTER = "select * from report where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORT_BEFORE = "select * from report where timestamp <";
    String GET_DRIVER_FROM_REPORT = "select distinct driver from report where suite_id = ";
    String AND_TESTCASE_ID = " and testcase_id =";
    String CREATE_REPORT_VIEW = "create view report_view as SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50";
    String GET_RESULT_BY_DRIVER = "select result,driver, count(result) from report where testcase_id = 1 group by result,driver";
    String GET_SPECIFIC_METHOD_DRIVER_INFO = "select driver, timestamp, message, result, time from report where driver = ";
    String LIMIT = " limit 20";
    
    private String GET_TIMESTAMPS_WITH_SUITE_NAME = "SELECT DISTINCT timestamp, suite.suitename FROM report INNER JOIN suite ON (report.suite_id = suite.suite_id);";
    
   
    
    
    public List getDriverFromTestcase(Integer suite_id, Integer testcase_id){
        List<String> ls = new ArrayList<>();
        ResultSet rs = readFromDB(GET_DRIVER_FROM_REPORT+suite_id+AND_TESTCASE_ID+testcase_id);
        try {
            while(rs.next()){
                ls.add(rs.getString("driver"));
            }
            return ls;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean reportExists(long reportTimeStamp){
        ResultSet rs = readFromDB(REPORT_EXISTS + "'" + reportTimeStamp + "'" + "limit 1");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
    public HashSet<String> getReportsWithSuiteNames(){
    	ResultSet rs = readFromDB(GET_TIMESTAMPS_WITH_SUITE_NAME);
    	HashSet<String> result = new HashSet<String>();
    	try {
			while (rs.next()) {
				result.add(rs.getString("timestamp")+rs.getString("suitename"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
}

