package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.getclasses.GetClassesWS;
import se.redmind.rmtest.web.route.api.getlogs.GetLogListWS;
import se.redmind.rmtest.web.route.api.getmethods.GetMethodsWS;
import se.redmind.rmtest.web.route.api.getsuites.GetSuitesWS;
import se.redmind.rmtest.web.route.api.graphdata.GetGraphDataWS;
import se.redmind.rmtest.web.route.api.suite.data.GetSuiteDataWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		get(new GetLogListWS("/api/log/getloglist"));
		get(new GetSuitesWS("/api/suite/getsuites"));
		get(new GetClassesWS("/api/class/getclasses"));
		get(new GetMethodsWS("/api/method/getmethods"));
		get(new GetSuiteDataWS("/api/suite/data"));
		post(new GetGraphDataWS("/api/stat/graphdata"));
	}
	
	
}
