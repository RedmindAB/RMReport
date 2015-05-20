package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;

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
		try {
			String body = request.body();
			JsonArray reportArray = new Gson().fromJson(body, JsonArray.class);
			ConfigHandler cHandler = ConfigHandler.getInstance();
			JsonArray reportPaths = cHandler.getConfig().getReportPaths();
			JsonArray removeArray = new JsonArray();
			for (JsonElement index : reportArray) {
				removeArray.add(reportPaths.get(index.getAsInt()));
			}
			cHandler.removeAllPaths(removeArray);
			return true;
		} catch (Exception e) {
			try {
				response.raw().sendError(417, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			response.status(417);
			return response;
		}
	}

}
