package se.redmind.rmtest.db.lookup.classname;

import se.redmind.rmtest.db.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johan on 15-01-29.
 */
public class ClassDbLookup extends DBBridge{

    String GET_CLASS_ID = "select class_id from class where classname =";
   

    public int getClassID(String className){
        ResultSet rs = readFromDB(GET_CLASS_ID+"'"+className+"'");
        try {
            return rs.getInt(1);
        } catch (SQLException e) {
           // e.printStackTrace();
        }
            return -1;
    }

    



}
