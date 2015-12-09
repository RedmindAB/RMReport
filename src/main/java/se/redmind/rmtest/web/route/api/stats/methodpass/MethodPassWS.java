package se.redmind.rmtest.web.route.api.stats.methodpass;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class MethodPassWS extends CachedRoute {


	@Override
	public JsonElement handleRequest(Response response, Request request) {
		int suiteid = getSuite(request);
		if(suiteid == -1) return getError("suiteid must be an integer");
		int limit = getLimit(request, 50);
		return new MethodPassDAO().getMethodPassRatio(suiteid, limit);
	}

	private int getSuite(Request request) {
		int suiteid;
		try {
			suiteid = Integer.valueOf(request.params("suiteid"));
		} catch (Exception e) {
			return -1;
		}
		return suiteid;
	}

	private JsonObject getError(String erroMessage) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("error", errorMessage);
		return jsonObject;
	}

}
