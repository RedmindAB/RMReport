package se.redmind.rmtest.web.route.api.stats.testreport;

import java.util.ArrayList;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class TestReportWS implements TemplateViewRoute {

	@Override
	public ModelAndView handle(Request request, Response response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int suiteid = getSuite(request);
		HashMap<String, String> parameters = getParameters(request);
		String timestamp = getTimestamp(request);
		ArrayList<Test> arrayList = new ArrayList<Test>();
		for (int i = 0; i < 160; i++) {
			arrayList.add(new Test("class_"+i, "test_"+i, "pass"));
		}
		map.put("tests", arrayList);
		map.put("ip", request.url());
		map.put("parameters", parameters);
		return new ModelAndView(map, "testreport.html");
	}

	private int getSuite(Request request){
		try {
			return Integer.valueOf(request.params("suite"));
		} catch (Exception e) {
			return -1;
		}
		
	}
	
	private HashMap<String, String> getParameters(Request request){
		String[] parameters = request.queryParamsValues("parameter");
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		for (int i = 0; i < parameters.length; i++) {
			try {
				String[] parameter = parameters[i].split("=");
				parametersMap.put(parameter[0], parameter[1]);
			} catch (Exception e) {
			}
		}
		return parametersMap;
	}
	
	private String getTimestamp(Request request){
		return request.queryParams("timestamp");
	}

}
