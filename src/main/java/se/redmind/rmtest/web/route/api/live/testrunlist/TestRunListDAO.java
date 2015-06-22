package se.redmind.rmtest.web.route.api.live.testrunlist;

import java.util.List;

import se.redmind.rmtest.liveteststream.LiveStreamContainer;
import se.redmind.rmtest.liveteststream.TestRun;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TestRunListDAO {

	public TestRunListDAO() {
	}
	
	public JsonArray getTestRuns(){
		List<TestRun> testRuns = LiveStreamContainer.instance().getTestRuns();
		JsonArray resArray = new JsonArray();
		for (TestRun testRun : testRuns) {
			JsonObject suite = testRun.getSuite();
			JsonObject tempSuite = new JsonObject();
			tempSuite.add("suite", suite.get("suite"));
			tempSuite.add("timestamp", suite.get("timestamp"));
			tempSuite.add("UUID", suite.get("UUID"));
			tempSuite.add("lastUpdated", suite.get("lastUpdated"));
			tempSuite.add("status", suite.get("status"));
			resArray.add(tempSuite);
		}
		return resArray;
	}
	
	
}
