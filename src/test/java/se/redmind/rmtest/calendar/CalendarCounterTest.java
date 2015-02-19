package se.redmind.rmtest.calendar;

import org.junit.Test;

import se.redmind.rmtest.util.CalendarCounter;



public class CalendarCounterTest {
	
	@Test
	public void getCurrentDateAsStringTest(){
		String date = new CalendarCounter().getCurrentDateAsString();
		System.out.println(date);
		
	}
	@Test 
	public void getDateOneMonthAgoAsStringTest(){
		String dateMinus = new CalendarCounter().getDateOneMonthAgoAsString();
		System.out.println(dateMinus);
	}

}
