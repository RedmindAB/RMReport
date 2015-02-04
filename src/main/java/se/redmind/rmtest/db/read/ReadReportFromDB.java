package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by johan on 15-01-26.
 */
public class ReadReportFromDB extends DBBridge{

    String GET_MAX_ID_FROM_REPORT = "select * from report order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from report where timestamp =";
    String GET_ALL_REPORT_NAMES = "select name from report";
    String GET_ID_FROM_REPORTNAME = "select id from report where name =";
    String GET_DATE_AND_TIME_FROM_REPORT_AFTER = "select * from report where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORT_BEFORE = "select * from report where timestamp <";
    String GET_RUNTIME_FROM_REPORT = "select time from reports where name =";
    String GET_RUNTIME_FROM_ALL_REPORT = "";
    String GET_DRIVER_FROM_REPORT = "select distinct driver from report where suite_id = ";
    String AND_TESTCASE_ID = " and testcase_id =";
    String GET_REPORTS_BY_SUITEID = "SELECT timestamp, result, time FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC;";

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

    public boolean reportExists(String reportTimeStamp){
        ResultSet rs = readFromDB(REPORT_EXISTS + "'" + reportTimeStamp + "'" + "limit 1");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashSet<String> getAllReportNames(){
        HashSet<String> hs = new HashSet<String>();
        ResultSet rs = readFromDB(GET_ALL_REPORT_NAMES);
        try {
            while(rs.next())
                hs.add(rs.getString(1));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }
    public void  getAllFromSuitID(){


    }
    
    public List<HashMap<String, String>> getReportListData(int suiteid){
    	HashMap<String, String> map = new HashMap<>();
    	map.put("suiteid", ""+suiteid);
    	String sql = stringParser.getString(GET_REPORTS_BY_SUITEID, map);
    	ResultSet rs = readFromDB(sql);
    	List<HashMap<String, String>> result = extractResultSetToGraphData(rs);
    	return result;
    }
    

	protected List<HashMap<String, String>> extractResultSetToGraphData(ResultSet rs) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
    	try {
    		String currentTimestamp = null;
    		float time = 0;
    		int fail = 0;
    		int pass = 0;
    		int error = 0;
			while (rs.next()) {
				boolean isCurrentTimestampNull = currentTimestamp == null;
				if (isCurrentTimestampNull || !currentTimestamp.equals(rs.getString("timestamp"))) {
					if (!isCurrentTimestampNull) {
						HashMap<String, String> hashMap = new HashMap<String,String>();
						hashMap.put("timestamp", currentTimestamp);
						hashMap.put("time", ""+time);
						hashMap.put("pass", ""+pass);
						hashMap.put("fail", ""+fail);
						hashMap.put("error", ""+error);
						result.add(hashMap);
					}
					currentTimestamp = rs.getString("timestamp");
					fail = 0;
					pass = 0;
					error = 0;
					time = 0f;
				}
				String res = rs.getString("result");
				switch (res) {
				case "passed":
					pass+=1;
					break;
				case "error":
					error+=1;
					break;
				case "failure":
					fail+=1;
				default:
					break;
				}
				time+=rs.getFloat("time");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
