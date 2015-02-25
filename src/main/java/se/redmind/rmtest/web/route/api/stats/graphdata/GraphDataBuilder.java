package se.redmind.rmtest.web.route.api.stats.graphdata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GraphDataBuilder {

	private List<Long> timestampArray;
	private long minTimestamp = Long.MAX_VALUE;
	
	public void generateTimestampList(ResultSet rs){
		timestampArray = new ArrayList<Long>();
		try {
			while (rs.next()) {
				long currentTimestamp = rs.getLong("timestamp");
				timestampArray.add(currentTimestamp);
				if (currentTimestamp<minTimestamp) {
					minTimestamp = currentTimestamp;
				}
			}
			Collections.reverse(timestampArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JsonArray getDataArray(HashMap<Long, JsonObject> graphData) {
		JsonArray graphDataToReturn = new JsonArray();
		for (Long timestamp : timestampArray) {
			graphDataToReturn.add(getJsonFromTimestamp(timestamp, graphData));
		}
		return graphDataToReturn;
	}
	
	public long getMinTimestamp(){
		return minTimestamp;
	}

	private JsonObject getJsonFromTimestamp(long timestamp, HashMap<Long, JsonObject> graphData){
		JsonObject data = null; 
		if ((data = graphData.get(timestamp)) == null) {
			data = new JsonObject();
			data.add("timestamp", new JsonPrimitive(timestamp));
			data.add("time", new JsonPrimitive("null"));
			data.add("pass", new JsonPrimitive("null"));
			data.add("fail", new JsonPrimitive("null"));
			data.add("error", new JsonPrimitive("null"));
			data.add("skipped", new JsonPrimitive(true));
		}
		return data;
	}
}
