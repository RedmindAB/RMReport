package se.redmind.rmtest.web.route.api.getlogs;



import spark.Request;
import spark.Response;
import spark.Route;

public class GetLogListWS extends Route {

	public GetLogListWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		return new GetLogListDAO().getReports();
	}

}
