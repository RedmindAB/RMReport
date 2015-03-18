package se.redmind.rmtest.web.route.api.longpoll.change;

import se.redmind.rmtest.web.route.longpoll.LongPollingHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class CheckForChangeWS extends Route {
	
	LongPollingHandler lpHandler = null;
	
	public CheckForChangeWS(String path) {
		super(path);
	}

	@Override
	public Object handle(Request request, Response response) {
		return syncHandle(request, response);
	}

	private synchronized Object syncHandle(Request request, Response response) {
		request.raw().startAsync();
		boolean running = LongPollingHandler.isRunning();
		System.out.println(running);
		if (running) {
			lpHandler.addReponseObject(request, response);
		}
		else {
			lpHandler = new LongPollingHandler(1000, request, response);
			new Thread(lpHandler).start();
		}
		return response.raw();
	}

}
