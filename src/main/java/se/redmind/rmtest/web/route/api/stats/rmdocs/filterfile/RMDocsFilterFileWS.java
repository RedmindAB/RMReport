package se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile;


import java.io.IOException;

import spark.Request;
import spark.Response;
import spark.Route;

public class RMDocsFilterFileWS extends Route {

	RMDocsFilterFileDAO dao;
	
	public RMDocsFilterFileWS(String path) {
		super(path);
		dao = new RMDocsFilterFileDAO();
	}

	@Override
	public Object handle(Request request, Response response) {
		Integer suiteid = Integer.valueOf(request.params("suiteid"));
		int threshhold = getThreshhold(request);
		Integer limit = getLimit(request);
		boolean isFile = isFile(request);
		String res = dao.getResultsAsText(suiteid, limit, threshhold); 
		if (isFile) {
			response.type("application/octet-binary");
			return res; 
		}
		else{
			response.type("text/plain");
			return res;
		}
	}

	private boolean isFile(Request request) {
		return request.queryParams("file") != null;
	}

	private int getLimit(Request request) {
		try {
			return Integer.valueOf(request.queryParams("limit"));
		} catch (Exception e) {
			return 20;
		}
	}
	
	private int getThreshhold(Request request) {
		try {
			return Integer.valueOf(request.queryParams("threshold"));
		} catch (Exception e) {
			return 100;
		}
	}
	
}
