package se.redmind.rmtest.web.route.api.getclasses;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetClassesWS extends Route {

	public GetClassesWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		int suiteid = (int) request.attribute("suiteid");
		return new GetClassesDAO().getClasses(suiteid);
	}

	
	
	
}
