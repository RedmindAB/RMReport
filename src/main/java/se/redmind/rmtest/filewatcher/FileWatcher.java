package se.redmind.rmtest.filewatcher;

import java.io.IOException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;
import se.redmind.rmtest.report.reportloader.ReportLoader;

public class FileWatcher {

	public static String directoryPath = new ReportLoader(System.getProperty("user.dir")+"/testfiles", false).getReportFolderPath();
	
	public static void Run(){
		System.out.println("File watcher starting to check: "+directoryPath);
		Path path = Paths.get(directoryPath);
		if (path == null) {
			throw new UnsupportedOperationException();
		}
		
		try {
			WatchService watcher = path.getFileSystem().newWatchService();
			FileWatcherQueueReader queueReader = new FileWatcherQueueReader(watcher);
			Thread thread = new Thread(queueReader, "DirectoryWatcher");
			thread.start();
			
			path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
