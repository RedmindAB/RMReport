package se.redmind.rmtest.web.route.api.screenshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.redmind.rmtest.testhome.TestHome;
import se.redmind.rmtest.web.properties.ConfigHandler;

public class ScreenshotFolderDAO {

	private final String screenshotPath; 
	
	public ScreenshotFolderDAO() {
		screenshotPath = setPath();
	}
	
	public File[] getFilesByTimestamp(String timestamp){
		String path = this.screenshotPath+"/"+timestamp;
		return getFiles(path);
	}
	
	public File[] getFiles(String path){
		return new File(path).listFiles();
	}
	
	public File getScreenshot(String timestamp, String filename){
		String path = this.screenshotPath+"/"+timestamp+"/"+filename;
		return new File(path);
	}
	
	public String generateFilename(String classname, 
							  String methodname, 
							  String timestamp, 
							  String osname, 
							  String osver, 
							  String browsername,
							  String browserver,
							  String devicename){
		return classname+"."+methodname+"-"+timestamp+"["+osname+"_"+osver+"_"+devicename+"_"+browsername+"_"+browserver+"].png";
	}
	
	public List<String> getFilenames(String filename, String timestamp){
		File[] files = getFilesByTimestamp(timestamp);
		List<String> filenames = new ArrayList<String>();
		if (files != null) {
			for (File file : files) {
				boolean fileExist = file.getName().contains(filename);
				if (fileExist) {
					filenames.add(file.getName());
				}
			}
		}
		return filenames;
	}
	
	private String getTestHomePath(){
		return TestHome.main()+"/RMR-Screenshots";
	}
	
	private String setPath(){
		String screenshotPath = ConfigHandler.getInstance().getScreenshotFolder();
		if(new File(screenshotPath).exists()){
			return screenshotPath;
		}
		else {
			return getTestHomePath();
		}
	}
	
	
	
}
