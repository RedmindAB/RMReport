package se.redmind.rmtest.api.cache;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.route.api.cache.WSCache;

public class WSCacheTests {

	WSCache cache;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBBridge.updateLastUpdated(1000);
	}
	
	@Before
	public void before(){
		cache = WSCache.getInstance();
		cache.clear();
	}

	@Test
	public void pathOnly() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("hej", new JsonPrimitive("da"));
		String key = "/api/stats/device/fail/1/android";
		cache.add(key, "null", jsonObject);
		JsonObject asJsonObject = cache.get(key, "null").getAsJsonObject();
		assertNotNull(asJsonObject);
		assertEquals("da", asJsonObject.get("hej").getAsString());
	}

	@Test
	public void pathAndBody() throws InterruptedException{
		JsonArray ar = getAnArray(5);
		String path = "/api/stats/graph/graphdata";
		String queryParams = "null";
		String body = new Gson().toJson(ar);
		JsonArray value = getAnArray(2);
		cache.add(path, body, queryParams, value);
		JsonElement jsonElement = cache.get(path, body, queryParams);
		assertNotNull(jsonElement);
		assertEquals(value.size(), jsonElement.getAsJsonArray().size());
	}
	
	private JsonArray getAnArray(int size){
		JsonArray array = new JsonArray();
		for (int i = 0; i < size; i++) {
			JsonObject obj = new JsonObject();
			obj.addProperty(""+i,i);
			obj.addProperty("aw"+i,i);
			array.add(obj);
		}
		return array;
	}
	
}
