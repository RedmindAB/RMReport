package se.redmind.rmtest.web.route.longpoll;

import spark.Request;
import spark.Response;

public class RequestResponseContainer {

	private Request request;
	private Response response;
	
	public RequestResponseContainer(Request request, Response response) {
		this.request = request;
		this.response = response;
	}
	
	public Request getRequest(){
		return this.request;
	}
	
	public Response getResponse(){
		return this.response;
	}
	
	
}
