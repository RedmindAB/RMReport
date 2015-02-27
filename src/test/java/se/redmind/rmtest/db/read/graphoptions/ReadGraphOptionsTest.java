package se.redmind.rmtest.db.read.graphoptions;

import static org.junit.Assert.*;

import org.junit.Test;

import se.redmind.rmtest.web.route.api.stats.grahoptions.GetGraphOptionsDAO;

public class ReadGraphOptionsTest {

	@Test
	public void test() {
		new GetGraphOptionsDAO().getGraphOptions(1, 50);
	}

}
