package se.redmind.rmtest.web.route.api.stats.rmdocs.filterfile;

import java.util.TreeMap;

import org.apache.logging.log4j.util.Strings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RMDocsFilterfFileBuilder {
	
	private final JsonArray data;
	private final TreeMap<String, String> map;
	private final int threshhold;

	public RMDocsFilterfFileBuilder(JsonArray data, int threshhold) {
		this.data = data;
		this.threshhold = threshhold;
		this.map = new TreeMap<>();
	}
	
	public String getAsText(){
		for (JsonElement element : data) {
			JsonObject json = element.getAsJsonObject();
			int avgPass = json.get("medel").getAsInt();
			if(avgPass >= threshhold){
				String key = getKey(json);
				String rawBrowser = json.get("browsername").getAsString();
				String browser = fixBrowserSpaces(rawBrowser);
				appendValue(key, browser);
			}
		}
		return convertMapToString();
	}

	private String fixBrowserSpaces(String rawBrowser) {
		String res = "";
		String[] split = rawBrowser.split(" ");
		for (String string : split) {
			res+=Character.toString(string.charAt(0)).toUpperCase()+string.substring(1);
		}
		return res;
	}

	private String convertMapToString() {
		StringBuilder sb = new StringBuilder();
		final String linebreak = "\n";
		map.entrySet()
		.iterator()
		.forEachRemaining(e -> 	sb.append(e.getKey())
								.append(e.getValue())
								.append(linebreak));
		return sb.toString();
	}

	private String getKey(JsonObject json) {
		return json.get("classname").getAsString()+"#"+json.get("testcasename").getAsString();
	}

	private void appendValue(String key, String value) {
		value = getCurrentValue(key)+" "+value;
		map.put(key, value);
	}

	private String getCurrentValue(String key) {
		String currentValue = map.get(key);
		if(currentValue == null) currentValue = "";
		return currentValue;
	}

}
