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
		try {
			String data = (String) request.body();
			System.out.println(data);
			JsonObject json = new Gson().fromJson(data, JsonObject.class);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nope";
	}

}
