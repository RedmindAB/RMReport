package se.redmind.rmtest.web.route.api.stats.grahoptions;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.graphoptions.ReadGraphOptions;
import se.redmind.rmtest.util.GraphDataExtractor;
import se.redmind.rmtest.web.route.api.stats.graphdata.ReadStatsFromReport;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GetGraphOptionsDAO {

	public String getGraphData(int suiteid, int limit){
		JsonObject json = new ReadGraphOptions().getGraphOptions(suiteid, limit);
		return new Gson().toJson(json);
	}
	
}
