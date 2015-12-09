package se.redmind.rmtest.web.route.api.admin.doctor;

import se.redmind.rmtest.testhome.TestHome;
import se.redmind.rmtest.util.ScriptRunner;
import spark.Request;
import spark.Response;
import spark.Route;

public class RunDoctorWS implements Route {


	@Override
	public Object handle(Request request, Response response) {
		ScriptRunner scriptRunner = new ScriptRunner();
		String testHome = TestHome.main();
		String run = scriptRunner.run("sh "+testHome+"/rmdoctor.sh");
		return run;
	}

}
