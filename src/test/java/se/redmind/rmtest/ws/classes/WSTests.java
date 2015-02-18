package se.redmind.rmtest.ws.classes;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import se.redmind.rmtest.web.route.api.suite.byid.GetLatestSuiteDAO;


public class WSTests {

	@Rule
	public Timeout timeout = new Timeout(250);
	
	@Test
	public void getLatestSuite() {
		String latestSuite = new GetLatestSuiteDAO().getLatestSuite(1);
		assertTrue(latestSuite.length() > 0);
		assertTrue(latestSuite != null);
	}
	
}
