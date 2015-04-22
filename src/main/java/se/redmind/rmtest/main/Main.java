package se.redmind.rmtest.main;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.filewatcher.FileWatcher;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.web.properties.PropertiesReader;
import se.redmind.rmtest.web.route.RMTRoute;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Logger log = LogManager.getLogger(Main.class);
		log.info("Starting Redmind report");
		log.info("Starting database...");
		//starts the database.
		DBCon.getDbInstance();
		log.info("Stating database done");
		//Searches though the report directory for reports that are not added yet.
		String[] testDirectories = new PropertiesReader().getTestDirectory();
		int addedreports = 0;
		for (String string : testDirectories) {
			addedreports += new ReportInit(string).initReports();
		}
		log.info("Added "+addedreports+" reports.");
		//init the In Memory DB
		log.info("Init the in memory db...");
		new InMemoryDBHandler("RMTest").init();
		log.info("Init the in memory db DONE!");
		//Listens to file changes in the report directory.
		FileWatcher.Run();
		
		//start the webserver.
		int port = setupPort(args);
		new RMTRoute(port);
	}

	private static int setupPort(String[] args) {
		try {
			String port = args[0];
			int argPort = 0;
			if (port != null) {
				argPort = Integer.valueOf(port);
			}
			return argPort;
		} catch (Exception e) {
			return 0;
		}
	}


}
