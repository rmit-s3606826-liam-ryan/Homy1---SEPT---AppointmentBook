package Bookings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.GregorianCalendar;

public class Timeslot {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
	Calendar calendar = new GregorianCalendar(0000,0,00,00,00,00);
	private String employee;
	private boolean booked = false;
	
	public Timeslot( int year, int month, int day, int hour){
		
		//this.employee=employee;
		
		calendar.set(year, month, day, hour, 00, 00);
		
	}
	
	

	public Calendar getDate(){
		return calendar; 
	}
	
	String getEmployee(){
		return employee;
	}
	
	void cancelBooking(){
		booked = false;
	}
	
	void book(){
		booked = true;
	}
	
	boolean returnStatus(){
		return booked; 
	}
}
