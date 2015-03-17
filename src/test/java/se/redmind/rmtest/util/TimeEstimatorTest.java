package se.redmind.rmtest.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeEstimatorTest {

	int ticks = 1000;
	int range = 100;
	
	@Test
	public void test() throws InterruptedException {
		TimeEstimator est = new TimeEstimator(ticks, range);
		est.start();
		for (int i = 0; i < ticks; i++) {
			est.addTick();
			if (est.isMeassure()) {
				System.out.println(est.getEstimatedTimeLeftDouble());
			}
			Thread.sleep(1);
		}
	}
	
	@Test
	public void getMeterTest() throws InterruptedException{
		TimeEstimator est = new TimeEstimator(ticks, range);
		est.start();
		System.out.println(est.getTopMeter());
		System.out.print("|");
		for (int i = 0; i < ticks; i++) {
			est.addTick();
			if (est.isMeassure()) {
				System.out.print(".");
			}
			Thread.sleep(1);
		}
	}
	
	@Test
	public void sampleTest(){
		assertEquals(1, 1);
	}

}
