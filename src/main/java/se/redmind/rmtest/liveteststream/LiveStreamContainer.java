package se.redmind.rmtest.liveteststream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

public class LiveStreamContainer {

	Logger log = LogManager.getLogger(LiveStreamContainer.class);
	
	private static LiveStreamContainer instance;
	
	private ConcurrentHashMap<String, TestRun> streamMap;
	
	private LiveStreamContainer() {
		streamMap = new ConcurrentHashMap<String, TestRun>();
	}
	
	public static LiveStreamContainer instance(){
		if (instance == null) {
			instance = new LiveStreamContainer();
		}
		return instance;
	}

	public void addSuite(String uUID, JsonObject suiteJson) {
		log.info("Adding suite: "+uUID);
		if (!streamMap.contains(uUID)) {
			TestRun testRun = new TestRun(uUID, suiteJson);
			testRun.setStatus("running");
			testRun.updateTime();
			streamMap.put(uUID, testRun);
		}
	}
	
	public void finishSuite(String UUID){
		log.info("Suite finished: "+UUID);
		TestRun suite = streamMap.get(UUID);
		suite.setStatus("finished");
	}
	
	public void addTestResult(String UUID, JsonObject test){
		log.info("Test finished: "+test.get("id").getAsString());
		TestRun suite = streamMap.get(UUID);
		suite.updateTime();
		String id = test.get("id").getAsString();
		suite.finishTest(id, test);
	}
	
	public List<TestRun> getTestRuns(){
		Set<String> keySet = streamMap.keySet();
		List<TestRun> array = new ArrayList<TestRun>();
		for (String key : keySet) {
			array.add(streamMap.get(key));
		}
		return array;
	}
	
	public TestRun getTestrunFromUUID(String UUID){
		return streamMap.get(UUID);
	}

	public void startTest(String UUID, String testID) {
		log.info("Test started: "+testID);
		TestRun suite = streamMap.get(UUID);
		suite.startTest(testID);
	}
	
}
