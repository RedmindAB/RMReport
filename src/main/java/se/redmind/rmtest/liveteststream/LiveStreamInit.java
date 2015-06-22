package se.redmind.rmtest.liveteststream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LiveStreamInit {
	
	Logger log = LogManager.getLogger(LiveStreamInit.class);
	
	private int port;
	private LiveStreamServer server;
	
	public LiveStreamInit(int port) {
		this.port = port;
	}
	
	public void init(){
		log.info("Init the LiveStream Server on port: "+port);
		this.server = new LiveStreamServer(port);
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
	
}
