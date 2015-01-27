package se.redmind.rmtest.web.properties;

import java.io.File;
import java.util.Properties;

public class PropertiesReader {
	
	private static String propFilePath;
	private Properties properties;
	public PropertiesReader() {
		propFilePath = System.getProperty("user.dir")+"/config.prop";
		File file = new File(propFilePath);
		file.mkdirs();
	}
	
	
}
