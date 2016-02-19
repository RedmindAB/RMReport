package se.redmind.rmtest.db.create.gherkinstepinserter;

import se.redmind.rmtest.db.DBBridge;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 * Created by victormattsson on 2016-02-11.
 */
public class GherkinStepInserter extends DBBridge {

    private final static String INSERT_STEP = "INSERT INTO steps (stepname,testcase_id,timestamp,step_id,result) " +
            "VALUES " + "('{stepname}',{testcase_id},{timestamp},(SELECT IFNULL(MAX(step_id), 0) + 1 FROM steps), " +
            "'{result}')";
    private Statement statement;
    boolean batchNotEmpty;

    public GherkinStepInserter() {
        createStatement();
    }

    protected void createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addStepsToBatch(Integer classID, List<String> gherkinSteps, long timestamp) {
        HashMap<String, String> map = new HashMap<>();
        for (String step : gherkinSteps) {
            String result = "passed";
            if (step.contains("@ThisStepFailed@")) {
                step = step.replaceAll("@ThisStepFailed@", "");
                result = "failure";
            } else if (step.contains("@ThisStepSkipped@")) {
                step = step.replaceAll("@ThisStepSkipped@", "");
                result = "skipped";
            }
            map.put("stepname", step);
            map.put("testcase_id", "" + classID);
            map.put("timestamp", "" + timestamp);
            map.put("result", result);
            String sql = stringParser.getString(INSERT_STEP, map);
            try {
                statement.addBatch(sql);
                batchNotEmpty = true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public int executeBatch() {
        if (batchNotEmpty) {
            try {
                return statement.executeBatch().length;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }
}
