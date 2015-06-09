package se.redmind.rmtest.web.route.api.classes.passfail;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

public class PassFailClassWS extends Route {

	public PassFailClassWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /class/passfail
	 * @apiName PassFail
	 * @apiGroup Class
	 * 
	 * @apiParam {Number} timestamp timestamp of the suite.
	 * @apiParam {Number} classid ID of the class.
	 * @apiParam {Number} [testcaseid] ID of the method/testcase.
	 * 
	 *@apiSuccessExample {json} Success-Response:
	 *{
	 *	pass:121,
	 *	fail:4,
	 *	error:0,
	 *	skipped:1
	 *	total:126
	 *}
	 */
	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.queryParams("timestamp");
		String classid = request.queryParams("classid");
		String testcaseid = request.queryParams("testcaseid");
		return new Gson().toJson(new PassFailDAO().getPassFail(timestamp, classid, testcaseid));
	}

	
	
	
}
