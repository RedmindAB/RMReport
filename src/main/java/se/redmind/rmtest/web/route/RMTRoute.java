package se.redmind.rmtest.web.route;

import static spark.Spark.*;
import se.redmind.rmtest.web.properties.PropertiesReader;
import se.redmind.rmtest.web.route.api.ApiRouter;
import se.redmind.rmtest.web.route.api.cache.AddCacheFilter;
import spark.Request;
import spark.Response;
import spark.Route;

public class RMTRoute {

	public RMTRoute() {
		PropertiesReader props = new PropertiesReader();
		int port = props.getPort();
		if (port != 0) {
			setPort(port);
		}
		init();
		new ApiRouter();
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
