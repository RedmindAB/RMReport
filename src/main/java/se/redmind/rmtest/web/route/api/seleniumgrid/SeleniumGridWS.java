package se.redmind.rmtest.web.route.api.seleniumgrid;

import spark.Request;
import spark.Response;
import spark.Route;

public class SeleniumGridWS extends Route{

	public SeleniumGridWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		SeleniumGridDAO sgDao = new SeleniumGridDAO();
		String selenumGridData = sgDao.getSelenumGridData();
		return selenumGridData;
	}

}
