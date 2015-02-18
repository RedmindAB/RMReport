package se.redmind.rmtest.web.route.api.suite.bytimestamp;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuiteByTimestampWS extends Route {

	public GetSuiteByTimestampWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		try {
			int suite_id = Integer.valueOf(request.queryParams("suiteid"));
			String timestamp = request.queryParams("timestamp");
			return new GetSuiteByTimestampDAO().getSuiteByTimestamp(suite_id, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hej";
	}

}
