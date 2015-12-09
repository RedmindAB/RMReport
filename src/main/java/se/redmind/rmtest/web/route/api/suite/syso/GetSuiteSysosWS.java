package se.redmind.rmtest.web.route.api.suite.syso;

import se.redmind.rmtest.web.route.api.ErrorResponse;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetSuiteSysosWS implements Route {

	private String basedir;
	
	public GetSuiteSysosWS() {
	}
	
	public GetSuiteSysosWS(String path, String basedir) {
		this.basedir = basedir;
	}

	/**
	 * @api {get} /suite/syso
	 * @apiName GetSysos
	 * @apiGroup Suite
	 * @apiParam {Number} timestamp timestamp of the text you want to retrieve.
	 * @apiParam {Number} suiteid suite ID of the text you want to retrieve.
	 * 
	 * @apiSuccessExample {text} Success-Response:
	 * Description of driver is: OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN
	 * Number of treads executing in parrallel: 2
	 * This is a RemoteWebDriver Started driver: 
	 * etc... etc...
	 */
	@Override
	public Object handle(Request request, Response response) {
		String result;
		try {
			long timestamp = Long.valueOf(request.queryParams("timestamp"));
			Integer suiteid = Integer.valueOf(request.queryParams("suiteid"));
			
			GetSuiteSysosDAO getSuiteSysosDAO;
			if (basedir != null) getSuiteSysosDAO = new GetSuiteSysosDAO();
			else getSuiteSysosDAO = new GetSuiteSysosDAO(basedir);
			result = getSuiteSysosDAO.getSysos(timestamp, suiteid);
		} catch (Exception e) {
			return new ErrorResponse(e.getMessage(), GetSuiteSysosWS.class).toString();
		}
		return result;
	}

}
