package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadStatsFromReport;
import se.redmind.rmtest.util.GraphDataExtractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetGraphDataDAO {

	public String getGraphData(JsonObject data){
		ReadStatsFromReport reportStats = new ReadStatsFromReport();
		List<HashMap<String, String>> graphData = reportStats.getGraphData(data);
		JsonArray extractGraphData = new GraphDataExtractor().extractGraphData(graphData);
		System.out.println("Graph data size: "+extractGraphData.size());
		return new Gson().toJson(extractGraphData);
	}
	
}
