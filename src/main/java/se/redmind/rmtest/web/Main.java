package se.redmind.rmtest.web;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
	
	public static void main(String[] args) {
		staticFileLocation("/static");
		get(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello world";
			}
		});
	}

}
