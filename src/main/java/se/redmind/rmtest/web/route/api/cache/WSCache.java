package se.redmind.rmtest.web.route.api.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.JsonElement;

public class WSCache {
	
	private ConcurrentHashMap<String, WSCacheContainer> cacheMap;
	
	private static WSCache instance;
	
	private WSCache() {
		cacheMap = new ConcurrentHashMap<String, WSCacheContainer>();
	}
	
	public static WSCache getInstance(){
		if (instance == null) {
			instance = new WSCache();
		}
		return instance;
	}
	
	public void add(String path, String body, String queryParams, JsonElement value){
		if (path == null || body == null || value == null) {
			throw new NullPointerException();
		}
		String key = path+queryParams+body;
		cacheMap.put(key, new WSCacheContainer(value));
	}
	
	/**
	 * Add a value to the cache, no value can be null.
	 * @param path - the path of the api method. example: "/path/to/api/method"
	 * @param queryParams - the params of the query.
	 * @param value - the value that should be cached
	 */
	public void add(String path, String queryParams, JsonElement value){
		if (path == null || value == null) {
			throw new NullPointerException();
		}
		cacheMap.put(path+queryParams, new WSCacheContainer(value));
	}
	
	
	/**
	 * Return the value save by the path and params, or @null if the value dose not exist.
	 * @param path - the path of the api method. example: "/path/to/api/method"
	 * @param queryParams - the params of the query.
	 * @return - the value saved with the path and query combination.
	 */
	public JsonElement get(String path, String queryParams){
		WSCacheContainer wsCacheContainer = cacheMap.get(path+queryParams);
		return getJson(wsCacheContainer);
	}
	
	public JsonElement get(String path, String body, String queryParams){
		String key = path+queryParams+body;
		WSCacheContainer wsCacheContainer = cacheMap.get(key);
		return getJson(wsCacheContainer);
	}

	private JsonElement getJson(WSCacheContainer wsCacheContainer) {
		if (wsCacheContainer != null && isValid(wsCacheContainer)) {
			JsonElement json = wsCacheContainer.getJson();
			return json;
		}
		return null;
	}

	private boolean isValid(WSCacheContainer wsCacheContainer) {
		boolean after = wsCacheContainer.getCreationDate().after(DBBridge.getLastUpdated());
		return after;
	}
	
	public void printAll(){
		Set<String> keySet = cacheMap.keySet();
		for (String key : keySet) {
			System.out.println(key+" : "+cacheMap.get(key));
		}
	}
	
	/**
	 * Clears the cache, use this if the database have changed.
	 */
	public void clear(){
		cacheMap.clear();
	}
	
}
