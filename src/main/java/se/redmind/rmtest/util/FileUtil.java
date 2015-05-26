package se.redmind.rmtest.util;

import java.io.File;

public class FileUtil {

	private static String lastMessage = "";
	
	public static synchronized boolean directoryExists(String path){
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				return true;
			} else lastMessage = "Is not a directory: "+path;
		}
		lastMessage = "Path do not exist: "+path;
		return false;
	}
	
	public static String getLastMessage(){
		return lastMessage;
	}
	
}
