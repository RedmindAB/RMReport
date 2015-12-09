package se.redmind.rmtest.web.route.api.driver;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetDriverByTestcaseWS implements Route {

	
	

	/**
	 * @api {get} /driver/bytestcase
	 * @apiName Driver By Testcase
	 * @apiGroup Driver
	 * 
	 * @apiParam {Number} id id of the method/testcase.
	 * @apiParam {number} timestamp timestamp of the test run you want to get data from.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		testcasename: "testGoogle2",
	 *		devicename: "UNKNOWN",
	 *		osname: "OSX",
	 *		osversion: "UNKNOWN",
	 *		browsername: "firefox",
	 *		browserversion: "UNKNOWN",
	 *		timetorun: "8.788",
	 *		result: "passed",
	 *		message: "{message}"
	 *	},
	 *	{
	 *		testcasename: "testGoogle2",
	 *		devicename: "UNKNOWN",
	 *		osname: "OSX",
	 *		osversion: "UNKNOWN",
	 *		browsername: "chrome",
	 *		browserversion: "UNKNOWN",
	 *		timetorun: "0.313",
	 *		result: "passed",
	 *		message: "{message}"
	 *	}
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		int testcaseId = Integer.valueOf(request.queryParams("id"));
		String timeStamp = request.queryParams("timestamp");
		return new GetDriverByTestcaseDAO().getDriverByTestcaseId(testcaseId, timeStamp);
	}

}
