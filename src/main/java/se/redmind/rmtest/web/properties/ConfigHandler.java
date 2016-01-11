package se.redmind.rmtest.web.properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigHandler {
	
	private static ConfigHandler configHandler;
	public static final String TEST_CONFIG_PATH = System.getProperty("user.dir")+"/configTest.json";
	private ConfigDAO configDAO;
	private ConfigJson configJson;
	private ConfigJson backup;
	private boolean autoCommit;
	
	private ConfigHandler(){
		this(false);
	}
	
	private ConfigHandler(boolean test) {
		this.configDAO = ConfigDAO.getInstance(test);
		this.configJson = configDAO.load();
		this.autoCommit = true;
		init();
	}

	public static ConfigHandler getInstance(boolean test){
		if (configHandler == null) {
			configHandler = new ConfigHandler(test);
		}
		return configHandler;
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
			conf.setLivePort(12345);
			configDAO.save(conf);
			this.configJson = conf;
		}
	}
	
	public boolean saveReportPath(String path){
		JsonArray reportPaths = configJson.getReportPaths();
		for (JsonElement storedPath : reportPaths) {
			if (path.equals(storedPath.getAsString())) {
				return false;
			}
		}
		configJson.getReportPaths().add(new JsonPrimitive(path));
		autoCommit();
		return true;
	}

	private void autoCommit() {
		if (autoCommit) {
			save();
		}
	}
	
	public void updateReportPath(String old, String newPath){
		JsonArray reportPaths = configJson.getReportPaths();
		for (int i = 0; i < reportPaths.size(); i++) {
			if (reportPaths.get(i).getAsString().equals(old)) {
				reportPaths.set(i, new JsonPrimitive(newPath));
				autoCommit();
				break;
			}
		}
		autoCommit();
	}
	
	public String[] getReportPaths(){
		JsonArray pathsJson = configJson.getReportPaths();
		return new Gson().fromJson(pathsJson, String[].class);
	}
	
	public void deleteReportPath(String dPath) {
		JsonArray reportPaths = configJson.getReportPaths();
		for (JsonElement pathElement : reportPaths) {
			String path = pathElement.getAsString();
			if (path.equals(dPath)) {
				reportPaths.remove(pathElement);
				break;
			}
		}
		autoCommit();
	}
	
	public boolean reportPathExistInConfig(String reportDir){
		String[] reportPaths = getReportPaths();
		for (String string : reportPaths) {
			if (string.equals(reportDir)) {
				return true;
			}
		}
		return false;
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

	public void removeAllPaths(JsonArray removeArray) {
		for (JsonElement jsonElement : removeArray) {
			configJson.getReportPaths().remove(jsonElement);
		}
		autoCommit();
	}

	public int getLiveStreamPort() {
		return configJson.getLivePort();
	}

	public String getFileName() {
		return ConfigDAO.getInstance().getFileName();
	}

	public void clearReportPaths() {
		configJson.setReportPaths(new JsonArray());
	}

	public void saveLiveStreamPort(int liveStreamPort) {
		configJson.setLivePort(liveStreamPort);
	}
	
	public String getScreenshotFolder(){
		return configJson.getScreenshotFolder();
	}

	public void setScreenshotFolder(String screenshotFolder) {
		this.configJson.setScreenshotFolder(screenshotFolder);
	}

}
