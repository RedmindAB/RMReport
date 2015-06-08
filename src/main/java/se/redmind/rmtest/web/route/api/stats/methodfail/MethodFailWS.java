package se.redmind.rmtest.web.route.api.stats.methodfail;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import se.redmind.rmtest.web.route.api.util.timestamp.TimestampUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class MethodFailWS extends CachedRoute {

	public MethodFailWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /stats/methodfail/:suiteid
	 * @apiName methodfail
	 * @apiGroup Stats
	 * @apiParam {Number} limit limit the amount of timestamps from the latest.
	 * @apiParam {Number} maxres limits the amount of results returned.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *	{
	 *		classname: "se.redmind.rmtest.selenium.example.RandomClass9",
	 *		testcaseName: "random2",
	 *		total: 1736,
	 *		fail: 247,
	 *		ratioFail: 14.228110599078342
	 *		},
	 *		{
	 *		classname: "se.redmind.rmtest.selenium.example.RandomClass2",
	 *		testcaseName: "random1",
	 *		total: 1736,
	 *		fail: 244,
	 *		ratioFail: 14.055299539170507
	 *	},
	 */
	@Override
	public JsonElement handleRequest(Response response, Request request) {
		int suiteid = extractNumber(request, "suiteid");
		int limit = getLimit(request, 150);
		long minTimestamp = TimestampUtil.getInstance().getMinTimestamp(suiteid, limit);
		int maxResult = extractQueryParam(request, "maxres", 5);
		JsonArray result = new MethodFailDAO(suiteid, minTimestamp, limit, maxResult).getResult();
		return result;
	}

	
}
