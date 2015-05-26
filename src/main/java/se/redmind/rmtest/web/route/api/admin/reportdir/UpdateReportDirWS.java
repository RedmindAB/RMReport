package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.redmind.rmtest.util.FileUtil;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateReportDirWS extends Route {

	public UpdateReportDirWS(String path) {
		super(path);
	}

	/**
	 * @api {put} /admin/reportdir/
	 * @apiParam {Number} index index of the report directory to change.
	 * @apiGroup Admin
	 * @apiParamExample {json} Request-Example:
	 * 	{
	 * 		"old":"/old/path/to/reports",
	 * 		"new":"/new/path/to/reports"
	 * 	}
	 * @apiSuccess {boolean} return a boolean of success or fail.
	 * @apiDescription set the body of the request to the path that should replace the old path.
	 */
	@Override
	public Object handle(Request request, Response response) {
		String requestBody = request.body();
		JsonObject requestJson = new Gson().fromJson(requestBody, JsonObject.class);
		ConfigHandler cHandler = ConfigHandler.getInstance();
		boolean directoryExists = FileUtil.directoryExists(requestBody);
		if (directoryExists) {
			cHandler.updateReportPath(requestJson.get("old").getAsString(), requestJson.get("new").getAsString());
			return true;
		}
		else {
			try {
				response.raw().sendError(417, "Path do not exist:"+requestBody);
				response.status(417);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;
		}
	}

}
