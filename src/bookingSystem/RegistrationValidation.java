package bookingSystem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Class for validating user input from registration
 * returns boolean value if input is valid
 **/
public class RegistrationValidation
{
    /**
     * Username does not allow special characters
     * Alphanumeric and punctuation
     **/
    static boolean validateUserName(String username)
    {
        boolean validUsername = username.matches("[a-zA-Z0-9'., -]+");
        if (!validUsername) //need a regex that accepts only alphanumeric only
        {
            System.out.println("invalid character, please insert only letters and numbers");
        }
        return validUsername;
    }

    /**
     * Password can be any format
     * function checks whether passwords match
     **/
    static boolean validatePassword(String password, String confirmPassword)
    {
        boolean validPassword = password.equals(confirmPassword);
        if (!validPassword)
        {
            System.out.println("Passwords do not match");
        }
        return validPassword;
    }

    /**
     *  Validate email regex requires format of <alphaNum/punc>@<alphanum/punc>.<alphanum/punc>  
     **/
    static boolean validateEmail(String email)
    {
        boolean validEmail = email.matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+");
        if (!validEmail) //need a regex that accepts  alphanumeric + a few special characters
        {
             System.out.println("invalid email address");
        }
        return validEmail;
    }

    /**
     *  Full name allows only alphabetical characters and common punctuation used in names 
     **/
    static boolean validateName(String fullName)
    {
        boolean validName = fullName.matches("[a-zA-Z'., -]+");
        if (!validName)
        {
            System.out.println("Please use only alphabetical letters in your name");
        }
        return validName;
    }

    /**
     * Currently only requires using between 8 and 10 characters 
     **/
    static boolean validatePhone(String phoneNumber)
    {
        boolean validNumber = phoneNumber.matches("[0-9]{8,10}+");
        if (!validNumber)
        {
            System.out.println("Please enter a valid phone number.");
        }
        return validNumber;
    }
    
    // Check that day of the week matches any of the 7 days,
    // or 5 if company doesn't work weekends (uncomment line below)
    static boolean validateDayOfWeek(String dayOfWeek)
    {
    	String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    	// String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday"};
        boolean validDay = Arrays.asList(days).contains(dayOfWeek.toLowerCase());
        if (!validDay)
        {
            System.out.println("Please enter a valid day.");
        }
        return validDay;
    }
    
    static LocalTime validateTime(String timeString)
    {
    	boolean validTime = false;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    	LocalTime time = null;
    	
    	try
    	{
    		time = LocalTime.parse(timeString, formatter);
    		validTime = true;
    	}
    	catch (Exception e)
    	{
    		// Do nothing, leave validTime = false.
    	}
    	
        if (!validTime)
        {
            System.out.println("Please enter a valid time.");
        }
        return time;
    }
}
