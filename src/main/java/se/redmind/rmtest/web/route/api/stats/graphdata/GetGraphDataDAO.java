package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.sql.ResultSet;
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
		GraphDataBuilder graphBuilder = new GraphDataBuilder();
		JsonArray resultArray = new JsonArray();
		JsonObject firstParams = getFirstJson(paramsArray);
		int reslimit = firstParams.get("reslimit").getAsInt();
		int suite_id = firstParams.get("suiteid").getAsInt();
		ResultSet timestamps = reportStats.getTimestamps(reslimit, suite_id);
		graphBuilder.generateTimestampList(timestamps);
		for (JsonElement params : paramsArray) {
			JsonObject paramsAsObject = params.getAsJsonObject();
			String name = paramsAsObject.get("name").getAsString();
			HashMap<Long, JsonObject> graphData = reportStats.getGraphDataAsHashMap(paramsAsObject, graphBuilder.getMinTimestamp());
			JsonArray dataArray = graphBuilder.getDataArray(graphData);
			JsonObject result = new JsonObject();
			result.add("name", new JsonPrimitive(name));
			result.add("data", dataArray);
			resultArray.add(result);
		}
		return new Gson().toJson(resultArray);
	}
	
	public JsonObject getFirstJson(JsonArray paramsArray){
		return paramsArray.get(0).getAsJsonObject();
	}
	
}
