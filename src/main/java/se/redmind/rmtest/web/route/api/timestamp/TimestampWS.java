package se.redmind.rmtest.web.route.api.timestamp;

import spark.Request;
import spark.Response;
import spark.Route;

public class TimestampWS extends Route {

	public TimestampWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.params("timestamp");
		int timestampVal = Integer.valueOf(timestamp);
		return new TimestampStats(timestampVal).get();
	}

}
