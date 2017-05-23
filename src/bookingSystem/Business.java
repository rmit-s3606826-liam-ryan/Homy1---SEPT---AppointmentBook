package bookingSystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Business
{
	private String businessName;
	private String ownerName;
	private String address;
	private String phone;
	private String adminUsername;
    private HashMap<String, LocalTime[]> hours = new HashMap<String, LocalTime[]>();
    private LocalTime earliest = null;
    private LocalTime latest = null;
	ArrayList<Service> services = new ArrayList<Service>();
	
	private static Business business = null;
	
	public Business() { }
	
	public void updateBusiness(String businessName, String ownerName, String address, String phone, String adminUsername)
	{
		this.businessName = businessName;
		this.ownerName = ownerName;
		this.address = address;
		this.phone = phone;
		this.adminUsername = adminUsername;
	}
	
	public void updateBusinessTimes(String dayOfWeek, LocalTime open, LocalTime close)
	{
		LocalTime[] openCloseTimes = {open, close};
		hours.put(dayOfWeek.toLowerCase(), openCloseTimes);
		
		if (earliest == null)
		{
			earliest = open;
		}
		else if (open.isBefore(earliest))
		{
			earliest = open;
		}
		if (latest == null)
		{
			latest = close;
		}
		else if (close.isAfter(latest))
		{
			latest = close;
		}
	}
	
    public static Business getBusiness()
    {
    	if (business == null)
    	{
    		business = new Business();
    	}
    return business;
    }

	public String getName()
	{
		return businessName;
	}
	
	public String getOwnerName()
	{
		return ownerName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getAdminUsername()
	{
		return adminUsername;
	}
	
    public HashMap<String, LocalTime[]> getBusinessHrs()
    {
    	return hours;
    }
    
    public LocalTime getEarliestOpen()
    {
    	return earliest;
    }
    
    public LocalTime getLatestClose()
    {
    	return latest;
    }
}
