package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.classes.getclasses.GetClassesWS;
import se.redmind.rmtest.web.route.api.device.getdevices.GetDevicesAMonthAgoWS;
import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseWS;
import se.redmind.rmtest.web.route.api.method.getmethods.GetMethodsWS;
import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsWS;
import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteWS;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampWS;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		
		get(new GetSuitesWS("/api/suite/getsuites"));
		// Gets the skeleton to build the structure of the page. 
		get(new GetLatestSuiteWS("/api/suite/latestbyid"));
		get(new GetSuiteByTimestampWS("/api/suite/bytimestamp"));
		get(new GetClassesWS("/api/class/getclasses"));
		get(new GetMethodsWS("/api/method/getmethods"));
		get(new GetDriverByTestcaseWS("/api/driver/bytestcase"));
		post(new GetGraphDataWS("/api/stats/graphdata"));
		get(new GetGraphOptionsWS("/api/stats/options"));
		get(new GetDevicesAMonthAgoWS("/api/device/notrunforamonth"));
	}
	
	
}
