package se.redmind.rmtest.web.route.api.admin.config;

import com.google.gson.Gson;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.properties.ConfigJson;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetConfigWS extends Route {

	public GetConfigWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		ConfigJson config = ConfigHandler.getInstance().getConfig();
		return new Gson().toJson(config);
	}

}
