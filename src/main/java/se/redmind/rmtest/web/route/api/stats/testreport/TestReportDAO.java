package se.redmind.rmtest.web.route.api.stats.testreport;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import se.redmind.rmtest.db.DBBridge;

public class TestReportDAO extends DBBridge {

	private HashMap<String, String> parameters;
	private String timestamp;
	private int suite;
	private boolean deadEnd = false;

	public TestReportDAO(HashMap<String, String> parameters, String timestamp, int suite) {
		this.parameters = parameters;
		this.timestamp = timestamp;
		this.suite = suite;
	}
	
	public void init(){
		if (!isTimestampSet() && !isParametersSet()) {
			this.deadEnd = true;
		}
		else {
			if(isParametersSet()){
				setSuiteAndTimestampFromParameters();
			}
		}
	}
	
	private void setSuiteAndTimestampFromParameters() {
		Set<Entry<String, String>> entrySet = parameters.entrySet();
		String sql = "SELECT suite_id, timestamp FROM parameters WHERE ";
		for (Entry<String, String> param : entrySet) {
			String key = param.getKey();
			String value = param.getValue();
		}
	}

	private boolean isParametersSet() {
		return parameters.size() > 0;
	}

	private boolean isTimestampSet(){
		return timestamp != null;
	}
	
	public boolean deadEnd(){
		return deadEnd;
	}
	
}
