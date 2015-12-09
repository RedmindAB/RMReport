package se.redmind.rmtest.web.route.api.classes.getclasses;

import com.google.gson.JsonElement;

import se.redmind.rmtest.web.route.api.CachedRoute;
import spark.Request;
import spark.Response;

public class GetClassesWS extends CachedRoute {


	/**
	 * @api {get} /class/getclasses
	 * @apiName GetClasses
	 * @apiGroup Class
	 * 
	 * @apiParam {Number} suiteid ID of the suite.
	 * 
	 *@apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		id: 1,
	 *		name: "se.redmind.rmtest.selenium.example.GoogleExample"
	 *	}
	 *]
	 */
	@Override
	public JsonElement handleRequest(Response response, Request request) {
		int suiteid = Integer.valueOf(request.queryParams("suiteid"));
		return new GetClassesDAO().getClasses(suiteid);
	}

	
	
	
}
