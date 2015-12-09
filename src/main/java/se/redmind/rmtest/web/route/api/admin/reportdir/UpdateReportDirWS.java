package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import se.redmind.rmtest.util.FileUtil;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateReportDirWS implements Route {


	/**
	 * @api {put} /admin/reportdir/
	 * @apiParam {Number} index index of the report directory to change.
	 * @apiGroup Admin
	 * @apiParamExample {json} Request-Example:
	 * 	{
	 * 		"oldPath":"/old/path/to/reports",
	 * 		"newPath":"/new/path/to/reports"
	 * 	}
	 * @apiSuccess {boolean} return a boolean of success or fail.
	 * @apiDescription set the body of the request to the path that should replace the old path.
	 */
	@Override
	public Object handle(Request request, Response response) {
		String requestBody = request.body();
		JsonObject requestJson = new Gson().fromJson(requestBody, JsonObject.class);
		ConfigHandler cHandler = ConfigHandler.getInstance();
		boolean directoryExists = FileUtil.directoryExists(requestJson.get("newPath").getAsString());
		if (cHandler.reportPathExistInConfig(requestJson.get("newPath").getAsString())) {
			System.out.println("path exists in config: "+requestJson.get("newPath").toString());
			try {
				response.raw().sendError(417, "Path already exists in the config. body: "+requestBody);
				response.status(417);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}
		else if (directoryExists) {
			cHandler.updateReportPath(requestJson.get("oldPath").getAsString(), requestJson.get("newPath").getAsString());
			return true;
		}
		else {
			try {
				response.raw().sendError(417, FileUtil.getLastMessage()+" body: "+requestBody);
				response.status(417);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}
	}

}
