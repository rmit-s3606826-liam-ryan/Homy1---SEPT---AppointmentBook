package Bookings;

public class timeSlots {
	private String Date; 
	private String time; 
	private String employee; 
	private boolean booked = false; 
	
	timeSlots(String Date, String time, String employee){
		this.Date=Date; 
		this.time= time; 
		this.employee=employee;
	}
	
	String getDate(){
		return Date; 
	}
	
	String getTime(){
		return time; 
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
