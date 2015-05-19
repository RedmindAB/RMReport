package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.web.route.api.admin.doctor.RunDoctorWS;
import spark.Request;
import spark.Response;

public class RunRMDoctorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		RunDoctorWS ws = new RunDoctorWS("");
		Request req = mock(Request.class);
		Response res = mock(Response.class);
		Object handle = ws.handle(req, res);
		System.out.println(handle);
	}
	
}
