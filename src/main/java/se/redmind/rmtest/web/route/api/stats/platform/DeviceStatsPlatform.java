package se.redmind.rmtest.web.route.api.stats.platform;

import se.redmind.rmtest.web.route.api.RouteUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeviceStatsPlatform extends RouteUtil {

	public DeviceStatsPlatform(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String sPlatform = request.params("platform");
		String sSuiteid = request.params("suiteid");
		String limit = request.queryParams("limit");
		PlatformStatsDAO platformStatsDAO = new PlatformStatsDAO();
		return null;
	}
	
	

}
