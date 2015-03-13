package se.redmind.rmtest.web.route.api.cache;

import java.util.concurrent.ConcurrentHashMap;

public class WSCache {
	
	private ConcurrentHashMap<String, String> cacheMap;
	
	private static WSCache instance;
	
	private WSCache() {
		cacheMap = new ConcurrentHashMap<String, String>();
	}
	
	public static WSCache getInstance(){
		if (instance == null) {
			instance = new WSCache();
		}
		return instance;
	}
	
	/**
	 * Add a value to the cache, no value can be null.
	 * @param path - the path of the api method. example: "/path/to/api/method"
	 * @param queryParams - the params of the query.
	 * @param value - the value that should be cached
	 */
	public void add(String path, String queryParams, String value){
		if (path == null || queryParams == null || value == null) {
			throw new NullPointerException();
		}
		cacheMap.put(path+queryParams, value);
	}
	
	
	/**
	 * Return the value save by the path and params, or @null if the value dose not exist.
	 * @param path - the path of the api method. example: "/path/to/api/method"
	 * @param queryParams - the params of the query.
	 * @return - the value saved with the path and query combination.
	 */
	public String get(String path, String queryParams){
		return cacheMap.get(path+queryParams);
	}
	
	/**
	 * Clears the cache, use this if the database have changed.
	 */
	public void clear(){
		cacheMap.clear();
	}
	
}
