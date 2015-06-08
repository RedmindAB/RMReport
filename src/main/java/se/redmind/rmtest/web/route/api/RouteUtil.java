package se.redmind.rmtest.web.route.api;

import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.cache.WSCache;
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
	
	protected int extractQueryParam(Request request, String key, int defaulValue){
		String queryParams = extractParam(request, key);
		try {
			int number = Integer.parseInt(queryParams);
			return number;
		} catch (Exception e) {
			return defaulValue;
		}
	}

	private String extractParam(Request request, String key) {
		String queryParams = request.queryParams(key);
		return queryParams;
	}
	
	protected long getMinTimestamp(int suiteid, int limit){
		return TimestampUtil.getInstance().getMinTimestamp(suiteid, limit);
	}
	
	protected JsonElement getCachedObject(Request request){
		if (request.body().isEmpty()) {
			return WSCache.getInstance().get(request.pathInfo(), request.queryString());
		}
		else {
			return WSCache.getInstance().get(request.pathInfo(), request.body(), request.queryString()); 
		}
	}
	
	protected void cacheResult(Request request, JsonElement value){
		if (request.body().isEmpty()) {
			WSCache.getInstance().add(request.pathInfo(), request.queryString(), value);
		}
		else {
			WSCache.getInstance().add(request.pathInfo(), request.body(), request.queryString(), value);
		}
	}
	
}
