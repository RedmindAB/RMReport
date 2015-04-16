package se.redmind.rmtest.web.route;

import static spark.Spark.*;
import se.redmind.rmtest.web.properties.PropertiesReader;
import se.redmind.rmtest.web.route.api.ApiRouter;
import se.redmind.rmtest.web.route.api.cache.AddCacheFilter;
import spark.Request;
import spark.Response;
import spark.Route;

public class RMTRoute {

	/**
	 * Starts the webserver and runs the API setup.
	 * @param argPort - Optional. the port the application should listen to, if 0 is passed as an argument the port will be the standard 4567
	 */
	public RMTRoute(int argPort) {
		setupPort(argPort);
		init();
		new ApiRouter();
	}

	private void setupPort(int argPort) {
		PropertiesReader props = new PropertiesReader();
		int port = props.getPort();
		if (port != 0) {
			if (argPort != 0) {
				setPort(argPort);
			}
			else setPort(port);
		}
	}
	
	private void init(){
		staticFileLocation("/static");
		get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				return null;
			}
		});
		get(new Route("/api/doc") {
			@Override
			public Object handle(Request request, Response response) {
				response.redirect("/api/doc/index.html");
				return response;
			}
		});
	}
	
}
