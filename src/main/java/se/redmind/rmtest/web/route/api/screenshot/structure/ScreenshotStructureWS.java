package se.redmind.rmtest.web.route.api.screenshot.structure;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class ScreenshotStructureWS extends Route {

	public ScreenshotStructureWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /screenshot/structure
	 * @apiName Structure
	 * @apiGroup Screenshot
	 * @apiParam {Number} timestamp timestamp the screenshot should be related to.
	 * @apiParam {Number} classid classid the screenshot should be related to.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *		name: "testGoogle",
	 *		testcases: [
	 *			{
	 *				device: "UNKNOWN",
	 *				result: "passed",
	 *				browser: "chrome",
	 *				screenshots: [
	 *					"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle-20150313164647[OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN].png"
	 *				]
	 *			},
	 *			{
	 *				device: "UNKNOWN",
	 *				result: "passed",
	 *				browser: "firefox",
	 *				screenshots: [
	 *					"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle-20150313164647[OSX_UNKNOWN_UNKNOWN_firefox_UNKNOWN].png"
	 *				]
	 *			}
	 *		]
	 *	},
	 *	{
	 *		name: "testGoogle2",
	 *		testcases: [
	 *			{
	 *				device: "UNKNOWN",
	 *				result: "passed",
	 *				browser: "chrome",
	 *				screenshots: [
	 *					"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle2-20150513164647[OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN].png"
	 *				]
	 *			},
	 *			{
	 *				device: "UNKNOWN",
	 *				result: "passed",
	 *				browser: "firefox",
	 *				screenshots: [
	 *					"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle2-20150513164647[OSX_UNKNOWN_UNKNOWN_firefox_UNKNOWN].png"
	 *				]
	 *			}
	 *		]
	 *	}
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.queryParams("timestamp");
		String classid = request.queryParams("classid");
		ScreenshotStructureDAO dao = new ScreenshotStructureDAO();
		JsonArray array = dao.getStructure(classid, timestamp);
		return new Gson().toJson(array);
	}

}
