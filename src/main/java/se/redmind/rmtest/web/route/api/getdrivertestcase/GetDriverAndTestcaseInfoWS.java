package se.redmind.rmtest.web.route.api.getdrivertestcase;

import spark.Request;
import spark.Response;
import spark.Route;
import se.redmind.rmtest.web.route.api.getdrivertestcase.GetDriverAndTestcaseDAO;

public class GetDriverAndTestcaseInfoWS extends Route {

	
	
	public GetDriverAndTestcaseInfoWS(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object handle(Request request, Response response) {
		int testcaseId = Integer.valueOf(request.queryParams("id"));
		String driver = (String)request.queryParams("driver");
		return new GetDriverAndTestcaseDAO().getDriverAndTestcase(driver, testcaseId);
	}

}
