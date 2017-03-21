package users;

public class Owner
{
	private String name;
	private String password;
	private String businessName;
	
	public Owner(String name, String password, String businessName)
	{
		this.name = name;
		this.password = password;
		this.businessName = businessName;
	}
	public String getName()
	{
		return name;
	}
	public String getPassword()
	{
		return password;
	}
	public String getBusinessName()
	{
		return businessName;
	}
	
}
