package se.redmind.rmtest.util;


public class TimeEstimator {
	
	int ticks;
	int currentTick;
	int totalDone;
	int meassureRange;
	long lastTick;
	long startTime;
	long endTime;
	long[] data;
	long[] historyDif;
	
	public TimeEstimator(int size) {
		this.currentTick = 0;
		this.ticks = size;
		this.meassureRange = 100;
		this.data = new long[size];
		this.historyDif = new long[size];
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

	public double getIncrease(long currentTicktime, long lastTicktime) {
		double res = (double) lastTicktime / currentTicktime;
		if (Double.isInfinite(res)) {
			return 1;
		}
		return res;
	}
	
	public double getTimeIncreasePercent(long[] data, int currentTick){
		long total = 0;
		for (int i = 0; i < currentTick; i++) {
			total+= data[i];
		}
		double result = (double)total/currentTick;
		return result;
	}
	
	public void meassure(){
		double percentDone = ((double)currentTick/ticks * 100d);
		if (percentDone == Math.floor(percentDone)) {
			for (int i = 0; i < percentDone-totalDone; i++) {
				System.out.print("#");
			}
			totalDone = (int) percentDone;
		}
	}
	
	public boolean isMeassureTime(){
		int d = (int) ((double) currentTick / ticks * 100d);
//		System.out.println(d);
		return d % 1 == 0;
	}
	
	public String getTopMeter(){
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		int length = meassureRange;
		for (int i = 0; i < length; i++) {
			sb.append("-");
		}
		sb.append("|");
		return sb.toString();
	}
	
}
