package se.redmind.rmtest.web.route.api.stats.graphdata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphDataWS extends Route {

	public GetGraphDataWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		try {
			String data = (String) request.body();
			JsonArray json = new Gson().fromJson(data, JsonArray.class);
			return new GetGraphDataDAO().getGraphData(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nope";
	}

}
