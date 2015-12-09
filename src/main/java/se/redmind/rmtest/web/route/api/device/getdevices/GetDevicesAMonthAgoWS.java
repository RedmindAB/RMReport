package se.redmind.rmtest.web.route.api.device.getdevices;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetDevicesAMonthAgoWS implements Route {

	/**
	 *@api {get} /device/notrunforamonth
	 *@apiName NotRunForAMonth
	 *@apiGroup Device 
	 */
	@Override
	public Object handle(Request request, Response response) {
		return new GetDevicesAMonthAgoDAO().getDeviceAmonthAgo();
	}

}
