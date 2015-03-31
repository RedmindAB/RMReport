package se.redmind.rmtest.web.route.api.stats.graphdata;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphDataWS extends Route {

	Logger log = LogManager.getLogger(GetGraphDataWS.class);
	
	public GetGraphDataWS(String path) {
		super(path);
	}

	/**
	 * @api {post} /stats/graphdata
	 * @apiName Graph Data
	 * @apiGroup Stats
	 * 
	 * @apiParamExample {json} Request-Example:
	 *[
	 *	{	
	 *		"name":'Nexus One',
	 *		"suiteid":1,
	 *		"reslimit":50,
	 *		"os":[1,2],
	 *		"devices":[1,2],
	 *		"browsers":[1,2],
	 *		"classes":[2],
	 *		"testcases":[3]
	 *},
	 *{	
	 *		"name":'iPhone 6',
	 *		"suiteid":1,
	 *		"reslimit":50,
	 *		"os":[1,2],
	 *		"devices":[1,2],
	 *		"browsers":[1,2],
	 *		"classes":[2],
	 *		"testcases":[3]
	 *	}	
	 *]
	 *
	 *@apiSuccessExample {json} Success-Response:
	 *[
	 *	{
	 *	"name":"Aggregation",
	 *	"data":[
	 *			{
	 *			"timestamp":20150313164647,
	 *			"time":13.307,
	 *			"pass":4,
	 *			"fail":0,
	 *			"error":0,
	 *			"skipped":0
	 *			},
	 *			{
	 *			"timestamp":20150313165619,
	 *			"time":14.871,
	 *			"pass":4,
	 *			"fail":0,
	 *			"error":0,
	 *			"skipped":0
	 *			},
	 *			{
	 *			"timestamp":20150316092847,
	 *			"time":20.255000000000003,
	 *			"pass":4,
	 *			"fail":0,
	 *			"error":0,
	 *			"skipped":0
	 *			}
	 *		]
	 *	}
	 *]
	 */
	@Override
	public Object handle(Request request, Response response) {
		try {
			String data = (String) request.body();
			log(request, data);
			JsonArray json = new Gson().fromJson(data, JsonArray.class);
			String res = new GetGraphDataDAO().getGraphData(json);
			return res;
		} catch (Exception e) {
			return e.getStackTrace().toString();
		}
	}

	private void log(Request request, String body) {
		HttpServletRequest rawRequest = request.raw();
		String contextPath = request.pathInfo();
		String type = rawRequest.getMethod();
		String id = rawRequest.getSession().getId();
		log.debug("session id: {} type: {} - {} data: {}", id, type, contextPath, body);
	}

}
