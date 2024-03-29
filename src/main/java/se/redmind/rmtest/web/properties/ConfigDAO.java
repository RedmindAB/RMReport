package se.redmind.rmtest.web.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigDAO {
	
	Logger log = LogManager.getLogger(ConfigDAO.class);
	
	private static ConfigDAO configDAO;
	private String filePath;
	private File configFile;
	private Gson gson;
	private boolean isNew;

	private boolean test;
	
	
	private ConfigDAO(){
		this(false);
	}

	
	private ConfigDAO(boolean test){
		this.test = test;
		setFilePath();
		this.configFile = new File(filePath);
		this.isNew = !configFile.exists();
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	private void setFilePath() {
		String fileName = "/config.json";
		if(test){
			fileName = "/configTest.json";
		}
		this.filePath = System.getProperty("user.dir")+fileName;
	}
	
	public static ConfigDAO getInstance(){
		return getInstance(false);
	}
	public static ConfigDAO getInstance(boolean test) {
		if (configDAO == null) {
			configDAO = new ConfigDAO(test);
		}
		return configDAO;
	}
	
	public void save(ConfigJson config){
		String prettyJson = gson.toJson(config);
		writeToFile(prettyJson);
	}
	
	public ConfigJson load(){
		String jsonText = getJsonText();
		ConfigJson config = gson.fromJson(jsonText, ConfigJson.class);
		return config;
		
	}
	
	private String getJsonText(){
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		String result = sb.toString();
		if (result.length() == 0) {
			log.info("New config file!");
			this.isNew = true;
		}
		return result;
	}
	
	private void writeToFile(String text){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public boolean isNew(){
		return isNew;
	}


	public String getFileName() {
		return configFile.getName();
	}

}
