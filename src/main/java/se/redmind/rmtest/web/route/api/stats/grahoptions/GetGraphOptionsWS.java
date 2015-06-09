package se.redmind.rmtest.web.route.api.stats.grahoptions;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetGraphOptionsWS extends Route {

	public GetGraphOptionsWS(String path) {
		super(path);
	}

	/**
	 * @api {get} /stats/options
	 * @apiName Options
	 * @apiGroup Stats
	 * @apiParam {Number} suiteid ID of the suite you want to have filter options for.
	 * @apiParam {Number} limit limit the range in timestamps the options should represent
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *{
	 *	"platforms":[
	 *		{
	 *		"osname":"OSX",
	 *		"versions":[
	 *			{
	 *			"osver":"UNKNOWN",
	 *			"osid":1
	 *			}
	 *		],
	 *		"devices":[
	 *			{
	 *			"devicename":"UNKNOWN",
	 *			"deviceid":1,
	 *			"osver":"UNKNOWN",
	 *			"osid":1
	 *			}
	 *		]
	 *	}
	 *	],
	 *	"browsers":[
	 *		{
	 *		"browsername":"firefox",
	 *		"browserid":2,
	 *		"browserver":"UNKNOWN"
	 *		},
	 *		{
	 *		"browsername":"chrome",
	 *		"browserid":1,
	 *		"browserver":"UNKNOWN"
	 *		}
	 *	]
	 *}
	 */
	@Override
	public Object handle(Request request, Response response) {
		try {
			int suiteid = Integer.valueOf(request.queryParams("suiteid"));
			int limit = Integer.valueOf(request.queryParams("limit"));
			return new GetGraphOptionsDAO().getGraphData(suiteid, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nope";
	}

}
