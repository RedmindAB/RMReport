package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.classes.getclasses.GetClassesWS;
import se.redmind.rmtest.web.route.api.classes.passfail.PassFailClassWS;
import se.redmind.rmtest.web.route.api.device.getdevices.GetDevicesAMonthAgoWS;
import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseWS;
import se.redmind.rmtest.web.route.api.filter.ApiBeforeFilter;
import se.redmind.rmtest.web.route.api.longpoll.change.CheckForChangeWS;
import se.redmind.rmtest.web.route.api.method.getmethods.GetMethodsWS;
import se.redmind.rmtest.web.route.api.screenshot.byfilename.ScreenshotByFilenameWS;
import se.redmind.rmtest.web.route.api.screenshot.structure.ScreenshotStructureDAO;
import se.redmind.rmtest.web.route.api.screenshot.structure.ScreenshotStructureWS;
import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsWS;
import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteWS;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampWS;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;
import se.redmind.rmtest.web.route.api.suite.syso.GetSuiteSysosWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		
		//API-filters
		after(new ApiBeforeFilter());
		
		//added to apidoc
		get(new GetSuitesWS("/api/suite/getsuites"));
		get(new GetLatestSuiteWS("/api/suite/latestbyid"));
		get(new GetSuiteByTimestampWS("/api/suite/bytimestamp"));
		get(new GetSuiteSysosWS("/api/suite/syso"));
		get(new GetClassesWS("/api/class/getclasses"));
		get(new PassFailClassWS("/api/class/passfail"));
		get(new GetMethodsWS("/api/method/getmethods"));
		get(new GetDriverByTestcaseWS("/api/driver/bytestcase"));
		post(new GetGraphDataWS("/api/stats/graphdata"));
		get(new GetGraphOptionsWS("/api/stats/options"));
		get(new ScreenshotStructureWS("/api/screenshot/structure"));
		get(new ScreenshotByFilenameWS("/api/screenshot/byfilename"));
		
		//added to apidocs, but no result example added.
		get(new GetDevicesAMonthAgoWS("/api/device/notrunforamonth"));
		
		//not added to apidocs
		get(new CheckForChangeWS("/api/long"));
	}
	
	
}
