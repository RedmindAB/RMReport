package se.redmind.rmtest.web.route.api.screenshot.structure;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import spark.Request;
import spark.Response;
import spark.Route;

public class ScreenshotStructureWS extends Route {

	public ScreenshotStructureWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.queryParams("timestamp");
		String classid = request.queryParams("classid");
		ScreenshotStructureDAO dao = new ScreenshotStructureDAO();
		JsonArray array = dao.getStructure(classid, timestamp);
		return new Gson().toJson(array);
	}

}
