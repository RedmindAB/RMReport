package se.redmind.rmtest.filewatcher;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcherQueueReader implements Runnable {

	private WatchService watchService;

	public FileWatcherQueueReader(WatchService watchService) {
		this.watchService = watchService;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void run() {
		System.out.println("File watcher is running!");
		try {
			// get the first event before looping
			WatchKey key = watchService.take();
			while (key != null) {
				// we have a polled event, now we traverse it and
				// receive all the states from it
				for (WatchEvent event : key.pollEvents()) {
//					System.out.printf("Received %s event for file: %s\n",
//							event.kind(), event.context());
					String filename = event.context().toString();
					boolean isXmlFile = filename.toLowerCase().endsWith(".xml");
					if (event.kind().equals(ENTRY_CREATE) && isXmlFile) {
						System.out.println(filename);
						ReportValidator reportValidator = new ReportValidator(
								filename);
						boolean reportExists = reportValidator.reportExists();
						if (!reportExists) {
							System.out.println("found new report!");
							reportValidator.saveReport();
						}
					}
				}
				key.reset();
				new InMemoryDBHandler().updateInMemoryDB();
				key = watchService.take();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Stopping file watcher thread");
	}

}
