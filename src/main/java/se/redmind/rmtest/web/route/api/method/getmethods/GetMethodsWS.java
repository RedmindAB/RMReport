package se.redmind.rmtest.web.route.api.method.getmethods;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetMethodsWS implements Route {


	/**
	 * @api {get} /method/getmethods
	 * @apiName Get Methods
	 * @apiGroup Method
	 * 
	 * @apiParam {Number} classid ID of the class.
	 * 
	 *@apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		id: 1,
	 *		name: "testGoogle2"
	 *	},
	 *	{
	 *		id: 2,
	 *		name: "testGoogle"
	 *	},
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		int classid = Integer.valueOf(request.queryParams("classid"));
		return new GetMethodsDAO().getMethods(classid);
	}

}
