package se.redmind.rmtest.web.route.api.admin.port;


import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class ChangePortWS extends Route {

	public ChangePortWS(String path) {
		super(path);
	}

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
