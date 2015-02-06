package se.redmind.rmtest.web.route.api.driver;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetDriverByTestcaseWS extends Route {

	
	
	public GetDriverByTestcaseWS(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handle(Request request, Response response) {
		int testcaseId = Integer.valueOf(request.queryParams("id"));
		return new GetDriverByTestcaseDAO().getDriverByTestcaseId(testcaseId);
	}

}
