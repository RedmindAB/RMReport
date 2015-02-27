package se.redmind.rmtest.web.route.api.classes.getclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.DBBridge;
import se.redmind.rmtest.db.read.ReadClassFromDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class GetClassesDAO extends DBBridge {

	
	public String getClasses(int suiteid){
		JsonArray array = new JsonArray();
		List<HashMap<String, Object>> allClassNames = getAllClassNames(suiteid);
		for (HashMap<String, Object> hashMap : allClassNames) {
			JsonObject classObject = new JsonObject();
			String name = (String) hashMap.get("name");
			int id = Integer.valueOf((String) hashMap.get("id"));
			classObject.add("id", new JsonPrimitive(id));
			classObject.add("name", new JsonPrimitive(name));
			array.add(classObject);
		}
		return new Gson().toJson(array);
	}
	
	public List<HashMap<String, Object>> getAllClassNames(int suiteID){
		String GET_CLASS_FROM_SUITE_ID = "SELECT DISTINCT class.classname, class.class_id FROM report INNER JOIN class ON class.class_id = report.class_id WHERE suite_id = ";
        ResultSet rs = readFromDB(GET_CLASS_FROM_SUITE_ID+suiteID);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            while(rs.next()) {
            	HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", rs.getString("classname"));
                hm.put("id", rs.getString("class_id"));
                result.add(hm);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
