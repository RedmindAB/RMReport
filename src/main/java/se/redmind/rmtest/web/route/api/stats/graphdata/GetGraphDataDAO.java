package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.web.route.api.ErrorResponse;

public class GetGraphDataDAO {

	Logger log = LogManager.getLogger(GetGraphDataDAO.class);
	
	private int reslimit = 0;
	private int suite_id = 0;
	private boolean error = false;

	public JsonElement getGraphData(JsonArray paramsArray){
		//Init the local variables, so that we know what suite_id and limit there is to this request.
		initLocalVariables(paramsArray);
		if (error) {
			return checkErrors(getFirstJson(paramsArray)).getJson();
		}
		ReadStatsFromReport reportStats = new ReadStatsFromReport();
		GraphDataBuilder graphBuilder = new GraphDataBuilder();
		JsonArray resultArray = new JsonArray();
		//Get all the timestamps from now to the result limit.
		ResultSet timestamps = reportStats.getTimestamps(reslimit, suite_id);
		//sort the timestamps
		graphBuilder.generateTimestampList(timestamps);
		for (JsonElement params : paramsArray) {
			JsonObject paramsAsObject = params.getAsJsonObject();
			//Get the name of this dataset (a line on the graph, showed as a legend).
			String name = paramsAsObject.get("name").getAsString();
			//Get the data from the database, save each row to a JsonObject and map it with a timestamp.
			HashMap<Long, JsonObject> graphData = reportStats.getGraphDataAsHashMap(paramsAsObject, graphBuilder.getMinTimestamp());
			JsonArray dataArray = graphBuilder.getDataArray(graphData);
			if (dataArray != null) {
				JsonObject result = new JsonObject();
				result.add("name", new JsonPrimitive(name));
				result.add("data", dataArray);
				resultArray.add(result);
			}
		}
		return resultArray;
	}

	private void initLocalVariables(JsonArray paramsArray) {
		try {
			JsonObject firstParams = getFirstJson(paramsArray);
			reslimit = firstParams.get("reslimit").getAsInt();
			if(reslimit<=0) this.error = true;
			suite_id = firstParams.get("suiteid").getAsInt();
		} catch (Exception e) {
			log.error("Could not extract variables for graphdata from response");
			error = true;
		}
	}
	
	private ErrorResponse checkErrors(JsonObject params){
		JsonArray badParams = new JsonArray();
		if (suite_id<=1) {
			badParams.add(new JsonPrimitive("suite id was not set to a valid value"));
			log.error("suite id (suite_id) was not set to a valid value");
		}
		if (reslimit<=0) {
			badParams.add(new JsonPrimitive("reslimit is not set to a valid value"));
			log.error("result limit (reslimit) was not set to a valid value");
		}
		return new ErrorResponse(badParams, GetGraphDataDAO.class);
	}
	
	public JsonObject getFirstJson(JsonArray paramsArray){
		return paramsArray.get(0).getAsJsonObject();
	}
	
}
