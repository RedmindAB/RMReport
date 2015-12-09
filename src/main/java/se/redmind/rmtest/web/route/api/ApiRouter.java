package se.redmind.rmtest.web.route.api;

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
import se.redmind.rmtest.web.route.api.stats.methodpass.MethodPassWS;
import se.redmind.rmtest.web.route.api.stats.platform.DeviceStatsPlatform;
import se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile.RMDocsFilterFileWS;
import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteWS;
import se.redmind.rmtest.web.route.api.suite.bytimestamp.GetSuiteByTimestampWS;
import se.redmind.rmtest.web.route.api.suite.getsuites.GetSuitesWS;
import se.redmind.rmtest.web.route.api.suite.parameters.SuiteParametersWS;
import se.redmind.rmtest.web.route.api.suite.syso.GetSuiteSysosWS;
import se.redmind.rmtest.web.route.api.timestamp.TimestampWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		
		//added to apidoc
		get("/api/suite/getsuites",new GetSuitesWS());
		get("/api/suite/latestbyid",new GetLatestSuiteWS());
		get("/api/suite/bytimestamp",new GetSuiteByTimestampWS());
		get("/api/suite/syso",new GetSuiteSysosWS());
		get("/api/class/getclasses",new GetClassesWS());
		get("/api/class/passfail",new PassFailClassWS());
		get("/api/method/getmethods",new GetMethodsWS());
		get("/api/driver/bytestcase",new GetDriverByTestcaseWS());
		post("/api/stats/graphdata",new GetGraphDataWS());
		get("/api/stats/options",new GetGraphOptionsWS());
		get("/api/screenshot/structure",new ScreenshotStructureWS());
		get("/api/screenshot/byfilename",new ScreenshotByFilenameWS());
		get("/api/selenium/griddata",new SeleniumGridWS());
		
		//added to apidocs, but no result example added.
		get("/api/device/notrunforamonth", new GetDevicesAMonthAgoWS());
		put("/api/admin/reportdir",new UpdateReportDirWS());
		put("/api/admin/port/:portnum",new ChangePortWS());
		post("/api/admin/reportdir",new CreateReportDirWS());
		
		//not added to apidocs
		delete("/api/admin/reportdir",new DeleteReportDir());
		delete("/api/admin/reportdir",new DeleteReportDirsWS());
		get("/api/admin/config",new GetConfigWS());
		get("/api/long",new CheckForChangeWS());
		get("/api/admin/doctor",new RunDoctorWS());
		get("/api/timestamp/:timestamp",new TimestampWS());
		get("/api/suite/parameters/:suiteid/:timestamp",new SuiteParametersWS());
		//api/stats/device/fail/:deviceid/:suiteid?limit=x (Return the device fails based on the last timestamps down to the limit)
		get("/api/stats/device/fail/:suiteid/:osname",new DeviceStatsFailWS());
		get("/api/stats/platform/:suiteid",new DeviceStatsPlatform());
		get("/api/stats/methodfail/:suiteid",new MethodFailWS());
		get("/api/stats/devicerange/:suiteid/:timestamp",new GraphTooltipWS());
		get("/api/live",new TestRunListWS());
		get("/api/live/:UUID",new LiveSuiteDataWS());
		get("/api/live/:UUID/:lastchange",new LiveSuiteChange());
		
		get("/api/stats/methodpass/:suiteid",new MethodPassWS());
		get("/api/stats/rmdocs/:suiteid", new RMDocsFilterFileWS());
	}
	
	
}
