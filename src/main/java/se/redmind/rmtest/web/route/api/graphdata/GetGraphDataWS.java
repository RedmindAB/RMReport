package se.redmind.rmtest.web.route.api.graphdata;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphDataWS extends Route {

	public GetGraphDataWS(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handle(Request request, Response response) {
		JsonObject json = new Gson().fromJson((String) request.attribute("data"), JsonObject.class);
		return new GetGraphDataDAO();
	}

}
