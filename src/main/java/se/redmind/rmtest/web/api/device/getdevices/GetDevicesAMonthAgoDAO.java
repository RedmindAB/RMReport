package se.redmind.rmtest.web.api.device.getdevices;

import se.redmind.rmtest.db.read.ReadReportFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class GetDevicesAMonthAgoDAO{
	
	public String getDeviceAmonthAgo(){
		ReadReportFromDB readReport = new ReadReportFromDB();
		JsonArray array = readReport.compareDeviceAndDate();
		return new Gson().toJson(array);
	}
}


