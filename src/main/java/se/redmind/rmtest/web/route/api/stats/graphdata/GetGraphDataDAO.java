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
		JsonArray graphData = reportStats.getGraphDataAsJson(params);
		return new Gson().toJson(graphData);
	}
	
}
