package se.redmind.rmtest.web.route.api.seleniumgrid;

import spark.Request;
import spark.Response;
import spark.Route;

public class SeleniumGridWS implements Route{


	@Override
	public Object handle(Request request, Response response) {
		SeleniumGridDAO sgDao = new SeleniumGridDAO();
		String selenumGridData = sgDao.getSelenumGridData();
		return selenumGridData;
	}

}
