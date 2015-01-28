package se.redmind.rmtest.web;

import se.redmind.rmtest.db.se.redmind.rmtest.db.create.DBCon;
import se.redmind.rmtest.db.se.redmind.rmtest.db.read.ReadFromDB;
import se.redmind.rmtest.filewatcher.FileWatcher;
import se.redmind.rmtest.web.route.RMTRoute;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		DBCon.getDbInstance();
		FileWatcher.Run();

		new RMTRoute();
	}


}
