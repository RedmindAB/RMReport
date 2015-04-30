package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import se.redmind.rmtest.web.route.api.seleniumgrid.SeleniumGridDAO;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SeleniumGridDAOTest extends WSSetupHelper{

	
	@Ignore
	@Test
	public void getSeleniumGridTest()  {
		SeleniumGridDAO sgDao = new SeleniumGridDAO();
		String result = sgDao.getSelenumGridData();
		JsonObject expected = new Gson().fromJson(result, JsonObject.class);
		
		assertTrue(expected.isJsonObject());

	}
}
