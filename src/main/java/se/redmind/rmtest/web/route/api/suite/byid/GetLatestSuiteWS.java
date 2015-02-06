package se.redmind.rmtest.web.route.api.suite.byid;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetLatestSuiteWS extends Route {

	public GetLatestSuiteWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		try {
			int suite_id = Integer.valueOf(request.queryParams("suiteid"));
			return new GetLatestSuiteDAO().getLatestSuite(suite_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "NOPE";
	}

}
