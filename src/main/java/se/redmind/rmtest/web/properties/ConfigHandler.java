package se.redmind.rmtest.web.properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class ConfigHandler {
	
	private static ConfigHandler configHandler;
	private ConfigDAO configDAO;
	private ConfigJson configJson;
	private ConfigJson backup;
	private boolean autoCommit;
	
	private ConfigHandler(){
		this.configDAO = ConfigDAO.getInstance();
		this.configJson = configDAO.load();
		this.autoCommit = true;
		init();
	}
	
	public static ConfigHandler getInstance(){
		if (configHandler == null) {
			configHandler = new ConfigHandler();
		}
		return configHandler;
	}
	
	public void savePoint(){
		this.backup = configJson.clone();
	}
	
	public void rollback(){
		this.configJson = this.backup;
	}
	
	public void setAutoCommit(boolean autoCommit){
		this.autoCommit = autoCommit;
	}
	
	private void save(){
		configDAO.save(configJson);
	}
	
	public void commit(){
		save();
	}
	
	public void commit(boolean setAutoCommit){
		save();
		autoCommit = setAutoCommit;
	}
	
	private void init(){
		boolean isNew = configDAO.isNew();
		if (isNew) {
			ConfigJson conf = new ConfigJson();
			conf.setGridQueryServletURL("http://localhost:4444/grid/admin/GridQueryServlet");
			conf.setPort(4567);
			conf.setReportPaths(new JsonArray());
			configDAO.save(conf);
			this.configJson = conf;
		}
	}
	
	public void saveReportPath(String path){
		configJson.getReportPaths().add(new JsonPrimitive(path));
		autoCommit();
	}

	private void autoCommit() {
		if (autoCommit) {
			save();
		}
	}
	
	public void updateReportPath(int index, String path){
		JsonArray reportPaths = configJson.getReportPaths();
		reportPaths.set(index, new JsonPrimitive(path));
		autoCommit();
	}
	
	public String[] getReportPaths(){
		JsonArray pathsJson = configJson.getReportPaths();
		return new Gson().fromJson(pathsJson, String[].class);
	}

	public int getPort() {
		return configJson.getPort();
	}
	
	public void savePort(int port){
		configJson.setPort(port);
		autoCommit();
	}

	public String getSeleniumGridURL() {
		return configJson.getGridQueryServletURL();
	}
	
	public void saveSeleniumGridURL(String url){
		configJson.setGridQueryServletURL(url);
		autoCommit();
	}
	
	public ConfigJson getConfig(){
		return configJson;
	}

}
