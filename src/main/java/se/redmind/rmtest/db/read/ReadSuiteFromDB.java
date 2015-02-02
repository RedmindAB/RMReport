package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by johan on 15-01-29.
 */
public class ReadSuiteFromDB extends DBBridge{

    String GET_SUIT_ID = "select suite_id from suite where name= ";
    String GET_ALL_SUITS = "select * from suite";


    public int getSuiteID(String suitName){
        ResultSet rs = readFromDB(GET_SUIT_ID+"'"+suitName+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return -1;
    }

    public List<HashMap<String,Object>> getAllSuites(){
        ResultSet rs = readFromDB(GET_ALL_SUITS);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        try {
            while(rs.next()) {
            	HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", rs.getString("name"));
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
