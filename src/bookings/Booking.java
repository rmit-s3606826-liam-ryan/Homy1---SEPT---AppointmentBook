package bookings;

import users.Employee;
import users.User;

public class Booking {
	private int id = 0;
	private User customer = null;
	private Employee employee = null;
	private Timeslot timeslot = null;
	private String service = null;
	private int duration = 0;
	boolean confirmed = false;
	
	public Booking (int id, User c, Employee e, Timeslot t, String service, int duration)
	{
		this.id = id;
		customer = c;
		employee = e;
		timeslot = t;
		this.service = service;
		this.duration = duration;
	}
	
	User getCustomer()
	{
		return customer;
	}
	
	Employee getEmployee()
	{
		return employee;
	}
	
	Timeslot getTimeslot()
	{
		return timeslot;
	}
	
	String getService()
	{
		return service;
	}
	
	int getDuration()	// Return duration of appointment, in minutes.
	{					//Should be multiples of 30 (half-hour intervals)
		return duration;
	}
	
	void confirm()
	{
		confirmed = true;
	}
}
