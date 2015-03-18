package se.redmind.rmtest.web.route.api.classes.passfail;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;

public class PassFailClassWS extends Route {

	public PassFailClassWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /class/passfail
	 * @apiName GetClasses
	 * @apiGroup Class
	 * 
	 * @apiParam {Number} classid ID of the class.
	 * @apiParam {Number} timestamp timestamp of the suite.
	 * 
	 *@apiSuccessExample {json} Success-Response:
	 *{
	 *	pass:121,
	 *	fail:4,
	 *	error:0,
	 *	skipped:1
	 *}
	 */
	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.queryParams("timestamp");
		String classid = request.queryParams("classid");
		return new Gson().toJson(new PassFailDAO().getPassFail(timestamp, classid, null));
	}

	
	
	
}
