package se.redmind.rmtest.web.route.api.device.getdevices;

import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetDevicesAMonthAgoWS extends Route{

	public GetDevicesAMonthAgoWS(String path) {
			super(path);
		}

		@Override
		public Object handle(Request request, Response response) {
			return new GetDevicesAMonthAgoDAO().getDeviceAmonthAgo();
		}

}
