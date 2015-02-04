package se.redmind.rmtest.web.route.api.getreport;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetReportWS extends Route {

	protected GetReportWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String report = (String) request.attribute("listTime");
		return new GetReportDAO();
	}

}
