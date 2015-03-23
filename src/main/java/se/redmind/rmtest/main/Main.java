package se.redmind.rmtest.main;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.filewatcher.FileWatcher;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.web.properties.PropertiesReader;
import se.redmind.rmtest.web.route.RMTRoute;

import java.sql.SQLException;


public class Main {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		//starts the database.
		DBCon.getDbInstance();
		//Searches though the report directory for reports that are not added yet.
		String[] testDirectories = new PropertiesReader().getTestDirectory();
		int addedreports = 0;
		for (String string : testDirectories) {
			addedreports += new ReportInit(string).initReports();
		}
		System.out.println("Added "+addedreports+" reports.");
		//init the In Memory DB
		System.out.println("Init the in memory db...");
		new InMemoryDBHandler().init();
		System.out.println("Init the in memory db DONE!");
		//Listens to file changes in the report directory.
		FileWatcher.Run();
		//start the webserver.
		new RMTRoute();
	}


}
