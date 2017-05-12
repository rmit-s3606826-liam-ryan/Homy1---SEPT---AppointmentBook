package bookingSystem;

import java.util.ArrayList;

public class Business
{
	private String businessName;
	private String adminPassword;
	private String adminUsername;
	ArrayList<Service> service = new ArrayList<>();
	
	public Business(String businessName, String adminUsername, String adminPassword)
	{
		this.businessName = businessName;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
	}

}
