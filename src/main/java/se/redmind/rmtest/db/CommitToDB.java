package se.redmind.rmtest.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by johan on 15-01-27.
 */
public class CommitToDB {
        Connection conn;

    public CommitToDB(Connection connection){
        conn = connection;
    }

    public void commitReport(PreparedStatement prep){


        try {
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void commitTestcase(PreparedStatement prep){

        try {
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
