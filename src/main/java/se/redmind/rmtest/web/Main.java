package se.redmind.rmtest.web;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.web.route.RMTRoute;

import java.sql.SQLException;


public class Main {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		DBCon Con = new DBCon();
		Con.connect();

		new RMTRoute();
	}


}
