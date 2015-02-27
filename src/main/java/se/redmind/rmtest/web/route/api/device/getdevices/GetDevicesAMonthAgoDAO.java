package se.redmind.rmtest.web.route.api.device.getdevices;

import java.sql.ResultSet;
import java.sql.SQLException;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.db.lookup.report.ReportDbLookup;
import se.redmind.rmtest.util.CalendarCounter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetDevicesAMonthAgoDAO extends DBBridge{
	
	public String getDeviceAmonthAgo(){
		ReportDbLookup readReport = new ReportDbLookup();
		JsonArray array = compareDeviceAndDate();
		return new Gson().toJson(array);
	}
	public JsonArray deviceRunThisMonth(){
		String TIMESTAMP_AFTER_DATE = "select timestamp, devicename from report inner join device on report.device_id = device.device_id where timestamp > ";
		String dateAmonthAgo = new CalendarCounter().getDateOneMonthAgoAsString();
		ResultSet rs = readFromDB(TIMESTAMP_AFTER_DATE+"'"+dateAmonthAgo+"000000"+"'"+" group by devicename");
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
		String TIMESTAMP_BEFORE_DATE = "select timestamp, devicename from report inner join device on report.device_id = device.device_id where timestamp < ";
		String dateAmonthAgo = new CalendarCounter().getDateOneMonthAgoAsString();
		ResultSet rs2 = readFromDB(TIMESTAMP_BEFORE_DATE+"'"+dateAmonthAgo+"000000"+"'"+" group by devicename");
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
		if(array2.equals(null)){
			return array;
		}
		return array2;
	}
}


