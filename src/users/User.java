package users;

import java.sql.Date;

public class User
{
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Date DOB;
    
    public User(String username, String password, String email, String fullName, String phoneNumber, Date DOB)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
    }
    
    public String getName()
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
    
    public Date getDOB()
    {
    	return DOB;
    }
}
