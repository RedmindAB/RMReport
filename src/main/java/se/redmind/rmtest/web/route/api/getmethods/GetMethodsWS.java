package se.redmind.rmtest.web.route.api.getmethods;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetMethodsWS extends Route {

	public GetMethodsWS(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handle(Request request, Response response) {
		int classid = Integer.valueOf(request.queryParams("classid"));
		return new GetMethodsDAO().getMethods(classid);
	}

}
