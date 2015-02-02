package se.redmind.rmtest.web.route.api.getsuites;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuitesWS extends Route{

	public GetSuitesWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		return new GetSuitesDAO().getSuites();
	}
	
}
