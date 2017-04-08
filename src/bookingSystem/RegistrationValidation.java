package bookingSystem;

import java.time.LocalDate;

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
    boolean validateUserName(String username)
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
    boolean validatePassword(String password, String confirmPassword)
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
    boolean validateEmail(String email)
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
    boolean validateName(String fullName)
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
    boolean validatePhone(String phoneNumber)
    {
        boolean validNumber = phoneNumber.matches("[0-9]{8,10}+");
        if (!validNumber)
        {
            System.out.println("Please enter a valid phone number!");
        }
        return validNumber;
    }

    /**
     * Validation of day may allow some invalid dates through currently
     * and anyone over the age of 118 is in trouble.
     **/
    /* not needed any more
    boolean validateDOB(LocalDate date)
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
