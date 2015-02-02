package se.redmind.rmtest.db.read;

import se.redmind.rmtest.db.create.DBBridge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Created by johan on 15-01-29.
 */
public class ReadTestrunsFromDB extends DBBridge{


    String GET_ALL_TESTRUNS = "select * from testruns";
    String GET_ALL_TESTRUNS_BY_NAME = "select * from testruns";

    public HashSet getAllTestruns(){
        HashSet hs = new HashSet();
        ResultSet rs = readFromDB(GET_ALL_TESTRUNS);
        try {
            while(rs.next())
                hs.add(rs.getString(1));
            return hs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
