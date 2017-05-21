package bookingSystem;

import java.util.ArrayList;

public class Business
{
	private String businessName;
	private String ownerName;
	private String address;
	private String phone;
	private String adminUsername;
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
}
