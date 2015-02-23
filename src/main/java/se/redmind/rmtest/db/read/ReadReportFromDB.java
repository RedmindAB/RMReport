package se.redmind.rmtest.db.read;



import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.util.CalendarCounter;

/**
 * Created by johan on 15-01-26.
 */
public class ReadReportFromDB extends DBBridge{

    String GET_MAX_ID_FROM_REPORT = "select * from report order by id desc limit 1";
    String REPORT_EXISTS = "select timestamp from report where timestamp =";
    String GET_DATE_AND_TIME_FROM_REPORT_AFTER = "select * from report where timestamp >";
    String GET_DATE_AND_TIME_FROM_REPORT_BEFORE = "select * from report where timestamp <";
    String GET_DRIVER_FROM_REPORT = "select distinct driver from report where suite_id = ";
    String AND_TESTCASE_ID = " and testcase_id =";
    String GET_REPORTS_BY_SUITEID = "SELECT timestamp, result, time FROM report WHERE suite_id = {suiteid} ORDER BY timestamp DESC;";
    String CREATE_REPORT_VIEW = "create view report_view as SELECT DISTINCT timestamp FROM report WHERE suite_id = 1 ORDER BY timestamp DESC LIMIT 50";
    String GET_RESULT_BY_DRIVER = "select result,driver, count(result) from report where testcase_id = 1 group by result,driver";
    String GET_SPECIFIC_METHOD_DRIVER_INFO = "select driver, timestamp, message, result, time from report where driver = ";
    String LIMIT = " limit 20";
    
    String TIMESTAMP_AFTER_DATE = "select timestamp, devicename from report inner join device on report.device_id = device.device_id where timestamp > ";
    String TIMESTAMP_BEFORE_DATE = "select timestamp, devicename from report inner join device on report.device_id = device.device_id where timestamp < ";
    
    
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

    
    
    
    public List<HashMap<String, String>> getReportListData(int suiteid){
    	HashMap<String, String> map = new HashMap<>();
    	map.put("suiteid", ""+suiteid);
    	String sql = stringParser.getString(GET_REPORTS_BY_SUITEID, map);
    	ResultSet rs = readFromDB(sql);
    	List<HashMap<String, String>> result = extractResultSetToGraphData(rs);
    	return result;
    }
    

    protected JsonArray extractGraphData(ResultSet rs) {
    	JsonArray graphArray = new JsonArray();
    	try {
			while (rs.next()) {
				JsonObject timestamp = new JsonObject();
				timestamp.add("timestamp", new JsonPrimitive(rs.getString("timestamp")));
				timestamp.add("time", new JsonPrimitive(rs.getDouble("time")));
				timestamp.add("pass", new JsonPrimitive(rs.getInt("passed")));
				timestamp.add("fail", new JsonPrimitive(rs.getInt("failure")));
				timestamp.add("error", new JsonPrimitive(rs.getInt("error")));
				timestamp.add("skipped", new JsonPrimitive(rs.getInt("skipped")));
				graphArray.add(timestamp);
			}
		} catch (SQLException e) {
			return null;
		}
    	return graphArray;
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
	public JsonArray deviceRunThisMonth(){
		String dateAmonthAgo = new CalendarCounter().getDateOneMonthAgoAsString();
		ResultSet rs = readFromDB(TIMESTAMP_AFTER_DATE+"'"+dateAmonthAgo+"-000000"+"'"+" group by devicename");
		JsonArray array = new JsonArray();
		try {
			while(rs.next()){
				JsonObject object = new JsonObject();
				object.add("device", new JsonPrimitive(rs.getString(2)));
				array.add(object);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public JsonArray deviceRunAmonthAgo(){
		String dateAmonthAgo = new CalendarCounter().getDateOneMonthAgoAsString();
		ResultSet rs2 = readFromDB(TIMESTAMP_BEFORE_DATE+"'"+dateAmonthAgo+"-000000"+"'"+" group by devicename");
		JsonArray array2 = new JsonArray();
		try {
			while(rs2.next()){
				JsonObject object2 = new JsonObject();
				object2.add("device", new JsonPrimitive(rs2.getString(2)));
				array2.add(object2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array2;
	}
	
	public JsonArray compareDeviceAndDate(){
		JsonArray array = null;
		JsonArray array1 = deviceRunThisMonth();
		JsonArray array2 = deviceRunAmonthAgo();
		for(int j = 0; j < array2.size();j++){
		for(int i = 0 ;i < array1.size(); i++){
			if(array1.get(i).equals(array2.get(j))){
				array2.remove(array1.get(i));
			}
			}
			
		}
		return array2;
		
	}
	
}

