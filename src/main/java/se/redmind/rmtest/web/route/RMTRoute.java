package se.redmind.rmtest.web.route;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.ApiRouter;
import se.redmind.rmtest.web.route.api.cache.AddCacheFilter;
import spark.Request;
import spark.Response;
import spark.Route;

public class RMTRoute {

	public RMTRoute() {
		init();
		new ApiRouter();
	}
	
	private void init(){
		staticFileLocation("/static");
		after(new AddCacheFilter());
		get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				return null;
			}
		});
	}
	
}
