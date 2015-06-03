package se.redmind.rmtest.web.route.api.admin.reportdir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import se.redmind.rmtest.util.FileUtil;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateReportDirWS extends Route {

	Logger log = LogManager.getLogger(CreateReportDirWS.class);
	
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
		if (dirArray == null) return badRequest(response, "Bad format or empty body.");
		
		ConfigHandler cHandler = ConfigHandler.getInstance();
		boolean enter = false;
		enter = handleRequest(response, dirArray, cHandler, enter);
		if (enter) return true;
		else {
			response.status(400);
			return response;
		}
		
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


	private boolean handleRequest(Response response, JsonArray dirArray, ConfigHandler cHandler, boolean enter) {
		for (JsonElement path : dirArray) {
			enter = true;
			boolean directoryExists = FileUtil.directoryExists(path.getAsString());
			if (directoryExists) {
				boolean saveReportPath = cHandler.saveReportPath(path.getAsString());
				if (!saveReportPath) {
					try {
						response.raw().sendError(417, "You can not add duplicates.");
						enter = false;
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
			}
			else {
				try {
					response.raw().sendError(417, "path did not exist: "+path.getAsString());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				enter = false;
				response.status(417);
				break;
			}
		}
		return enter;
	}

}
