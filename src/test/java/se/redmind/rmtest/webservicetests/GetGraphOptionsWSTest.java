package se.redmind.rmtest.webservicetests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mockito.runners.MockitoJUnitRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsWS;
import se.redmind.rmtest.web.route.api.stats.grahoptions.GraphOptionsBuilder;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

@RunWith(MockitoJUnitRunner.class)
public class GetGraphOptionsWSTest extends WSSetupHelper{
	
	@Test
	public void getMethods_true()  {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.queryParams("suiteid")).thenReturn("1");
		when(request.queryParams("limit")).thenReturn("5");
		
		GetGraphOptionsWS ws = new GetGraphOptionsWS();
		
		Object result = ws.handle(request, response);
		Gson gson = new Gson();
		JsonObject object = gson.fromJson(result.toString(), JsonObject.class);
		//assert platforms to be 3 because the testdb has 2 android, 1 osx and 1 ubuntu devices.
		JsonArray platforms = object.get("platforms").getAsJsonArray();
		assertEquals(3, platforms.size());
		//Assert browsers to be two because all devices in the testdb has same version of chrome and firefox
		JsonArray browsers = object.get("browsers").getAsJsonArray();
		assertEquals(2, browsers.size());
	}
	
	@Test
	public void miltiVersionsOfOS(){
 		GraphOptionsBuilder gob = new GraphOptionsBuilder(getTestValues(2,2,3));
 		JsonObject tJson = gob.buildJson();
 		JsonArray platforms = tJson.get("platforms").getAsJsonArray();
 		int platformSize = platforms.size();
 		assertEquals(2, platformSize);
 		JsonObject os1 = (JsonObject) platforms.get(0);
 		JsonArray versionArray = os1.get("versions").getAsJsonArray();
 		assertEquals(2, versionArray.size());
 		assertEquals(3, tJson.get("browsers").getAsJsonArray().size());
	}
	
	private List<HashMap<String, Object>> getTestValues(int numOS, int numVersion, int browsers){
		List<HashMap<String, Object>> testValues = new ArrayList<HashMap<String,Object>>();
		for (int os = 0; os < numOS; os++) {
			for (int osVer = 0; osVer < numVersion; osVer++) {
				for (int browser = 0; browser < browsers; browser++) {
					HashMap<String,Object> map = new HashMap<String, Object>();
					map.put(GraphOptionsBuilder.OSNAME, "OS"+os);
					map.put(GraphOptionsBuilder.OSVER,  "OSver"+osVer);
					map.put(GraphOptionsBuilder.OSID, os);
					map.put(GraphOptionsBuilder.DEVICENAME, "device"+os);
					map.put(GraphOptionsBuilder.DEVICEID, os);
					map.put(GraphOptionsBuilder.BROWSERNAME, "browser_"+browser);
					map.put(GraphOptionsBuilder.BROWSERID, browser);
					map.put(GraphOptionsBuilder.BROWSERVER, ""+browser);
					testValues.add(map);
				}
			}
		}
		return testValues;
	}
}
