package se.redmind.rmtest.web.route.api.getspecificsuite;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSpecificSuiteWS extends Route {

	public GetSpecificSuiteWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String timestamp = (String)request.queryParams("timestamp");
		int	id = Integer.valueOf(request.queryParams("id"));
		return new GetSpecificSuiteDAO().getSpecificSuite(timestamp, id);
	}

}
