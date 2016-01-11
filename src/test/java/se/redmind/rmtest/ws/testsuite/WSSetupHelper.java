package se.redmind.rmtest.ws.testsuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

import org.junit.BeforeClass;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static org.mockito.Mockito.*;

import se.redmind.rmtest.db.DBCon;
import se.redmind.rmtest.db.InMemoryDBHandler;
import se.redmind.rmtest.report.init.ReportInit;
import se.redmind.rmtest.web.properties.ConfigHandler;
import spark.Request;
import spark.Response;

public class WSSetupHelper{

	private static String reportPath = System.getProperty("user.dir")+"/reports_for_test";
	private static String staticTests = System.getProperty("user.dir")+"/statictests";
	private static String longerMockedTests = System.getProperty("user.dir")+"/longermockedtests";
	private static String dbPath = System.getProperty("user.dir")+"/testRMTest.db";
	private static String root = System.getProperty("user.dir");
	private boolean useStaticTests;
	private static volatile boolean testMode = false;
	
	public WSSetupHelper() {
		beforeClass();
	}
	
	public static synchronized void beforeClass(){
		if (!testMode) {
			deleteOldDatabase();
			DBCon.getDbTestInstance();
			new ReportInit(reportPath).initReports();
			new ReportInit(staticTests).initReports();
			InMemoryDBHandler.getInstance().init();
			testMode = true;
		}
	}
	
	public static void beforeAutoTests(){
		closeConnections();
		deleteOldDatabase();
		DBCon.getDbTestInstance();
		ConfigHandler ch = ConfigHandler.getInstance(true);
		ch.clearReportPaths();
		ch.savePort(4567);
		ch.setScreenshotFolder(reportPath+"/screenshots");
		new ReportInit(longerMockedTests).initReports();
		new ReportInit(reportPath).initReports();
		InMemoryDBHandler.getInstance().updateInMemoryDB();
	}

	public static void deleteOldDatabase(){
		//remove testdb
		closeConnections();
		File dbFile = new File(dbPath);
		if (dbFile.exists()) {
			try {
				Files.delete(dbFile.toPath());
				System.out.println("Test DB deleted");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected static synchronized void closeConnections() {
		testMode = false;
		DBCon.closeAllConnections();
	}
	
	protected Response response(){
		return mock(Response.class);
	}
	
	protected Request request(){
		return mock(Request.class);
	}
	
	protected JsonObject jsonObject(JsonElement json){
		return new Gson().fromJson(json, JsonObject.class);
	}
	
	protected JsonArray jsonArray(JsonElement json){
		return new Gson().fromJson(json, JsonArray.class);
	}
	
	protected JsonObject jsonObject(String json) {
		return new Gson().fromJson(json, JsonObject.class);
	}
	
}
