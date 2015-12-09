package se.redmind.rmtest.web.route.api.driver;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.db.jdbm.message.MessageDAO;

public class GetDriverByTestcaseDAO extends DBBridge {

	private MessageDAO messageDAO;

	public GetDriverByTestcaseDAO() {
		messageDAO = MessageDAO.getInstance();
	}
	
	public String getDriverByTestcaseId(int testCaseId, String timestamp){
		JsonArray jsonArray  = getDriverAndMessageFromLastRun(testCaseId, timestamp);
		return new Gson().toJson(jsonArray);
	}
	public JsonArray getDriverAndMessageFromLastRun(int testcaseId, String timestamp){
		String SELECT_ALL_FROM_REPORT_OS_DEVICE_BROWSER = "select testcase.testcasename, device.devicename, os.osname, os.osversion, browser.browsername, browser.browserversion, time, report.result, report.message from report inner join os on os.os_id = report.os_id inner join device on device.device_id = report.device_id inner join browser on browser.browser_id = report.browser_id inner join testcase on testcase.testcase_id = report.testcase_id where report.testcase_id = ";
		String AND_TIMESTAMP = " and timestamp = ";
		String sql = SELECT_ALL_FROM_REPORT_OS_DEVICE_BROWSER+testcaseId+AND_TIMESTAMP+"'"+timestamp+"'";
//    	System.out.println(sql);
    	ResultSet rs = readFromDB(sql);
    	JsonArray array = new JsonArray();
    	try {
			while(rs.next()){
				JsonObject jsonObject = new JsonObject();
				jsonObject.add("testcasename", new JsonPrimitive(rs.getString("testcasename")));
				jsonObject.add("devicename", new JsonPrimitive(rs.getString("devicename")));
				jsonObject.add("osname", new JsonPrimitive(rs.getString("osname")));
				jsonObject.add("osversion", new JsonPrimitive(rs.getString("osversion")));
				jsonObject.add("browsername", new JsonPrimitive(rs.getString("browsername")));
				jsonObject.add("browserversion", new JsonPrimitive(rs.getString("browserversion")));
				jsonObject.add("timetorun", new JsonPrimitive(rs.getString("time")));
				jsonObject.add("result", new JsonPrimitive(rs.getString("result")));
				String dbMessageId = rs.getString("message");
				String message = getMessage(dbMessageId);
				jsonObject.add("message", new JsonPrimitive(message));
				
				array.add(jsonObject);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
    	  	
    }
	
	private String getMessage(String id){
		if (id != null && !id.isEmpty()) {
			String message = messageDAO.get(Integer.valueOf(id));
			if (message == null) {
				return "";
			}
			return message;
		}
		return "";
	}
}
