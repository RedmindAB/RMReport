package se.redmind.rmtest.web.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class PropertiesReader {
	
	private static String propFilePath;
	private Properties properties;
	private File file;
	
	private static final String TEST_DIR = "testDir";
	
	public PropertiesReader() {
		propFilePath = System.getProperty("user.dir")+"/config.prop";
		properties = new Properties();
		this.file = new File(propFilePath);
		try {
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
			properties.load(new FileInputStream(file));
			checkPropFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkPropFile(){
		String testDir = properties.getProperty(TEST_DIR);
		if (testDir == null) {
			System.err.println("No properties file was detected, creating template file:");
			properties.put(TEST_DIR, "/PATH/TO/surefire-reports");
			try {
				properties.store(new FileOutputStream(this.file), "Created: "+new Date()+" add more test directories with ',' as a divider");
				System.out.println("config.prop created at: "+file.getAbsolutePath());
				System.err.println("Exiting program");
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public String[] getTestDirectory(){
		String property = properties.getProperty(TEST_DIR);
		String[] dirs = property.split(",");
		return dirs;
	}
	
	
}
