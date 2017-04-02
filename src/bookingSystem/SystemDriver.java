package bookingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

import com.sun.xml.internal.ws.util.StringUtils;

import users.User;

/** System driver class - contains menus and functions used to run the system */
public class SystemDriver
{
	private Scanner keyboard = new Scanner(System.in);
    private String customerInfoFileName = "src/users/customerinfo";

    /** list to hold user data (may use one list for all 
     * people type objects customer/owner/employee and differentiate with a field) */
	List<User> userList = new ArrayList<User>();
	
	User authUser = null;	// TODO Add logout options to menus?
	
	public SystemDriver()
	{
		
	}
	
	/** loads the system at start up, call functions to load users currently
	 *  will be used to load all data
	 */
    public void loadSystem()
    {
        loadFromFile(customerInfoFileName);
        registerAndLogin();
    }
	
    /** boolean running keeps menus looping until quit is selected */
	static Boolean running = true;
	
	public void registerAndLogin()
	{
		while (running)
		{
			System.out.println("======================\n"
							 + "1. Log In\n"
							 + "2. Register\n"
							 + "3. Quit\n"
							 + "4. owner menu (testing)\n"
							 + "5. customer menu (testing)\n"
							 + "6. show currently authenticated user (testing)\n");
			
			int answer = Integer.parseInt(keyboard.nextLine());
			
			switch (answer)
			{
			case 1: login();                   break;
			case 2: register();                break;
			case 3: running = false;           break;
			case 4: ownerMenu();               break;
			case 5: customerMenu();            break;
			case 6: printCurrentUser();        break;
			default: System.out.println("no"); break;
			}
		}
	}
	
	private void printCurrentUser()
	{
		User u = getAuthUser();
		System.out.println("======================\n"
				 + "Current User: ");
		if (u != null)
		{
			System.out.println(u.getName() + "\n");
		}
		else
		System.out.println("NONE\n");
	}
	
  /** Customer specific menu, user sent here when valid customer account used*/
	private void customerMenu()
	{
		while (running)
		{
			System.out.println("======================\n"
							 + "1. View Bookings\n"
							 + "2. Make Booking\n"
							 + "3. Quit\n"
							 + "4. register/login menu (testing)\n"
							 + "5. owner menu (testing)\n");
			
			int answer = Integer.parseInt(keyboard.nextLine());
			
			switch (answer)
			{
			case 1: viewCoustomerBooking();    break;
			case 2: addBooking();              break;
			case 3: running = false;           break;
			case 4: registerAndLogin();        break;
			case 5: customerMenu();            break;
			default: System.out.println("no"); break;
			}
		}
	}

	/** Owner specific menu - only accessible with owner user name and password */
	private void ownerMenu()
	{
		while (running)
		{
			System.out.println("======================\n"
							 + "1. View Bookings\n"
							 + "2. View Employees\n"
							 + "3. Add Employee\n"
							 + "4. Remove Employee\n"
							 + "5. Quit\n"
							 + "6. ...\n"
							 + "7. register/login menu (testing)\n"
							 + "8. customer menu (testing)\n");
			
			int answer = Integer.parseInt(keyboard.nextLine());
			
			switch (answer)
			{
			case 1: viewBooking();             break;
			case 2: viewEmployee();            break;
			case 3: addEmployee();             break;
			case 4: removeEmployee();          break;
			case 5: running = false;           break;
			case 6: superSecretMenu();         break;
			case 7: registerAndLogin();        break;
			case 8: customerMenu();            break;
			default: System.out.println("no"); break;
			}
		}
	}

	/** super secret menu... 'nuff said */
	private void superSecretMenu()
	{
		while (running)
		{
			System.out.println("======================\n"
							 + "1. Quit\n"
							 + "2. ...\n"
							 + "3. Profit\n"
							 + "4. owner menu (testing)"
							 + "5. register/login menu (testing)\n"
							 + "6. customer menu (testing)\n");
			
			int answer = Integer.parseInt(keyboard.nextLine());
			
			switch (answer)
			{
			case 1: running = false;           break;
			case 4: ownerMenu();               break;
			case 5: registerAndLogin();        break;
			case 6: customerMenu();            break;
			default: System.out.println("no"); break;
			}
		}
	}

	private void addBooking()
	{
		// TODO Auto-generated method stub
		
	}

	private void viewCoustomerBooking()
	{
		// TODO Auto-generated method stub
		
	}

	private void removeEmployee()
	{
		// TODO Auto-generated method stub
		
	}

	private void addEmployee()
	{
		// TODO Auto-generated method stub
		
	}

	private void viewEmployee()
	{
		// TODO Auto-generated method stub
		
	}

	private void viewBooking()
	{
		// TODO Auto-generated method stub
		
	}

