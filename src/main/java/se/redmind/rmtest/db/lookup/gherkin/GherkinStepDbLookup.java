package se.redmind.rmtest.db.lookup.gherkin;

import se.redmind.rmtest.db.DBBridge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by victormattsson on 2016-02-15.
 */
public class GherkinStepDbLookup extends DBBridge {

    String GET_ALL_FROM_STEPS = "SELECT * FROM steps";

    public HashMap<String, Integer> getAllFromStepsConcat() {
        ResultSet rs = readFromDB(GET_ALL_FROM_STEPS);
        HashMap<String, Integer> hs = new HashMap<>();
        try {
            while (rs.next()) {
                String nameCaseAndTimestamp = rs.getString(1) + rs.getInt(2) + rs.getLong(3);
                hs.put(nameCaseAndTimestamp, rs.getInt(4));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hs;
    }
}
