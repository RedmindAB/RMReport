package se.redmind.rmtest.filewatcher;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.reportvalidation.ReportValidator;
import se.redmind.rmtest.report.sysout.ReportSystemOutPrintFile;
import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.route.api.cache.WSCache;

public class FileWatcher extends Thread {

    private final Logger log = LogManager.getLogger(FileWatcher.class);
    private final WatchService watchService;
    private final String path;

    public FileWatcher(WatchService watchService, String path) {
        this.watchService = watchService;
        this.path = path;
        this.setDaemon(true);
    }

    @SuppressWarnings({"rawtypes"})
    @Override
    public void run() {
        while (this.isAlive()) {
            WatchKey key = null;
            try {
                key = watchService.take();
                // we have a polled event, now we traverse it and
                // receive all the states from it
                boolean updatedReports = false;
                for (WatchEvent event : key.pollEvents()) {
                    String filename = event.context().toString();
                    boolean isValidFile = filename.toLowerCase().endsWith(".xml") || filename.toLowerCase().endsWith(".json");
                    if (event.kind().equals(ENTRY_CREATE) && isValidFile) {
                        int attempts = 0;
                        while (!isCompletelyWritten(path, filename) && attempts < 10) {
                            log.info("File is not done, waiting for 50ms");
                            TimeUnit.MILLISECONDS.sleep(50);
                            attempts++;
                        }
                        ReportValidator reportValidator = new ReportValidator(filename, path);
                        if (!reportValidator.reportExists() && reportValidator.isValidFilename()) {
                            log.info("found new report!");
                            log.info("saved report: " + filename + " " + reportValidator.saveReport());
                            ReportSystemOutPrintFile sysoFile = new ReportSystemOutPrintFile(reportValidator);
                            sysoFile.copyReportOutputFile();
                            updatedReports = true;
                        } else {
                            log.info(filename + " is not a valid report");
                        }
                    }
                }
                if (updatedReports) {
                    log.info("Updating in memory DB");
                    new InMemoryDBHandler("RMTest").updateInMemoryDB();
                    log.info("Clearing WebService cache");
                    WSCache.getInstance().clear();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (key != null) {
                    key.reset();
                }
            }
        }
        log.info("Stopping file watcher thread");
    }

    private boolean isCompletelyWritten(String path, String filename) {
        File dir = new File(path);
        path = dir.getAbsolutePath();
        File file = new File(path + filename);
        RandomAccessFile stream = null;
        try {
            stream = new RandomAccessFile(path + filename, "rw");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    Files.deleteIfExists(file.toPath());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return false;
    }

    public static void Run() {
        for (String testFolder : ConfigHandler.getInstance().getReportPaths()) {
            Path path = Paths.get(testFolder);
            if (path == null) {
                throw new UnsupportedOperationException();
            }
            try {
                WatchService watcher = path.getFileSystem().newWatchService();
                FileWatcher queueReader = new FileWatcher(watcher, testFolder);
                path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
                LogManager.getLogger(FileWatcher.class).info("File watcher starting to check: " + testFolder);
                queueReader.start();
            } catch (IOException e) {
                LogManager.getLogger(FileWatcher.class).error("failed to start File watcher for path: " + testFolder);
            }
        }
    }

}
