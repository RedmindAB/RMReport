package se.redmind.rmtest.db.create.testcaseinserter;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import se.redmind.rmtest.db.DBBridge;

public class TestCaseInserter extends DBBridge {

    private final static String INSERT_SUITE = "INSERT INTO testcase (testcasename,class_id,is_gherkin) VALUES " + "" +
            "('{testcasename}',{classid},{isgherkin})";
    private Statement statement;
    boolean batchNotEmpty;

    public TestCaseInserter() {
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

    public boolean insertTestCase(String testCaseName, int classid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("testcasename", testCaseName);
        map.put("classid", "" + classid);
        String sql = stringParser.getString(INSERT_SUITE, map);
        return insertToDB(sql);
    }

    public void addTestCaseToBatch(String testCaseName, int classid, boolean isGherkin) {
        HashMap<String, String> map = new HashMap<>();
        int gherkin = isGherkin ? 1 : 0;
        map.put("testcasename", testCaseName);
        map.put("classid", "" + classid);
        map.put("isgherkin", "" + gherkin);
        String sql = stringParser.getString(INSERT_SUITE, map);
        try {
            statement.addBatch(sql);
            batchNotEmpty = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
