package se.redmind.rmtest.web.route.api.suite.data;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuiteDataWS extends Route {

	public GetSuiteDataWS(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handle(Request request, Response response) {
		int suiteid = Integer.valueOf(request.queryParams("suiteid"));
		return new GetSuiteDataDAO().getData(suiteid);
	}

}
