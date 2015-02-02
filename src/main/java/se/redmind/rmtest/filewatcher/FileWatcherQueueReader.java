package se.redmind.rmtest.filewatcher;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcherQueueReader implements Runnable {

	private WatchService watchService;
	public FileWatcherQueueReader(WatchService watchService) {
		this.watchService = watchService;
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void run() {
		System.out.println("Running file watcher");
		try {
            // get the first event before looping
            WatchKey key = watchService.take();
            while(key != null) {
                // we have a polled event, now we traverse it and 
                // receive all the states from it
                for (WatchEvent event : key.pollEvents()) {
//                    System.out.printf("Received %s event for file: %s\n",
//                                      event.kind(), event.context() );
                	String context = event.context().toString();
                    if (event.kind().equals(ENTRY_CREATE)) {
                    	
                    }
                }
                
                
                key.reset();
                key = watchService.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping thread");
	}
	
}
