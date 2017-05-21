package bookingSystem;

import java.util.ArrayList;

public class Business
{
	private String businessName;
	private String ownerName;
	private String address;
	private String phone;
	private String adminPassword;
	private String adminUsername;
	ArrayList<Service> service = new ArrayList<>();
	
	public Business(String businessName, String ownerName, String address, String phone, String adminUsername, String adminPassword)
	{
		this.businessName = businessName;
		this.ownerName = ownerName;
		this.address = address;
		this.phone = phone;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
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
