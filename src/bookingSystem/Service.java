package bookingSystem;

public class Service
{
	private int id = 0;
	private int duration;
	private String name = null;
	
	public Service (int serviceId, String name, int duration)
	{
		id = serviceId;
		this.name = name;
		this.duration = duration;
	}
	
	public int getDuration()	// Return duration of appointment, in minutes.
	{							// Should be multiples of 30 (half-hour intervals)
		return duration;		// Not set in stone, can do different intervals or
	}							//different system completely
	
	public String getName()
	{
		return name;
	}
}
