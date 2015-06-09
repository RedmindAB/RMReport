package se.redmind.rmtest.web.route.api;

import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public abstract class CachedRoute extends RouteUtil{
	
	private boolean usingCache;
	
	protected CachedRoute(String path) {
		super(path);
		this.usingCache = false;
	}

	@Override
	public Object handle(Request request, Response response) {
		JsonElement cachedObject = getCachedObject(request);
		if (cachedObject != null) {
			this.usingCache = true;
			return cachedObject;
		}
		JsonElement result = handleRequest(response, request);
		cacheResult(request, result);
		return new Gson().toJson(result);
	}
	
	public abstract JsonElement handleRequest(Response response, Request request);

	public boolean isUsingCache(){
		return usingCache;
	}
	
}
