package se.redmind.rmtest.filewatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;
import se.redmind.rmtest.web.properties.ConfigHandler;

public class FileWatcher {

	public static String[] directoryPaths = ConfigHandler.getInstance().getReportPaths();

	public static void Run() {
		for (String testFolder : directoryPaths) {
			Path path = Paths.get(testFolder);
			if (path == null) {
				throw new UnsupportedOperationException();
			}
			FileWatcherQueueReader queueReader = null;
			try {
				WatchService watcher = path.getFileSystem().newWatchService();
				queueReader = new FileWatcherQueueReader(
						watcher, testFolder);
				path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

				System.out.println("File watcher starting to check: " + testFolder);
				Thread thread = new Thread(queueReader, "DirectoryWatcher");
				thread.start();
			} catch (IOException e) {
				System.err.println("failed to start File watcher for path: "+testFolder);
			}
		}
	}
}
