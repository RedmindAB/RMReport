package se.redmind.rmtest.web.route.api.suite.data;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadReportFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuiteDataDAO {

	public String getData(int suiteid) {
		JsonArray array = new JsonArray();
		List<HashMap<String, String>> reportListData = new ReadReportFromDB().getReportListData(suiteid);
		try {
			for (HashMap<String, String> hashMap : reportListData) {
				JsonObject report = new JsonObject();
				report.add("timestamp", new JsonPrimitive(hashMap.get("timestamp")));
				report.add("pass", new JsonPrimitive(Integer.valueOf(hashMap.get("pass"))));
				report.add("fail", new JsonPrimitive(Integer.valueOf(hashMap.get("fail"))));
				report.add("error", new JsonPrimitive(Integer.valueOf(hashMap.get("error"))));
				report.add("time", new JsonPrimitive(Float.valueOf(hashMap.get("time"))));
				array.add(report);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Gson().toJson(array);
	}

}
