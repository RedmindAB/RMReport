package se.redmind.rmtest.web.route.api.suite.parameters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class SuiteParametersWS extends CachedRoute {

	@Override
	public JsonElement handleRequest(Response response, Request request) {
		String suiteid = request.params("suiteid");
		String timestamp = request.params("timestamp");
		JsonObject parameters = new SuiteParametersDAO().getParameters(suiteid, timestamp);
		return parameters;
	}

}
