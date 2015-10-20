package se.redmind.rmtest.web.route.api.suite.parameters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class SuiteParametersWS extends CachedRoute {

	public SuiteParametersWS(String path) {
		super(path);
	}

	@Override
	public JsonElement handleRequest(Response response, Request request) {
		System.out.println("Incomming call to parameters");
		String suiteid = request.params("suiteid");
		String timestamp = request.params("timestamp");
		JsonObject parameters = new SuiteParametersDAO().getParameters(suiteid, timestamp);
		System.out.println("Sending back parameters");
		return parameters;
	}

}
