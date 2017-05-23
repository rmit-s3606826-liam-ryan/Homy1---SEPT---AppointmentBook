package bookings;

import java.time.LocalDate;
import java.time.LocalTime;

public class Timeslot
{
	private boolean booked = false;
	private int id = 0;
	private LocalDate date = null;
	private LocalTime time = null;
	
	public Timeslot (LocalDate date, LocalTime time, boolean booked)
	{
		this.date = date;
		this.time = time;
		this.booked = booked;
	}
	
	public LocalDate getDate()
	{
		return date; 
	}
	
	public LocalTime getTime()
	{
		return time;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void unBook()
	{
		booked = false;
	}
	
	public void book()
	{
		booked = true;
	}
	// returns if there are any bookings for this timeslot.
	// note there can be multiple bookings for a given timeslot,
	// maximum one for each employee.
	public boolean isBooked()
	{
		return booked; 
	}
}
