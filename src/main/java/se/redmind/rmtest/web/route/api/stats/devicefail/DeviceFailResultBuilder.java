package se.redmind.rmtest.web.route.api.stats.devicefail;

import java.util.HashMap;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DeviceFailResultBuilder {
	
	private String DEVICENAME, RESULT;
	
	private JsonArray deviceArray;
	private HashMap<String,Integer> totalRes;
	private HashMap<String,Integer> totalFail;

	public DeviceFailResultBuilder(JsonArray deviceArray) {
		this.deviceArray = deviceArray;
		this.totalRes = new HashMap<String,Integer>();
		this.totalFail = new HashMap<String,Integer>();
		this.DEVICENAME = DeviceStatusFailDAO.DEVICENAME;
		this.RESULT = DeviceStatusFailDAO.RESULT;
	}
	
	public JsonArray getResults(){
		for (JsonElement deviceFail : deviceArray) {
			JsonObject deviceFailJson = deviceFail.getAsJsonObject();
			String devicename = deviceFailJson.get(DEVICENAME).getAsString();
			String result = deviceFailJson.get(RESULT).getAsString();
			addResult(devicename, result);
		}
		return buildResult();
	}
	
	private JsonArray buildResult(){
		Set<String> keySet = totalRes.keySet();
		JsonArray array = new JsonArray();
		for (String devicename : keySet) {
			JsonObject jObj = new JsonObject();
			jObj.addProperty(DEVICENAME, devicename);
			jObj.addProperty("total", totalRes.get(devicename));
			jObj.addProperty("totalFail", totalFail.get(devicename));
			array.add(jObj);
		}
		return array;
	}

	private void addResult(String devicename, String result) {
		addTotalRes(devicename);
		addTotalFail(devicename, result);
	}

	private void addTotalFail(String devicename, String result) {
		Integer tot = totalFail.get(devicename);
		boolean isFail = isFail(result);
		if (tot != null && isFail) {
			totalFail.put(devicename, (tot+1));
		}
		else if (tot == null && isFail(result)){
			totalFail.put(devicename, 1);
		}
		else if (tot == null && !isFail ){
			totalFail.put(devicename, 0);
		}
	}

	private void addTotalRes(String devicename) {
		Integer tot = totalRes.get(devicename);
		if (tot != null) {
			totalRes.put(devicename, (tot+1));
		}
		else totalRes.put(devicename, 1);
		
	}
	
	private boolean isFail(String result){
		return result.equals("failure") || result.equals("error");
	}
}
