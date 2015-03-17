package se.redmind.rmtest.web.route.api.suite.getsuites;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuitesWS extends Route{

	public GetSuitesWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /suite/getsuites
	 * @apiName GetSuites
	 * @apiGroup Suite
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		name: "Sample1",
	 *		id: 1
	 *	},
	 *	{
	 *		name: "Sample2",
	 *		id: 2
	 *	}
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		return new GetSuitesDAO().getSuites();
	}
	
}
