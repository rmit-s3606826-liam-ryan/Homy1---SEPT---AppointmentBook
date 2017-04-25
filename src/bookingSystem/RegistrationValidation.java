package bookingSystem;

import java.time.LocalDate;

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
     * Validation of day may allow some invalid dates through currently
     * and anyone over the age of 118 is in trouble.
     **/
    /* not needed any more
    static boolean validateDOB(LocalDate date)
    {
        boolean validDate = false;
        date
        if(day < 32 && day > 0)
        {
            if (month < 13 && month > 0)
            {
                if (year < 2018 && year > 1900)
                {
                    validDate = true;
                }
            }
        }
        if (!validDate)
        {
            System.out.println("Please enter a valid date");
        }
        return validDate;
    }
    */
}
