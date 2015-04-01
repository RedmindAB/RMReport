package se.redmind.rmtest.filewatcher;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.report.sysout.ReportSystemOutPrintFile;
import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcherQueueReader implements Runnable {

	private Logger log = LogManager.getLogger(FileWatcherQueueReader.class);
	
	private WatchService watchService;
	private String path;

	public FileWatcherQueueReader(WatchService watchService, String path) {
		this.watchService = watchService;
		this.path = path;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void run() {
		try {
			// get the first event before looping
			WatchKey key = watchService.take();
			while (key != null) {
				// we have a polled event, now we traverse it and
				// receive all the states from it
				boolean updatedReports = false;
				for (WatchEvent event : key.pollEvents()) {
					String filename = event.context().toString();
					boolean isXmlFile = filename.toLowerCase().endsWith(".xml");
					while (!isCompletelyWritten(path, filename)) {
						log.info("File is not done, waiting for 50ms");
						Thread.sleep(50);
					}
					if (event.kind().equals(ENTRY_CREATE) && isXmlFile) {
						System.out.println(filename);
						ReportValidator reportValidator = new ReportValidator(
								filename, path);
						boolean reportExists = reportValidator.reportExists();
						boolean validFilename = reportValidator.isValidFilename();
						if (!reportExists && validFilename) {
							log.info("found new report!");
							reportValidator.saveReport();
							ReportSystemOutPrintFile sysoFile = new ReportSystemOutPrintFile(reportValidator);
							sysoFile.copyReportOutputFile();
							updatedReports = true;
						}
					}
				}
				if (updatedReports) {
					new InMemoryDBHandler().updateInMemoryDB();
				}
				key.reset();
				key = watchService.take();
			}
		} catch (InterruptedException e) {
			log.error("something went wrong: restarting filewatcher!");
			run();
		}
		log.info("Stopping file watcher thread");
	}
	
	private boolean isCompletelyWritten(String path, String filename) {
		File dir = new File(path);
		path = dir.getAbsolutePath();
		File file = new File(path+filename);
	    RandomAccessFile stream = null;
	    try {
	        stream = new RandomAccessFile(file, "rw");
	        return true;
	    } catch (Exception e) {
	    	log.error(e.getMessage());
	    } finally {
	        if (stream != null) {
	            try {
	                stream.close();
	            } catch (IOException e) {
	            	log.error(e.getMessage());
	            }
	        }
	    }
	    return false;
	}

}
