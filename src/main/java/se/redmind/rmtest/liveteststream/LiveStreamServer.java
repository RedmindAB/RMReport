package se.redmind.rmtest.liveteststream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LiveStreamServer implements Runnable {

	Logger log = LogManager.getLogger(LiveStreamServer.class); 
	
	private boolean run;
	private int port;
	
	public LiveStreamServer(int port) {
		this.run = true;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
		ServerSocket serverSocket = new ServerSocket(port);
		log.info("LiveStream server running on: "+port);
			while (run) {
					Socket connection = serverSocket.accept();
					log.info("New LiveStream connection from: "+connection.getInetAddress().toString());
					Thread thread = new Thread(new LiveStream(connection));
					thread.start();
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
