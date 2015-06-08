package se.redmind.rmtest.web.route.api;

import se.redmind.rmtest.web.route.api.util.timestamp.TimestampUtil;
import spark.Request;
import spark.Route;

public abstract class RouteUtil extends Route{

	
	protected String errorMessage;
	protected boolean fail;
	
	protected RouteUtil(String path) {
		super(path);
		this.errorMessage = "";
		this.fail = false;
	}

	protected int extractNumber(Request req, String paramValue){
		try {
			return Integer.parseInt(req.params(paramValue));
		} catch (Exception e) {
			this.fail = true;
			errorMessage+=paramValue+" cannot be "+req.params(paramValue)+", must be an int\n";
		}
		return -1;
	}
	
	protected int getLimit(Request request, int defaultLimit){
		String queryParams = request.queryParams("limit");
		try {
			int limit = Integer.parseInt(queryParams);
			if (limit < 0) {
				return defaultLimit;
			}
			return limit;
		} catch (Exception e) {
			return defaultLimit;
		}
	}
	
	protected long getMinTimestamp(int suiteid, int limit){
		return TimestampUtil.getInstance().getMinTimestamp(suiteid, limit);
	}
	
}
