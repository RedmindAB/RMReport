package se.redmind.rmtest.web.route.api;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import se.redmind.rmtest.web.route.api.admin.config.GetConfigWS;
import se.redmind.rmtest.web.route.api.admin.doctor.RunDoctorWS;
import se.redmind.rmtest.web.route.api.admin.port.ChangePortWS;
import se.redmind.rmtest.web.route.api.admin.reportdir.CreateReportDirWS;
import se.redmind.rmtest.web.route.api.admin.reportdir.DeleteReportDir;
import se.redmind.rmtest.web.route.api.admin.reportdir.DeleteReportDirsWS;
import se.redmind.rmtest.web.route.api.admin.reportdir.UpdateReportDirWS;
import se.redmind.rmtest.web.route.api.classes.getclasses.GetClassesWS;
import se.redmind.rmtest.web.route.api.classes.passfail.PassFailClassWS;
import se.redmind.rmtest.web.route.api.device.getdevices.GetDevicesAMonthAgoWS;
import se.redmind.rmtest.web.route.api.driver.GetDriverByTestcaseWS;
import se.redmind.rmtest.web.route.api.filter.ApiBeforeFilter;
import se.redmind.rmtest.web.route.api.live.change.LiveSuiteChange;
import se.redmind.rmtest.web.route.api.live.suite.LiveSuiteDataWS;
import se.redmind.rmtest.web.route.api.live.testrunlist.TestRunListWS;
import se.redmind.rmtest.web.route.api.longpoll.change.CheckForChangeWS;
import se.redmind.rmtest.web.route.api.method.getmethods.GetMethodsWS;
import se.redmind.rmtest.web.route.api.screenshot.byfilename.ScreenshotByFilenameWS;
import se.redmind.rmtest.web.route.api.screenshot.structure.ScreenshotStructureWS;
import se.redmind.rmtest.web.route.api.seleniumgrid.SeleniumGridWS;
import se.redmind.rmtest.web.route.api.stats.devicefail.DeviceStatsFailWS;
import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsWS;
import se.redmind.rmtest.web.route.api.stats.graph.tooltip.GraphTooltipWS;
import se.redmind.rmtest.web.route.api.stats.graphdata.GetGraphDataWS;
import se.redmind.rmtest.web.route.api.stats.methodfail.MethodFailWS;
import se.redmind.rmtest.web.route.api.stats.platform.DeviceStatsPlatform;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteWS;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampWS;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;
import se.redmind.rmtest.web.route.api.suite.parameters.SuiteParametersDAO;
import se.redmind.rmtest.web.route.api.suite.parameters.SuiteParametersWS;
import se.redmind.rmtest.web.route.api.suite.syso.GetSuiteSysosWS;
import se.redmind.rmtest.web.route.api.timestamp.TimestampWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		
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
		put(new UpdateReportDirWS("/api/admin/reportdir"));
		put(new ChangePortWS("/api/admin/port/:portnum"));
		post(new CreateReportDirWS("/api/admin/reportdir"));
		
		//not added to apidocs
		delete(new DeleteReportDir("/api/admin/reportdir"));
		delete(new DeleteReportDirsWS("/api/admin/reportdir"));
		get(new GetConfigWS("/api/admin/config"));
		get(new CheckForChangeWS("/api/long"));
		get(new RunDoctorWS("/api/admin/doctor"));
		get(new TimestampWS("/api/timestamp/:timestamp"));
		get(new SuiteParametersWS("/api/suite/parameters/:suiteid/:timestamp"));
		//api/stats/device/fail/:deviceid/:suiteid?limit=x (Return the device fails based on the last timestamps down to the limit)
		get(new DeviceStatsFailWS("/api/stats/device/fail/:suiteid/:osname"));
		get(new DeviceStatsPlatform("/api/stats/platform/:suiteid"));
		get(new MethodFailWS("/api/stats/methodfail/:suiteid"));
		get(new GraphTooltipWS("/api/stats/devicerange/:suiteid/:timestamp"));
		get(new TestRunListWS("/api/live"));
		get(new LiveSuiteDataWS("/api/live/:UUID"));
		get(new LiveSuiteChange("/api/live/:UUID/:lastchange"));
	}
	
	
}
