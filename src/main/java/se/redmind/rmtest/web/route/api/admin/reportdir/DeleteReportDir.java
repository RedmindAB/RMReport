package se.redmind.rmtest.web.route.api.admin.reportdir;

import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteReportDir extends Route {

	public DeleteReportDir(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		ConfigHandler cHandler = ConfigHandler.getInstance();
		String delPath = request.body();
		cHandler.deleteReportPath(delPath);
		return true;
	}

}
