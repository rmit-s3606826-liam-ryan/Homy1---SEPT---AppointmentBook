package bookingSystem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Class for validating user input from registration
 * returns boolean value if input is valid
 **/
public class RegistrationValidation
{
	@FXML
	private TextField txtUsername;
	@FXML
	private Label invUsername;
	
	public RegistrationValidation() {}
    boolean validateUsername(ActionEvent event)
    {
        boolean validUsername = txtUsername.getText().matches("[a-zA-Z0-9'., -]+");
        if (!validUsername) //need a regex that accepts only alphanumeric only
        {
	        invUsername.setTextFill(Color.FIREBRICK);
	        invUsername.setText("Invalid Username");				
	    }
		else
		{
	        invUsername.setText("");				
		}
        return validUsername;
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
