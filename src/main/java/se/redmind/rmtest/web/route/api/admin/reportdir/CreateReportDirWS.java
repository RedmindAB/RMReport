package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.util.FileUtil;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class CreateReportDirWS extends Route {

	Logger log = LogManager.getLogger(CreateReportDirWS.class);
	private JsonArray errorArray;
	private boolean error;
	
	public CreateReportDirWS(String path) {
		super(path);
		errorArray = new JsonArray();
		this.error = false;
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
		if (dirArray == null) return badRequest(response, "Bad format or empty body.");
		
		ConfigHandler cHandler = ConfigHandler.getInstance();
		boolean error = handleRequest(response, dirArray, cHandler);
		if (!error) return true;
		else {
			return error(response);
		}
		
	}


	private Object error(Response response) {
		response.header("Content-Type", "application/json; charset=UTF-8");
		response.body(new Gson().toJson(errorArray));
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


	private boolean handleRequest(Response response, JsonArray dirArray, ConfigHandler cHandler) {
		for (JsonElement path : dirArray) {
			boolean directoryExists = FileUtil.directoryExists(path.getAsString());
			if (directoryExists) {
				boolean saveReportPath = cHandler.saveReportPath(path.getAsString());
				if (!saveReportPath) {
					appendError("You can not add duplicates: ", path.getAsString());
					this.error = true;
				}
			}
			else {
				appendError("path did not exist: ", path.getAsString());
				this.error = true;
				break;
			}
		}
		return this.error;
	}


	private void appendError(String errorType, String path) {
		errorArray.add(new JsonPrimitive(errorType+path));
	}

}
