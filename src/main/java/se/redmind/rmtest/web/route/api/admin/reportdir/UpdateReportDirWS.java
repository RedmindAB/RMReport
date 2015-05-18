package se.redmind.rmtest.web.route.api.admin.reportdir;

import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateReportDirWS extends Route {

	public UpdateReportDirWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String path = request.body();
		String index = request.params("index");
		ConfigHandler cHandler = ConfigHandler.getInstance();
		cHandler.updateReportPath(Integer.valueOf(index), path);
		return null;
	}

}
