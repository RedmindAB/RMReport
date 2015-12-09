package se.redmind.rmtest.web.route.api.admin.port;


import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class ChangePortWS implements Route {

	
	/**
	 * @api {put} /admin/port/:portnum
	 * @apiName ScreenshotByFilename
	 * @apiGroup Admin
	 * @apiParam {Number} portnum port number that should be set as default port.
	 * 
	 * @apiSuccess {boolean} boolean
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		ConfigHandler cHandler = ConfigHandler.getInstance();
		cHandler.savePoint();
		try {
			String port = request.params("portnum");
			cHandler.savePort(Integer.valueOf(port));
			return true;
		} catch (Exception e) {
			cHandler.rollback();
			return false;
		}
	}

}
