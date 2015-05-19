package se.redmind.rmtest.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.testhome.TestHome;

public class ScriptRunnerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void smokeTest() {
		ScriptRunner runner = new ScriptRunner();
		String result = "";
		result = runner.run("echo hej");
		assertEquals("hej", result);
	}
	
	@Test
	public void runMVN(){
		ScriptRunner runner = new ScriptRunner();
		String run = runner.run("cd "+TestHome.main());
		System.out.println(run);
	}
	
}
