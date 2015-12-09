package se.redmind.rmtest.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeEstimatorTest {

	int ticks = 100;
	int range = 10;
	
	@Test
	public void getMeterTest() throws InterruptedException{
		TimeEstimator est = new TimeEstimator(ticks);
		est.start();
		String topMeter = est.getTopMeter();
		System.out.println(topMeter);
		System.out.print(" ");
		for (int i = 0; i < ticks; i++) {
			est.addTick();
			est.meassure();
		}
		assertEquals(topMeter.length(), est.totalDone+2);
	}
	
	@Test
	public void timeIncreaseTest(){
		TimeEstimator est = new TimeEstimator(10);
		double increase = est.getIncrease(100,10);
		assertEquals(0.1, increase, 0);
	}
}
