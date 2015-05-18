package se.redmind.rmtest.web.route.api.admin.reportdir;

import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateReportDirWS extends Route {

	public UpdateReportDirWS(String path) {
		super(path);
	}

	/**
	 * @api {put} /admin/reportdir/:index
	 * @apiParam {Number} index index of the report directory to change.
	 * @apiGroup Admin
	 * @apiSuccess {boolean} return a boolean of success or fail.
	 * @apiDescription set the body of the request to the path that should replace the old path.
	 */
	@Override
	public Object handle(Request request, Response response) {
		String path = request.body();
		String index = request.params("index");
		ConfigHandler cHandler = ConfigHandler.getInstance();
		cHandler.updateReportPath(Integer.valueOf(index), path);
		return null;
	}

}
