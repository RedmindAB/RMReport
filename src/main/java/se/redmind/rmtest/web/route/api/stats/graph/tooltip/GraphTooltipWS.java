package se.redmind.rmtest.web.route.api.stats.graph.tooltip;

import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;
import spark.Route;

public class GraphTooltipWS extends CachedRoute {

	public GraphTooltipWS(String path) {
		super(path);
	}

	@Override
	public JsonElement handleRequest(Response response, Request request) {
		long timestamp = extractLong(request, "timestamp");
		int suiteid = extractInt(request, "suiteid");
		GraphTooltipDAO graphTooltipDAO = new GraphTooltipDAO(timestamp, suiteid);
		return graphTooltipDAO.getResult();
	}

}
