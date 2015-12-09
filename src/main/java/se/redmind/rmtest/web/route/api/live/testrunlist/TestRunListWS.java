package se.redmind.rmtest.web.route.api.live.testrunlist;

import spark.Request;
import spark.Response;
import spark.Route;

public class TestRunListWS implements Route{

	
	
	/**
	 * @api {get} /live
	 * @apiName getLiveRuns
	 * @apiGroup Live
	 * 
	 * @apiSuccessExample {json} Success-Reponse:
	 *	[
	 *		{
	 *		suite: "test.java.se.redmind.rmtest.selenium.example.GoogleTestsRMR",
	 *		timestamp: "20150622162733",
	 *		UUID: "23d2a51b-0129-4a83-83c9-99c3d87743fe",
	 *		lastUpdated: 1434983273305,
	 *		status: "finished"
	 *		}
	 *	]
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		TestRunListDAO dao = new TestRunListDAO();
		return dao.getTestRuns();
	}

}
