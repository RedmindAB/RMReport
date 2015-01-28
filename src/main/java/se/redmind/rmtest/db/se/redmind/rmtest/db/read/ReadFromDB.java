package se.redmind.rmtest.db.se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-26.
 */
public class ReadFromDB {

    Connection conn;

    public ReadFromDB(Connection connection){
        conn=connection;
    }

    public void getMaxIdFromReports(){

        Statement stat = null;

        try {
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select max(id) from reports;");
            while(rs.next()){

                System.out.println("report id = " + rs.getString("id"));

                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void readTableTestcases(){

    }
}
