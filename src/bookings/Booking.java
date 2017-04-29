package bookings;

import bookingSystem.Service;
import users.Employee;
import users.User;

public class Booking {
	private int id = 0;
	private User customer = null;
	private Employee employee = null;
	private Timeslot timeslot = null;
	private Service service = null;
	boolean confirmed = false;
	
	/*public Booking (User c, Employee e, Timeslot t)
	{
		customer = c;
		employee = e;
		timeslot = t;
	}*/
	
	public Booking (int id, User c, Employee e, Timeslot t, Service s)
	{
		this.id = id;
		customer = c;
		employee = e;
		timeslot = t;
		service = s;
	}
	
	public int getID()
	{
		return id;
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
	
	public Service getService()
	{
		return service;
	}
	
	void confirm()
	{
		confirmed = true;
	}
}
