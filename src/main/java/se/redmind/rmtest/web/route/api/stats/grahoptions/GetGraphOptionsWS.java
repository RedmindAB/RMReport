package se.redmind.rmtest.web.route.api.stats.grahoptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphOptionsWS extends Route {

	public GetGraphOptionsWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		try {
			int suiteid = Integer.valueOf(request.queryParams("suiteid"));
			int limit = Integer.valueOf(request.queryParams("limit"));
			return new GetGraphOptionsDAO().getGraphData(suiteid, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nope";
	}

}
