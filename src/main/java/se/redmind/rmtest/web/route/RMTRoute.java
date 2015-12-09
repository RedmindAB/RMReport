package se.redmind.rmtest.web.route;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.setPort;
import static spark.Spark.staticFileLocation;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.route.api.ApiRouter;

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
		int port = ConfigHandler.getInstance().getPort();
		if (argPort != 0) {
			setPort(argPort);
			return;
		}
		if (port != 0) {
			setPort(port);
		}
	}
	
	private void init(){
		staticFileLocation("/static");
		get("/", (req, res) -> {
			return null;
		});
		after("/api/*", (req, res) -> {
			res.header("Content-Encoding", "gzip");
		});
		get("/api/doc", (req, res) -> {
			res.redirect("/api/doc/index.html");
			return res;
		});
	}
	
}
