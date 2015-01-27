package se.redmind.rmtest.web.route.api;

import static spark.Spark.*;
import se.redmind.rmtest.web.route.api.getlogs.GetLogListWS;

public class ApiRouter {

	
	
	public ApiRouter() {
		init();
	}
	
	private void init(){
		get(new GetLogListWS("/api/log/getloglist"));
	}
	
	
}
