package se.redmind.rmtest.liveteststream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LiveStream implements Runnable {
	
	Logger log = LogManager.getLogger(LiveStream.class);
	
	private Socket connection;
	private boolean listen;
	private final String CLOSE = "!close@", SUITE = "suite@", TEST = "test@", RESULT = "result@";
	private Gson gson;

	private String UUID;

	private String timestamp;

	public LiveStream(Socket connection) {
		this.connection = connection;
		this.listen = true;
		this.gson = new Gson();
	}

	@Override
	public void run() {
		try {
			while (listen) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			if (in.ready()) {
				String request = null;
				while ((request = in.readLine()) != null) {
					handleRequest(request);
				}
			}
			}
			Thread.sleep(50);
			log.info("Ending session: "+connection);
			connection.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void handleRequest(String request) {
		if (request.startsWith("!close@")) {
			listen = false;
		}
		else if (request.startsWith("suite@")) {
			String suite = extractRequest(request);
			handleSuite(suite);
		}
		else if (request.startsWith("!suiteFinished@")){
			LiveStreamContainer.instance().finishSuite(UUID);
		}
		else if (request.startsWith("test@")){
			String testString = extractRequest(request);
			LiveStreamContainer.instance().addTestResult(UUID, gson.fromJson(testString, JsonObject.class));
		}
		else if (request.startsWith("testStart@")){
			String testID = extractRequest(request);
			LiveStreamContainer.instance().startTest(UUID, testID);
		}
	}

	private void handleSuite(String suite) {
		JsonObject suiteJson = gson.fromJson(suite, JsonObject.class);
		UUID = suiteJson.get("UUID").getAsString();
		timestamp = suiteJson.get("timestamp").getAsString();
		LiveStreamContainer.instance().addSuite(UUID, suiteJson);
	}

	private String extractRequest(String request) {
		int start = request.indexOf('@');
		String suite = request.substring(start+1, request.length());
		return suite;
	}

}
