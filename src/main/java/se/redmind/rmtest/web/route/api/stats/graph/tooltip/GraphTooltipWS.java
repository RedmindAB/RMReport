package se.redmind.rmtest.web.route.api.stats.graph.tooltip;

import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class GraphTooltipWS extends CachedRoute {


	@Override
	public JsonElement handleRequest(Response response, Request request) {
		long timestamp = extractLong(request, "timestamp");
		int suiteid = extractInt(request, "suiteid");
		GraphTooltipDAO graphTooltipDAO = new GraphTooltipDAO(timestamp, suiteid);
		return graphTooltipDAO.getResult();
	}

}
