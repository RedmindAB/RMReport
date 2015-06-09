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
		int suiteid = extractInt(request, "suiteid");
		int limit = getLimit(request, 500);
		PlatformStatsDAO platformStatsDAO = new PlatformStatsDAO(suiteid,limit);
		return platformStatsDAO.getResult();
	}
	
	

}
