package se.redmind.rmtest.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeEstimatorTest {

	int ticks = 1000;
	int range = 100;
	
	@Test
	public void getMeterTest() throws InterruptedException{
		TimeEstimator est = new TimeEstimator(ticks);
		est.start();
		System.out.println(est.getTopMeter());
		System.out.print(" ");
		for (int i = 0; i < ticks; i++) {
			est.addTick();
			est.meassure();
			Thread.sleep(1);
		}
	}
	
	@Test
	public void timeIncreaseTest(){
		TimeEstimator est = new TimeEstimator(10);
		double increase = est.getIncrease(100,10);
		assertEquals(0.1, increase, 0);
	}
}
