package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.util.GraphDataExtractor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class GetGraphDataDAO {

	private int reslimit;
	private int suite_id;

	public String getGraphData(JsonArray paramsArray){
		initLocalVariables(paramsArray);
		
		ReadStatsFromReport reportStats = new ReadStatsFromReport();
		GraphDataBuilder graphBuilder = new GraphDataBuilder();
		JsonArray resultArray = new JsonArray();
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
//			System.out.println(name+ " "+ result);
			resultArray.add(result);
		}
		return new Gson().toJson(resultArray);
	}

	private void initLocalVariables(JsonArray paramsArray) {
		JsonObject firstParams = getFirstJson(paramsArray);
		reslimit = firstParams.get("reslimit").getAsInt();
		suite_id = firstParams.get("suiteid").getAsInt();
	}
	
	public JsonObject getFirstJson(JsonArray paramsArray){
		return paramsArray.get(0).getAsJsonObject();
	}
	
}
