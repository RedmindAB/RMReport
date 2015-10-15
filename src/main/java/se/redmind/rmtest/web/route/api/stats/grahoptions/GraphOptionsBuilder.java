package se.redmind.rmtest.web.route.api.stats.grahoptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GraphOptionsBuilder {

	private JsonObject mainObject = new JsonObject();
	private JsonArray platformArray = new JsonArray();
	private JsonArray browserArray = new JsonArray();
	private ResultSet rs;
	private HashSet<String> osList;
	private HashSet<Integer> osIDs;
	private HashSet<Integer> deviceIDs;
	private HashSet<Integer> browser;
	private HashSet<String> osNames;
	private HashSet<String> osVers;
	private boolean buildWithHashMap;
	
	public static final String 	OSNAME = "osname", 
								OSVER = "osver", 
								OSID = "osid",
								OSVERS = "versions",
								DEVICENAME = "devicename",
								DEVICEID = "deviceid",
								DEVICES = "devices",
								BROWSERNAME = "browsername",
								BROWSERID = "browserid",
								BROWSERVER = "browserver";
	private List<HashMap<String, Object>> hashMapValues;
	
	private GraphOptionsBuilder(){
		this.mainObject.add("platforms", platformArray);
		this.mainObject.add("browsers", browserArray);
		this.osIDs = new HashSet<Integer>();
		this.osNames = new HashSet<String>();
		this.deviceIDs = new HashSet<Integer>();
		this.browser = new HashSet<Integer>();
		this.osVers = new HashSet<String>();
		this.osList = new HashSet<String>();
	}
	
	public GraphOptionsBuilder(ResultSet rs) {
		this();
		this.rs = rs;
	}
	
	public GraphOptionsBuilder(List<HashMap<String, Object>> hashMapValues) {
		this();
		this.hashMapValues = hashMapValues;
		this.buildWithHashMap = true;
	}

	public JsonObject buildJson(){
		if(buildWithHashMap){
			buildWithHashMap();
		}
		else buildWithResultset();
		return mainObject;
	}

	private void buildWithHashMap() {
		for (HashMap<String, Object> combination : hashMapValues) {
			addOption(combination);
		}
	}

	private void buildWithResultset() {
		try {
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String os 			= rs.getString("osname");
				map.put(OSNAME, os);
				String osVer 		= rs.getString("osversion");
				map.put(OSVER, osVer);
				int osID 			= rs.getInt("os_id");
				map.put(OSID, osID);
				String deviceName 	= rs.getString("devicename");
				map.put(DEVICENAME, deviceName);
				int deviceID 		= rs.getInt("device_id");
				map.put(DEVICEID, deviceID);
				String browserName 	= rs.getString("browsername");
				map.put(BROWSERNAME, browserName);
				int browserID 		= rs.getInt("browser_id");
				map.put(BROWSERID, browserID);
				String browserVer 	= rs.getString("browserversion");
				map.put(BROWSERVER, browserVer);
				addOption(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addOption(HashMap<String, Object> map){
		addOS(map);
		addDevice(map);
		addBrowser(map);
	}

	private void addDevice(HashMap<String, Object> map) {
		String platformName = (String) map.get(OSNAME);
		try {
			JsonObject platform = getPlatform(platformName);
			int deviceID = (int) map.get(DEVICEID);
			String device = (String) map.get(DEVICENAME);
			if (!deviceExists(deviceID)) {
				JsonObject deviceToAdd = new JsonObject();
				deviceToAdd.add(DEVICENAME, new JsonPrimitive(device));
				deviceToAdd.add(DEVICEID, new JsonPrimitive(deviceID));
				String osver = (String) map.get(OSVER);
				deviceToAdd.add(OSVER, new JsonPrimitive(osver));
				deviceToAdd.add(OSID, new JsonPrimitive((Integer) map.get(OSID)));
				platform.get(DEVICES).getAsJsonArray().add(deviceToAdd);
				deviceIDs.add(deviceID);
			}
		} catch (Exception e) {
			System.err.println("Error adding devices for "+platformName+" in graphoptions");
		}
	}

	private JsonObject getPlatform(String platformName) {
		JsonArray os = mainObject.get("platforms").getAsJsonArray();
		for (JsonElement platform : os) {
			JsonObject currentPlatform = (JsonObject) platform;
			String osname = currentPlatform.get(OSNAME).getAsString();
			if (osname.equals(platformName)) {
				return currentPlatform;
			}
		}
		return new JsonObject();
	}

	private void addBrowser(HashMap<String, Object> map) {
		String browserName = (String) map.get(BROWSERNAME);
		String browserVer = (String) map.get(BROWSERVER);
		int browserID = (int) map.get(BROWSERID);
		if (!browser.contains(browserID)) {
			JsonObject browser = new JsonObject();
			browser.add(BROWSERNAME, new JsonPrimitive(browserName));
			browser.add(BROWSERID, new JsonPrimitive(browserID));
			browser.add(BROWSERVER, new JsonPrimitive(browserVer));
			browserArray.add(browser);
			this.browser.add(browserID);
		}
	}

	private void addOS(HashMap<String, Object> map) {
		int osid = (int) map.get(OSID);
		String osName = (String) map.get(OSNAME);
		String osVer = (String) map.get(OSVER);
		if (!osExists(osid, osName, osVer)) {
			JsonObject os = getPlatform(osName);
			boolean isNew = os.get(OSNAME) == null;
			if (isNew) {
				deviceIDs.clear();
				osNames.add(osName);
				os.add(OSNAME, new JsonPrimitive(osName));
				os.add(OSVERS, new JsonArray());
				os.add(DEVICES, new JsonArray());
				JsonArray versions = os.get(OSVERS).getAsJsonArray();
				JsonObject ver = getVersionObject(osid, osVer);
				versions.add(ver);
				osVers.add(osVer);
				osIDs.add(osid);
				platformArray.add(os);
				addOsToOSlist(osid, osName, osVer);
			}
			else if(!osContainsVersion(os, osVer)){
				JsonArray versions = os.get(OSVERS).getAsJsonArray();
				versions.add(getVersionObject(osid, osVer));
			}
		}
	}

	private JsonObject getVersionObject(int osid, String osVer) {
		JsonObject ver = new JsonObject();
		ver.add(OSVER, new JsonPrimitive(osVer));
		ver.add(OSID, new JsonPrimitive(osid));
		return ver;
	}
	
	private boolean osContainsVersion(JsonObject os, String osVer) {
		JsonArray osVers = (JsonArray) os.get(OSVERS);
		boolean containsVersion = false;
		if(osVers != null){
			for (JsonElement versionElm : osVers) {
				JsonObject version = (JsonObject) versionElm;
				String versionString = version.get(OSVER).getAsString();
				containsVersion = versionString.equals(osVer);
				if(containsVersion) return true;
			}
		}
		return containsVersion;
	}

	private boolean osExists(int osid, String osName, String osVer){
		return osList.contains(osIDs+osName+osVer);
	}
	
	private void addOsToOSlist(int osId, String osname, String osVer){
		osList.add(osId+osname+osVer);
	}
	
	private boolean deviceExists(int deviceid){
		return deviceIDs.contains(deviceid);
	}
	
}
