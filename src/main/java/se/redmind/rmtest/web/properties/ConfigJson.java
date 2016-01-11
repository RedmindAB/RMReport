package se.redmind.rmtest.web.properties;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigJson {
	
	private String gridQueryServletURL;
	private int port;
	private JsonArray reportPaths;
	private int livePort;
	private String screenshotFolder;
	
	public String getGridQueryServletURL() {
		return gridQueryServletURL;
	}
	public void setGridQueryServletURL(String gridQueryServletURL) {
		this.gridQueryServletURL = gridQueryServletURL;
	}
	public JsonArray getReportPaths() {
		return reportPaths;
	}
	public void setReportPaths(JsonArray reportPaths) {
		this.reportPaths = reportPaths;
	}
	public void addReportPath(String path){
		this.reportPaths.add(new JsonPrimitive(path));
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getLivePort() {
		return livePort;
	}
	
	public void setLivePort(int livePort) {
		this.livePort = livePort;
	}
	
	@Override
	public ConfigJson clone(){
		ConfigJson backup = new ConfigJson();
		backup.setReportPaths(new JsonArray());
		backup.setGridQueryServletURL(this.gridQueryServletURL);
		backup.setPort(this.port);
		backup.setScreenshotFolder(this.screenshotFolder);
		for (JsonElement path : reportPaths) {
			backup.addReportPath(path.getAsString());
		}
		return backup;
	}
	
	public String getScreenshotFolder() {
		return this.screenshotFolder == null ? "" : this.screenshotFolder;
	}
	
	public void setScreenshotFolder(String screenshotFolder){
		this.screenshotFolder = screenshotFolder;
	}
	
}
