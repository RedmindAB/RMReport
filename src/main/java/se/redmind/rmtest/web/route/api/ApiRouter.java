package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.getclasses.GetClassesWS;
import se.redmind.rmtest.web.route.api.getlogs.GetLogListWS;
import se.redmind.rmtest.web.route.api.getsuites.GetSuitesWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		get(new GetLogListWS("/api/log/getloglist"));
		get(new GetSuitesWS("/api/suite/getsuites"));
		get(new GetClassesWS("/api/class/getclasses"));
	}
	
	
}
