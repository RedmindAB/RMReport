package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadStatsFromReport;
import se.redmind.rmtest.util.GraphDataExtractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetGraphDataDAO {

	public String getGraphData(JsonObject params){
		ReadStatsFromReport reportStats = new ReadStatsFromReport();
		List<HashMap<String, String>> graphData = reportStats.getGraphData(params);
		JsonArray extractGraphData = new GraphDataExtractor().extractGraphData(graphData);
		return new Gson().toJson(extractGraphData);
	}
	
}
