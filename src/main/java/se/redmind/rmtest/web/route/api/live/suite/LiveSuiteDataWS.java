package se.redmind.rmtest.web.route.api.live.suite;


import se.redmind.rmtest.liveteststream.LiveStreamContainer;
import spark.Request;
import spark.Response;
import spark.Route;

public class LiveSuiteDataWS extends Route {

	public LiveSuiteDataWS(String path) {
		super(path);
	}
	
	
	/**
	 * @api {get} /live/:UUID
	 * @apiName getSuiteRun
	 * @apiGroup Live
	 * 
	 * @apiSuccess {json} returns a suite, with current status etc and tests with current results. 
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		LiveStreamContainer lsContainer = LiveStreamContainer.instance();
		String UUID = request.params("UUID");
		try {
			return lsContainer.getTestrunFromUUID(UUID).getSuite();
		} catch (Exception e) {
			return null;
		}
	}

}
