package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.util.FileUtil;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateReportDirWS implements Route {

	Logger log = LogManager.getLogger(CreateReportDirWS.class);
	private JsonArray errorArray;
	private boolean isTest;
	
	public CreateReportDirWS(boolean test) {
		this.isTest = test;
	}
	
	public CreateReportDirWS() {}
	
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
		errorArray = new JsonArray();
		String reportdirs = request.body();
		JsonArray dirArray = extractBody(reportdirs);
		if (dirArray == null) return badRequest(response, "Bad format or empty body.");
		
		ConfigHandler cHandler = ConfigHandler.getInstance(isTest);
		boolean error = handleRequest(dirArray, cHandler);
		if (!error) return true;
		else {
			response = error(response);
			return new Gson().toJson(errorArray);
		}
		
	}

	private JsonArray extractBody(String reportdirs) {
		try {
			JsonArray dirArray = new Gson().fromJson(reportdirs, JsonArray.class);
			return dirArray;
		} catch (Exception e) {
			return null;
		}
	}


	private Response error(Response response) {
		response.header("Content-Type", "text/json; charset=UTF-8");
		response.status(400);
		return response;
	}


	private Response badRequest(Response response, String string) {
		try {
			response.raw().sendError(400, string);
			response.status(400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}


	private boolean handleRequest(JsonArray dirArray, ConfigHandler cHandler) {
		boolean error = false;
		for (JsonElement path : dirArray) {
			boolean directoryExists = FileUtil.directoryExists(path.getAsString());
			if (directoryExists) {
				boolean saveReportPath = cHandler.saveReportPath(path.getAsString());
				if (!saveReportPath) {
					appendError("You can not add duplicates: ", path.getAsString());
					error = true;
				}
			}
			else {
				appendError("path did not exist: ", path.getAsString());
				error = true;
			}
		}
		return error;
	}


	private void appendError(String errorType, String path) {
		errorArray.add(new JsonPrimitive(errorType+path));
	}

}
