package se.redmind.rmtest.web.route.api.gherkin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import se.redmind.rmtest.db.DBBridge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victormattsson on 2016-02-15.
 */
public class GetGherkinStepsDAO extends DBBridge {

    public String getSteps(int id, String timeStamp) {
        JsonArray array = new JsonArray();
        List<HashMap<String, Object>> stepsFromTestcaseId = getStepsFromTestcaseId(id, timeStamp);
        for (HashMap<String, Object> hashMap : stepsFromTestcaseId) {
            JsonObject steps = new JsonObject();
            steps.add("stepname", new JsonPrimitive(hashMap.get("stepname").toString()));
            steps.add("result", new JsonPrimitive(hashMap.get("result").toString()));
            array.add(steps);
        }
        return new Gson().toJson(array);
    }

    private List<HashMap<String, Object>> getStepsFromTestcaseId(int id, String timeStamp) {
        String GET_STEP_FROM_TESTCASE_ID = "SELECT stepname, result FROM steps WHERE testcase_id = ";
        String AND_TIMESTAMP = " AND timestamp = ";
        ResultSet rs = readFromDB(GET_STEP_FROM_TESTCASE_ID + id + AND_TIMESTAMP + timeStamp);
        List<HashMap<String, Object>> result = new ArrayList<>();
        try {
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("stepname", rs.getString("stepname"));
                row.put("result", rs.getString("result"));
                result.add(row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

}
