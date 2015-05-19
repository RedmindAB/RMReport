package se.redmind.rmtest.util;

import java.io.File;

public class FileUtil {

	
	public static synchronized boolean directoryExists(String path){
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			return true;
		}
		return false;
	}
	
}
