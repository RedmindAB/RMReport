package se.redmind.rmtest.web.route.api.filter;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spark.Filter;
import spark.Request;
import spark.Response;

public class ApiBeforeFilter extends Filter {

	Logger log = LogManager.getLogger("API");
	
	@Override
	public void handle(Request request, Response response) {
		if (request.pathInfo().startsWith("/api/")) {
			log(request);
		}
	}

	private void log(Request request) {
		HttpServletRequest rawRequest = request.raw();
		String queryString = getQuery(request);
		if (queryString != null) {
			String contextPath = request.pathInfo();
			String type = rawRequest.getMethod();
			String id = rawRequest.getSession().getId();
			log.debug("session id: {} type: {} - {}{}", id, type, contextPath, queryString);
		}
	}

	private String getQuery(Request request) {
		
		String type = request.raw().getMethod();
		String query = "";
		switch (type.toUpperCase()) {
		case "GET":
			String queryString = request.raw().getQueryString();
			if (queryString != null) {
				query = "?"+queryString;
			}
			break;
		case "POST":
			return null;
		default:
			break;
		}
		return query;
	}

}
