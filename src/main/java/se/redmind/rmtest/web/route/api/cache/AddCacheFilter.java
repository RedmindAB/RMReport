package se.redmind.rmtest.web.route.api.cache;

import spark.Filter;
import spark.Request;
import spark.Response;

public class AddCacheFilter extends Filter {

	@Override
	public void handle(Request request, Response response) {
		boolean isApiRequest = request.pathInfo().startsWith("/api");
		if (isApiRequest) {
			String queryParams = getQueryString(request);
			System.out.println(queryParams);
			if (queryParams != null && queryParams.length() > 0) {
				System.out.println(response.body());
			}
		}
	}

	private String getQueryString(Request request){
		String message;
		message = request.queryString();
		return message;
	}
	
}
