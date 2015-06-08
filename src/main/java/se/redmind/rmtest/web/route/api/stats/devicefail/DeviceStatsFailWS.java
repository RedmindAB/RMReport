package se.redmind.rmtest.web.route.api.stats.devicefail;

import java.io.IOException;

import com.google.gson.Gson;

import se.redmind.rmtest.web.route.api.RouteUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeviceStatsFailWS extends RouteUtil {

	private boolean fail;
	
	public DeviceStatsFailWS(String path) {
		super(path);
		this.fail = false;
	}

	@Override
	public Object handle(Request request, Response response) {
		int suiteid = extractNumber(request, "suiteid");
		String os_name = request.params("osname");
		int limit = getLimit(request, 500);
		if (fail) {
			return generateFailReponse(response, errorMessage);
		}
		DeviceStatusFailDAO deviceStatusFailDAO = new DeviceStatusFailDAO(suiteid, os_name, limit);
		return new Gson().toJson(deviceStatusFailDAO.getResult());
	}
	
	private Response generateFailReponse(Response response, String errorMessage) {
		try {
			response.raw().sendError(417, errorMessage);
			response.status(417);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
