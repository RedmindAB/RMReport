package se.redmind.rmtest.db.read.graphoptions;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReadGraphOptionsTest {

	@Test
	public void test() {
		new ReadGraphOptions().getGraphOptions(1, 50);
	}

}
