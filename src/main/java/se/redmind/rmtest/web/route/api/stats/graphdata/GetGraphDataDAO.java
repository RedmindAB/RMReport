package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.read.ReadStatsFromReport;
import se.redmind.rmtest.util.GraphDataExtractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class GetGraphDataDAO {

	public String getGraphData(JsonArray paramsArray){
		ReadStatsFromReport reportStats = new ReadStatsFromReport();
		JsonArray resultArray = new JsonArray();
		for (JsonElement params : paramsArray) {
			JsonObject paramsAsObject = params.getAsJsonObject();
			String name = paramsAsObject.get("name").getAsString();
			JsonArray graphData = reportStats.getGraphDataAsJson(paramsAsObject);
			JsonObject result = new JsonObject();
			result.add("name", new JsonPrimitive(name));
			result.add("data", graphData);
			resultArray.add(result);
		}
		return new Gson().toJson(resultArray);
	}
	
}
