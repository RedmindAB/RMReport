package se.redmind.rmtest.web.route.longpoll;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import se.redmind.rmtest.db.DBCon;
import spark.Request;
import spark.Response;

public class LongPollingHandler implements Runnable {

	private int timeout;
	private List<RequestResponseContainer> responseList;
	private volatile static AtomicBoolean running = new AtomicBoolean(false);
	
	public LongPollingHandler(int timeoutMills, Request request, Response response){
		this.timeout = timeoutMills;
		responseList = new CopyOnWriteArrayList<RequestResponseContainer>();
		addReponseObject(request, response);
	}

	@Override
	public void run() {
		LongPollingHandler.running.set(true);;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		createResponsBodies();
		LongPollingHandler.running.set(false);
	}
	
	public void addReponseObject(Request request ,Response response){
		responseList.add(new RequestResponseContainer(request, response));
		System.out.println(responseList.size());
	}
	
	private void createResponsBodies(){
		for (RequestResponseContainer reqResContainer : responseList) {
			String result = "\nhej\n";
			try {
				reqResContainer.getResponse().type("text/plain");
				reqResContainer.getResponse().raw().getOutputStream().write(result.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reqResContainer.getRequest().raw().getAsyncContext().complete();
		}
	}
	
	public synchronized static boolean isRunning(){
		return running.get();
	}
	
}
