package se.redmind.rmtest.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Calendar {
	
	public String getCurrentDateAsString(){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNow = sdf.format(now);
		int yearNow = Integer.parseInt(dateNow.toString().substring(0, 4));
		int monthNow = Integer.parseInt(dateNow.toString().substring(4, 6));
		int dayNow = Integer.parseInt(dateNow.toString().substring(6, 8));
		System.out.println(yearNow);
		System.out.println(monthNow);
		System.out.println(dayNow);
		GregorianCalendar cal = new GregorianCalendar(yearNow,monthNow-1,dayNow);
		
		return sdf.format(cal.getTime());
	}

}
