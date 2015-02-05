package se.redmind.rmtest.db.inmemory.test;

import static org.junit.Assert.*;

import org.junit.Test;

import se.redmind.rmtest.db.InMemoryDBHandler;

public class InitInmemoryTest {

	@Test
	public void test() {
		new InMemoryDBHandler().init();
	}
	
}
