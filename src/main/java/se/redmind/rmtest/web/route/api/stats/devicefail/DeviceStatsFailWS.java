package se.redmind.rmtest.web.route.api.stats.devicefail;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

@SuppressWarnings("unused")
public class DeviceStatsFailWS extends CachedRoute {

	private boolean fail;
	
	public DeviceStatsFailWS() {
		this.fail = false;
	}

	private void generateFailReponse(Response response, String errorMessage) {
		try {
			response.raw().sendError(417, errorMessage);
			response.status(417);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JsonElement handleRequest(Response response, Request request) {
		int suiteid = extractInt(request, "suiteid");
		String os_name = request.params("osname");
		int limit = getLimit(request, 500);
		if (fail) {
			generateFailReponse(response, errorMessage);
		}
		JsonArray result = new DeviceStatusFailDAO(suiteid, os_name, limit).getResult();
		return result;
	}

}
