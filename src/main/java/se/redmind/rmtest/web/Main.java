package se.redmind.rmtest.web;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.RMTRoute;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
	
	public static void main(String[] args) {
		new RMTRoute();
	}

}
