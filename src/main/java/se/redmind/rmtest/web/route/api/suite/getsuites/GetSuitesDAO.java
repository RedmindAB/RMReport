package se.redmind.rmtest.web.route.api.suite.getsuites;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.redmind.rmtest.db.DBBridge;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GetSuitesDAO extends DBBridge{

	public String getSuites() {
		JsonArray jsonArray = new JsonArray();
		List<HashMap<String, Object>> allSuites = getAllSuites();
		for (HashMap<String, Object> hashMap : allSuites) {
			JsonObject suiteObject = new JsonObject();
			suiteObject.add("name",
					new JsonPrimitive((String) hashMap.get("name")));
			JsonPrimitive id = new JsonPrimitive(
					Integer.valueOf((String) hashMap.get("id")));
			suiteObject.add("id", id);
			jsonArray.add(suiteObject);
		}
		return new Gson().toJson(jsonArray);
	}
    public List<HashMap<String,Object>> getAllSuites(){
    	String GET_ALL_SUITS = "select * from suite";
        ResultSet rs = readFromDB(GET_ALL_SUITS);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            while(rs.next()) {
            	HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", rs.getString("suitename"));
                hm.put("id", rs.getString("suite_id"));
                result.add(hm);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
