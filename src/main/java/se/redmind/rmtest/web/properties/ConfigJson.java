package se.redmind.rmtest.web.properties;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigJson {
	
	private String gridQueryServletURL;
	private int port;
	private JsonArray reportPaths;
	
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

	public ConfigJson clone(){
		ConfigJson backup = new ConfigJson();
		backup.setGridQueryServletURL(this.gridQueryServletURL);
		backup.setPort(this.port);
		for (JsonElement path : reportPaths) {
			backup.addReportPath(path.getAsString());
		}
		return backup;
	}
	
}
