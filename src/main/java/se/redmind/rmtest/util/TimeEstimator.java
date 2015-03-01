package se.redmind.rmtest.util;

import javax.security.auth.callback.LanguageCallback;

public class TimeEstimator {
	
	int ticks;
	int currentTick;
	int meassureRange;
	long lastTick;
	long startTime;
	long endTime;
	long[] data;
	
	public TimeEstimator(int ticks, int meassureRange) {
		this.currentTick = 0;
		this.ticks = ticks;
		this.meassureRange = meassureRange;
		this.data = new long[ticks];
	}
	
	public void start(){
		this.startTime = System.currentTimeMillis();
		this.lastTick = startTime;
	}
	
	public void addTick(){
		long result = System.currentTimeMillis() - lastTick;
		this.data[currentTick] = result;
		currentTick++;
		lastTick = System.currentTimeMillis();
	}
	
	public boolean isMeassure(){
		return currentTick % meassureRange == 0;
	}
	
	public long getEstimatedTimeLeftMills(){
		int start = currentTick-meassureRange;
		if (start<0) {
			return 0;
		}
		long total = 0;
		for (int i = start; i < start+meassureRange; i++) {
//			System.out.println(data[i]);
			total+=data[i];
		}
		long result = (total/meassureRange)*(ticks-currentTick);
		return result;
	}
	
	public double getEstimatedTimeLeftDouble(){
		return (double) getEstimatedTimeLeftMills()/1000;
	}
	
	public boolean isMeassureTime(){
		int d = (int) ((double) currentTick / ticks * 100d);
//		System.out.println(d);
		return d % 1 == 0;
	}
	
	public String getTopMeter(){
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		int length = ticks / meassureRange;
		for (int i = 0; i < length; i++) {
			sb.append("-");
		}
		sb.append("|");
		return sb.toString();
	}
	
}
