package se.redmind.rmtest.webservicetests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;
import se.redmind.rmtest.web.route.api.gherkin.GetGherkinStepsWS;
import se.redmind.rmtest.ws.testsuite.WSSetupHelper;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by victormattsson on 2016-02-18.
 */
public class GetGherkinStepsWSTest extends WSSetupHelper{

    @Test
    public void getSteps_true() {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.queryParams("testcase_id")).thenReturn("110");
        when(request.queryParams("timestamp")).thenReturn("20160217135753");

        GetGherkinStepsWS ws = new GetGherkinStepsWS();

        Object result = ws.handle(request, response);
        Gson gson = new Gson();
        JsonArray array = gson.fromJson(result.toString(), JsonArray.class);
        assertEquals(10, array.size());
    }
}
