package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


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

    public Object[] getAllSuites(){
        HashMap<String, Object> hm = new HashMap();
        ResultSet rs = readFromDB(GET_ALL_SUITS);
        try {
            while(rs.next()) {
                hm.put("Name", "id");
            }
            return hm.entrySet().toArray();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
