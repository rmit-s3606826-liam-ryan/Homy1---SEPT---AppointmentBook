package Bookings;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.GregorianCalendar;

public class booking {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
	Calendar calendar = new GregorianCalendar(0000,0,00,00,00,00);
	//private String employee; 
	private String customer;
	boolean confirmed = false; 
	
	public booking (Calendar calendar, String customer){
		
		//this.employee = employee;
		this.customer= customer; 
		this.calendar=calendar;
	}
	

	String getCustomer(){
		return customer;
	}
	
	Calendar getDate(){
		return calendar; 
	}
	
	
	void confirm(){
		confirmed = true;
	}
	
}
