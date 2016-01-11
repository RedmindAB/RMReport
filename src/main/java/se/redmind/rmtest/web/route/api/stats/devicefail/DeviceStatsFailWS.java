package se.redmind.rmtest.web.route.api.stats.devicefail;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class DeviceStatsFailWS extends CachedRoute {

	private boolean fail;
	
	public DeviceStatsFailWS() {
		this.fail = false;
	}


	@Override
	public JsonElement handleRequest(Response response, Request request) {
		int suiteid = extractInt(request, "suiteid");
		String os_name = request.params("osname");
		int limit = getLimit(request, 500);
		JsonArray result = new DeviceStatusFailDAO(suiteid, os_name, limit).getResult();
		return result;
	}

}
