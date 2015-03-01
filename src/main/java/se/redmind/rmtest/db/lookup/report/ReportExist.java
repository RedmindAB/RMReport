package se.redmind.rmtest.db.lookup.report;

import java.util.HashSet;

public class ReportExist {
	
	ReportDbLookup dbLookup;
	HashSet<String> suiteTimes = null;
	
	public ReportExist() {
		dbLookup = new ReportDbLookup();
	}
	
	public boolean reportExists(long timestamp, String suitename){
		if (suiteTimes == null) {
			getReports();
		}
		return suiteTimes.contains(timestamp+suitename);
	}
	
	private void getReports(){
		suiteTimes = dbLookup.getReportsWithSuiteNames();
	}
	
}