	private void register()
	{
		// Prevent duplicate registrations, check for existing username entries
		
		String username, password, email, fullName, phoneNumber, DOB, confirmPassword = null;
		System.out.println("======================");
		boolean validUsername = false;
		do{
		System.out.println("Enter Username: ");
		username = keyboard.nextLine();
			if (username.matches("[a-zA-Z0-9]+")) //need a regex that accepts only alphanumeric only
			{
				validUsername = true;
		}
			else{
				System.out.println("invalid character, please insert only letters and numbers");
				validUsername = false;	
			}
		}
		while (validUsername == false);
		
		/////////////
		boolean samePassword = false;
		do{
		System.out.println("\nEnter password: ");
		password = keyboard.nextLine();
		
		System.out.println("\nConfirm password: ");
		confirmPassword = keyboard.nextLine();
		
		if (password.equals(confirmPassword))
		{
			samePassword = true;
		}
		else{ 
			System.out.println("Passwords do not match");
			samePassword = false;
		}
		}
		while (samePassword == false);
		///////////////	
		boolean validEmail = false;
	/*	do{ 
		System.out.println("\nEnter email address: ");
		email = keyboard.nextLine();
		if (email.matches("[a-zA-Z0-9]+")) //need a regex that accepts  alphanumeric + a few special characters
		{
			validEmail = true;
	}
		else{
			System.out.println("invalid email address");
		}
			validEmail = false;
		}
		while (validEmail == false); */
		///////////
		email = "ok"; //temporary 
		boolean validName = false; 
		do{
		System.out.println("\nEnter full name: ");
		fullName = keyboard.nextLine();
		if (fullName.matches("[a-zA-Z]+")){
			validName = true;
			System.out.println("debug1");
		}
		else
		{
			System.out.println("Please use only alphabetical letters in your name");
			validName = false;			
		}
		}
		while (validName == false);
		////////////
		boolean invalidNumber = false;
		do{
		System.out.println("\nEnter phone number: ");
		phoneNumber = keyboard.nextLine();
		if (phoneNumber.matches("[0-9]+") == false || phoneNumber.length() < 6){
			System.out.println("Please enter a valid phone number!");
			System.out.println(phoneNumber.matches("[0-9]+"));
			invalidNumber = false;
		}
		else
		{
			invalidNumber = true;
		}
		}
		while (invalidNumber == false);
		////////////
		boolean invalidDate = false;
		do{
		int day = 0, month = 0, year = 0;
		boolean invalidDay = false;
		boolean invalidMonth = false;
		boolean invalidYear = false;
		System.out.println("\nEnter date of birth in the order day, month, year: ");
		do{
		System.out.println("day:");

		if (keyboard.hasNextInt() == true)
		{
		day = keyboard.nextInt();
		if( day < 32 && day > 0)
		{
			invalidDay = true;
		}
		else {
			System.out.println("Please insert a value equal to or lower than 31");
			invalidDay = false;
		}
		}
		else {
			System.out.println("Please insert a number");
			day = keyboard.nextInt();
			
		}
		}
		while(invalidDay == false);
		do{
			System.out.println("month: ");
			month = keyboard.nextInt();
			if (month < 13 && month > 0){
				invalidMonth = true;
			}
			else {
				System.out.println("Please insert a value equal to or lower than 12");
				invalidMonth = false;
				year = 4000;
			}
		}
		while(invalidMonth == false);
		do{
			System.out.println("year: " );
			year = keyboard.nextInt();
			if(year < 2018 && year > 0){
				invalidYear = true;
			}
			else {
				System.out.println("Please insert a value equal to or lower than 2018");
				invalidYear = false;
		  	}
		}
		while(invalidYear == false);
		
		DOB = day + "/" + month + "/" + year; 
		invalidDate = true;
		}
		while (invalidDate == false);

		User newUser = new User(username, password, email, fullName, phoneNumber, DOB);
		User userCheck = null;
		boolean invalid = false;
		
		for (int index = 0; !invalid && index < userList.size(); ++index)
		{
			userCheck = userList.get(index);
			invalid = userCheck.getName().equals(newUser.getName());
		}
		if (invalid)
		{
			System.out.println("Username taken");
		}
		else
		{
			userList.add(newUser);
			System.out.println("User Registered. Welcome " + newUser.getName());
			customerMenu();
		}
		

	}

	private void login()
	{
		User authUser = null;
		String username, password = null;
		System.out.println("======================\n"
				 + "Enter username: ");
		username = keyboard.nextLine();
		
		System.out.println("\nEnter password: ");
		password = keyboard.nextLine();
		
		try
		{
			authUser = auth(username,password);
			System.out.println("\nSuccessfully logged in as " + authUser.getName() + ".\n");
			if (authUser.getName().equals("Owner"))
			{
				System.out.println("Directing to Owners menu.");
				ownerMenu();
			}
			else
			{
				customerMenu();
			}
			// Should this check be moved to the menu code, and login() changed to boolean return to check for success?
		}
		catch(AuthException e)
		{
			System.out.println("\nAuthorisation error - " + e.getMessage() + ".\n");
		}
		
		setAuthUser(authUser);
	}
	
	private User auth(String username, String password) throws AuthException
	{
		User user = null;
		
		for (int i = 0; i < userList.size(); i++)
		{
			user = userList.get(i);
			if (user.getName().equals(username))
			{
				if (user.getPassword().equals(password))
					return user;
				break;		// break regardless of correct or incorrect creds, since there won't be another matching
							// username in the userlist with a different p/w. We can split this up if we want
							// a different error msg for (matching user, wrong pw) vs. username doesn't exist.
			}
		}
		throw new AuthException("Invalid credentials");
	}

	/** load data from file - currently loads customer data only, will be refactored to be more general */
    public boolean loadFromFile(String customerInfoFileName)
    {
        Scanner customerInputStream = null;
        
        // TODO add checks for end of file whitespace.
        // with an extra empty line in file, it gets appended to the last user's password
        //and will result in mismatch when authenticating

        try
        {
            customerInputStream = new Scanner(new File(customerInfoFileName));
            customerInputStream.useDelimiter(",");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening " + customerInfoFileName);
            return false;
        }

        while (customerInputStream.hasNextLine())
        {
            String customerName = customerInputStream.next();
            String customerPassword = customerInputStream.nextLine();
            customerPassword = customerPassword.substring(1);
            User newUser = new User(customerName, customerPassword, null, null, null, null);

            userList.add(newUser);
            System.out.println(newUser.getName() + newUser.getPassword());

        }
        customerInputStream.close();
        return true;
    }
    
    public void setAuthUser(User user)
    {
    	authUser = user;
    }
    
    public User getAuthUser()
    {
    	return authUser;
    }

}
