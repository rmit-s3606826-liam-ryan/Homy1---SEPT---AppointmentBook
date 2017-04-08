package users;

import java.time.LocalDate;

public class User
{
	private int id = 0;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDate dob;
    
    public User(int id, String username, String password, String email, String fullName, String phoneNumber, LocalDate dob)
    {
    	this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }
    
    public int getID()
    {
    	return id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getEmail()
    {
    	return email;
    }
    
    public String getFullName()
    {
    	return fullName;
    }
    
    public String getPhoneNumber()
    {
    	return phoneNumber;
    }
    
    public LocalDate getDOB()
    {
    	return dob;
    }
}
