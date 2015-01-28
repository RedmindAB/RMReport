package se.redmind.rmtest.db.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by johan on 15-01-26.
 */
public class ReadFromDB {

	public static 
	
    Connection conn;

    public ReadFromDB(Connection connection){
        conn=connection;
    }
    
    public void getMaxID(){
    	ResultSet rs = getResulSet("select MAX(id) from reports");
    }

    public ResultSet getResulSet(String query){
        Statement stat = null;
            try {
            	stat = conn.createStatement();
				return stat.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
    }

    public void readTableTestcases(){

    }
}
