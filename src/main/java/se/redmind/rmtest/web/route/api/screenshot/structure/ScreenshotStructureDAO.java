package se.redmind.rmtest.web.route.api.screenshot.structure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.web.route.api.screenshot.ScreenshotFolderDAO;

public class ScreenshotStructureDAO extends DBBridge {

	private String query = "SELECT classname, testcasename, osname, osversion, devicename, browsername, browserversion  FROM report NATURAL JOIN (os,suite,testcase, class, device, browser) WHERE report.class_id = {class_id} AND timestamp = {timestamp} AND class.class_id = report.class_id;";
	
	public JsonArray getStructure(String classid, String timestamp){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("class_id", classid);
		map.put("timestamp", timestamp);
		String sql = stringParser.getString(query, map);
		return createStructure(timestamp, sql);
	}
	
	private JsonArray createStructure(String timestamp, String sql){
		ResultSet rs = readFromDB(sql);
		ScreenshotFolderDAO sfDAO = new ScreenshotFolderDAO();
		ScreenshotStructureBuilder builder = new ScreenshotStructureBuilder();
		sfDAO.getFilesByTimestamp(timestamp);
		try {
			while (rs.next()) {
				String classname = rs.getString("classname");
				String methodname = rs.getString("testcasename");
				String osname = rs.getString("osname");
				String osver = rs.getString("osversion");
				String devicename = rs.getString("devicename");
				String browsername = rs.getString("browsername");
				String browserver = rs.getString("browserversion");
				
				String fileName = sfDAO.generateFilename(classname, methodname, timestamp, osname, osver, browsername, browserver, devicename);
				List<String> fileNamesWithPrefix = sfDAO.getFilenames(fileName, timestamp);
				builder.addTestcase(methodname, browsername, devicename, fileNamesWithPrefix);
			}
			return builder.getAsJsonArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
