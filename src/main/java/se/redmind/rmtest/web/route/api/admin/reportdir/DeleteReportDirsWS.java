package se.redmind.rmtest.web.route.api.admin.reportdir;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.properties.ConfigJson;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteReportDirsWS extends Route {

	public DeleteReportDirsWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String body = request.body();
		JsonArray reportArray = new Gson().fromJson(body, JsonArray.class);
		ConfigHandler cHandler = ConfigHandler.getInstance();
		ConfigJson config = cHandler.getConfig();
		JsonArray reportPaths = config.getReportPaths();
		JsonArray removeArray = new JsonArray();
		for (JsonElement index : reportArray) {
			removeArray.add(reportPaths.get(index.getAsInt()));
		}
		cHandler.removeAllPaths(removeArray);
		return true;
	}

}
