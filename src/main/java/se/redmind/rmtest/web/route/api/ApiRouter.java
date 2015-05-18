package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.admin.config.GetConfigWS;
import se.redmind.rmtest.web.route.api.admin.port.ChangePortWS;
import se.redmind.rmtest.web.route.api.admin.reportdir.CreateReportDirWS;
import se.redmind.rmtest.web.route.api.admin.reportdir.UpdateReportDirWS;
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
import se.redmind.rmtest.web.route.api.seleniumgrid.SeleniumGridDAO;
import se.redmind.rmtest.web.route.api.seleniumgrid.SeleniumGridWS;
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
		get(new SeleniumGridWS("/api/selenium/griddata"));
		//added to apidocs, but no result example added.
		get(new GetDevicesAMonthAgoWS("/api/device/notrunforamonth"));
		put(new UpdateReportDirWS("/api/admin/reportdir/:index"));
		put(new ChangePortWS("/api/admin/port/:portnum"));
		
		//not added to apidocs
		post(new CreateReportDirWS("/api/admin/reportdir"));
		get(new GetConfigWS("/api/admin/config"));
		get(new CheckForChangeWS("/api/long"));
	}
	
	
}
