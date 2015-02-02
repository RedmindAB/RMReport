package se.redmind.rmtest.web;

import se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.filewatcher.FileWatcher;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.web.route.RMTRoute;

import java.sql.SQLException;


public class Main {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		//starts the database.
		DBCon.getDbInstance();
		//Searches though the report directory for reports that are not added yet.
		int addedreports = new ReportInit().initReports();
		System.out.println("Added "+addedreports+" reports.");
		//Listens to file changes in the report directory.
		FileWatcher.Run();
		//start the webserver.
		new RMTRoute();
	}


}
