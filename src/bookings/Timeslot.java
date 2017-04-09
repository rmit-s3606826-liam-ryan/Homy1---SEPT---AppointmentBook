package bookings;

//import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
//import java.util.Calendar;

//import java.util.GregorianCalendar;

public class Timeslot
{
	/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
	Calendar calendar = new GregorianCalendar(0000,0,00,00,00,00);
	private String employee; */
	private boolean booked = false;
	private int id = 0;
	private LocalDate date = null;
	private LocalTime time = null;
	
	public Timeslot( LocalDate date, LocalTime time, boolean booked)
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
	
	void cancelBooking()
	{
		booked = false;
	}
	
	void book()
	{
		booked = true;
	}
	
	public boolean getStatus()
	{
		return booked; 
	}
}
