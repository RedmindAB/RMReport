package se.redmind.rmtest.web.route.api.suite.data;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadReportFromDB;
import se.redmind.rmtest.util.GraphDataExtractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuiteDataDAO {

	public String getData(int suiteid) {
		List<HashMap<String, String>> reportListData = new ReadReportFromDB().getReportListData(suiteid);
		return new Gson().toJson(new GraphDataExtractor().extractGraphData(reportListData));
	}

}
