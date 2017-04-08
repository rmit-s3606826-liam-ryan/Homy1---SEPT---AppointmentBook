package Bookings;

import java.time.LocalDateTime;

public class Booking {
	/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
	Calendar calendar = new GregorianCalendar(0000,0,00,00,00,00);
	//private String employee;*/
	private int id = 0;
	private int customer_id = 0;
	private int employee_id = 0;
	private int timeslot_id = 0;
	private String service = null;
	private int duration = 0;
	boolean confirmed = false;
	
	public Booking (int customer_id, int employee_id, int timeslot_id, String service, int duration)
	{
		this.customer_id = customer_id;
		this.employee_id = employee_id;
		this.timeslot_id = timeslot_id;
		this.service = service;
		this.duration = duration;
	}
	
	int getCustomerID()
	{
		return customer_id;
	}
	
	int getEmployeeID()
	{
		return employee_id;
	}
	
	void confirm()
	{
		confirmed = true;
	}
}
