package se.redmind.rmtest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarCounter {
	
	Date now = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String dateNow = sdf.format(now);
	int yearNow = Integer.parseInt(dateNow.toString().substring(0, 4));
	int monthNow = Integer.parseInt(dateNow.toString().substring(4, 6));
	int dayNow = Integer.parseInt(dateNow.toString().substring(6, 8));
	GregorianCalendar cal = new GregorianCalendar(yearNow,monthNow-1,dayNow);
	
	
	public String getCurrentDateAsString(){
		return sdf.format(cal.getTime());
	}
	public String getDateOneMonthAgoAsString(){
		cal.add(Calendar.MONTH, -1);
		return sdf.format(cal.getTime());
	}

}
