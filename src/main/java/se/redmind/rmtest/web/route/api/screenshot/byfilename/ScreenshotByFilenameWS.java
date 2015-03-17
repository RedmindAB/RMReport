package se.redmind.rmtest.web.route.api.screenshot.byfilename;

import java.io.IOException;

import spark.Request;
import spark.Response;
import spark.Route;

public class ScreenshotByFilenameWS extends Route {

	ScreenshotHandler screenshotHandler;
	
	public ScreenshotByFilenameWS(String path) {
		super(path);
		screenshotHandler = new ScreenshotHandler();
	}
	
	/**
	 * @api {get} /screenshot/byfilename
	 * @apiName ScreenshotByFilename
	 * @apiGroup Screenshot
	 * @apiParam {Number} timestamp timestamp the screenshot should be related to.
	 * @apiParam {String} filename the name of the file.
	 * 
	 * @apiSuccess {Image} image the image from the timestamp and filename is returned.
	 * 
	 */
	@Override
	public Object handle(Request request, Response response) {
		String timestamp = request.queryParams("timestamp");
		String filename = request.queryParams("filename");
		byte[] imageAsByteArray = screenshotHandler.getImageAsByteArray(timestamp, filename);
		response.type("image/png");
		try {
			response.raw().getOutputStream().write(imageAsByteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
