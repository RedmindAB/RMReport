package se.redmind.rmtest.web.route.api.gherkin;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by victormattsson on 2016-02-15.
 */
public class GetGherkinStepsWS implements Route {

    /**
     * @api {get} /gherkin/getgherkinsteps
     * @apiName Get Steps
     * @apiGroup Steps
     *
     * @apiParam {Number} Testcase ID of the test.
     *
     *@apiSuccessExample {json} Success-Response:
     *[
     *	{
     *		stepname: "Given that this is a test"
     *	    result: "passed"
     *	},
     *	{
     *		stepname: "Then this should be a test"
     *	    result: "passed";
     *	},
     *]
     */
    @Override
    public Object handle(Request request, Response response) {
        int testcase_id = Integer.valueOf(request.queryParams("testcase_id"));
        String timeStamp = request.queryParams("timestamp");
        return new GetGherkinStepsDAO().getSteps(testcase_id, timeStamp);
    }
}
