package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-29.
 */
public class ReadClassFromDB {

    public static Connection conn;

    String GET_CLASS_ID = "select class_id from class where name =";

    public ReadClassFromDB(Connection connection){
        conn=connection;
    }
    public int getClassID(String className){
        ResultSet rs = getResulSet(GET_CLASS_ID+"'"+className+"'");
        try {
            System.out.println("Class id: "+rs.getString("class_id"));
            return rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
            return -1;
    }



    public ResultSet getResulSet(String query){
        Statement stat;
        try {
            stat = conn.createStatement();
            return stat.executeQuery(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
