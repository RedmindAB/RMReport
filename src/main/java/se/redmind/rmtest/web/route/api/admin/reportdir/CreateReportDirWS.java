package se.redmind.rmtest.web.route.api.admin.reportdir;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateReportDirWS extends Route {

	public CreateReportDirWS(String path) {
		super(path);
	}

	
	/**
	 * @api {post} /admin/reportdir
	 * @apiName CreateReportDir
	 * @apiGroup Admin
	 * @apiParamExample {json} Request-Example:
	 * 	{
	 * 		["a/nice/path",
	 * 		"another/nice/Path"]
	 * 	}
 	 *
	 * @apiSuccess {boolean} boolean 
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		String reportdirs = request.body();
		JsonArray dirArray = new Gson().fromJson(reportdirs, JsonArray.class);
		ConfigHandler cHandler = ConfigHandler.getInstance();
		for (JsonElement jsonElement : dirArray) {
			cHandler.saveReportPath(jsonElement.getAsString());
		}
		return true;
	}

}
