package se.redmind.rmtest.web.route.api.live.change;


import com.google.gson.JsonArray;

import se.redmind.rmtest.liveteststream.LiveStreamContainer;
import se.redmind.rmtest.liveteststream.TestRun;
import spark.Request;
import spark.Response;
import spark.Route;

public class LiveSuiteChange extends Route {

	public LiveSuiteChange(String path) {
		super(path);
	}

	/**
	 * @api {get} /live/:UUID/:lastChangeID
	 * @apiName getLastChange
	 * @apiGroup Live
	 * 
	 * @apiSuccess {Json} json returns a sorted list with the test results thats newer than the last change 
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		int lastChange = Integer.valueOf(request.params(":lastchange"));
		String UUID = request.params("UUID");
		LiveStreamContainer lsContainer = LiveStreamContainer.instance();
		TestRun suite = lsContainer.getTestrunFromUUID(UUID);
		JsonArray history = suite.getHistory(lastChange);
		return history;
	}

}
