package se.redmind.rmtest.web.route.api.suite.bytimestamp;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuiteByTimestampWS extends Route {

	public GetSuiteByTimestampWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /suite/bytimestamp
	 * @apiName Get latest by timestamp
	 * @apiGroup Suite
	 * @apiParam {Number} suiteid ID of the suite you want to fetch
	 * @apiParam {Number} timestamp ID of the suite you want to fetch
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		id: 1,
	 *		name: "se.redmind.rmtest.selenium.example.GoogleExample",
	 *		time: 20.255000114440918,
	 *		result: "passed",
	 *		testcases: [
	 *			{
	 *				id: 1,
	 *				name: "testGoogle2",
	 *				result: "passed",
	 *				time: 9.10099983215332
	 *			},
	 *			{
	 *				id: 2,
	 *				name: "testGoogle",
	 *				result: "passed",
	 *				time: 11.154000282287598
	 *			}
	 *		],
	 *	}
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		try {
			int suite_id = Integer.valueOf(request.queryParams("suiteid"));
			String timestamp = request.queryParams("timestamp");
			return new GetSuiteByTimestampDAO().getSuiteByTimestamp(suite_id, timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hej";
	}

}
