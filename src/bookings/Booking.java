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
	
	public User getCustomer()
	{
		return customer;
	}
	
	public Employee getEmployee()
	{
		return employee;
	}
	
	public Timeslot getTimeslot()
	{
		return timeslot;
	}
	
	public String getService()
	{
		return service;
	}
	
	public int getDuration()	// Return duration of appointment, in minutes.
	{							// Should be multiples of 30 (half-hour intervals)
		return duration;		// Not set in stone, can do different intervals or
								//different system completely
	}
	
	void confirm()
	{
		confirmed = true;
	}
}
