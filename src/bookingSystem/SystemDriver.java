package bookingSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import users.User;

/** System driver class - contains menus and functions used to run the system */
public class SystemDriver
{
	private Scanner keyboard = new Scanner(System.in);
    private String customerInfoFileName = "src/users/customerinfo.dat";

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
	    try
	    {
    		while (running)
    		{
    			System.out.println("======================\n"
    							 + "1. Log In\n"
    							 + "2. Register\n"
    							 + "3. Quit\n"
    							 + "4. owner menu (testing)\n"
    							 + "5. customer menu (testing)\n"
    							 + "6. show currently authenticated user (testing)\n"
    							 + "7. Logout\n");
    			
    			int answer = Integer.parseInt(keyboard.nextLine());
    			
    			switch (answer)
    			{
    			case 1: login();                   break;
    			case 2: register();                break;
    			case 3: running = false;           break;
    			case 4: ownerMenu();               break;
    			case 5: customerMenu();            break;
    			case 6: printCurrentUser();        break;
    			case 7: logout();                  break;
    			default: System.out.println("no"); break;
    			}
    		}
	    }
	    catch (NumberFormatException e)
	    {
	        System.out.println("Please Enter a valid number");
	    }
	    finally
	    {
	        registerAndLogin();
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
		try
		{
    	    while (running)
    		{
    			System.out.println("======================\n"
    							 + "1. View 'My' Bookings\n"
    							 + "2. View Available Bookings\n"
    							 + "3. Make Booking\n"
    							 + "4. Quit\n"
    							 + "5. register/login menu (testing)\n"
    							 + "6. owner menu (testing)\n");
    			
    			int answer = Integer.parseInt(keyboard.nextLine());
    			
    			switch (answer)
    			{
    			case 1: viewCustomerBooking();     break;
    			case 2: viewAvailableBooking();    break;
    			case 3: addBooking();              break;
    			case 4: running = false;           break;
    			case 5: registerAndLogin();        break;
    			case 6: customerMenu();            break;
                case 7: printCurrentUser();        break;
    			default: System.out.println("no"); break;
    			}
    		}
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please Enter a valid number");
        }
        finally
        {
            customerMenu();
        }
	}


	/** Owner specific menu - only accessible with owner user name and password */
	private void ownerMenu()
	{
	    try
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
        catch (NumberFormatException e)
        {
            System.out.println("Please Enter a valid number");
        }
        finally
        {
            ownerMenu();
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

	// TODO mock up function - not yet implemented
	private void viewAvailableBooking()
	{
		System.out.println("=================================================\n"
						 + "Available Bookings\n"
						 + "=================================================\n"
						 + "|     |Mon  |Tue  |Wed  |Thu  |Fri  |Sat  |Sun  |\n"
						 + "|9-10 |     |     |     |     |     |     |     |\n"
						 + "|10-11|     |     |     |     |     |     |     |\n"
						 + "|11-12|     |     |     |     |     |     |     |\n"
						 + "|12-1 |     |     |     |     |     |     |     |\n"
						 + "|1-2  |     |     |     |     |     |     |     |\n"
						 + "|2-3  |     |     |     |     |     |     |     |\n"
						 + "|3-4  |     |     |     |     |     |     |     |\n"
						 + "|4-5  |     |     |     |     |     |     |     |\n");
	}

	// TODO mock up function - not yet implemented
	private void viewCustomerBooking()
	{
		System.out.println("Welcome " + getAuthUser().getName());
		System.out.println("You Have a booking with us at:\n"
						 + "4-5pm Thurday 23/3\n"
						 + "And At:\n"
						 + "1-2pm Friday 24/3\n");
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

	public User addUser(User newUser) throws DuplicateUserException, IOException
	{
		User userCheck = null;
		boolean invalid = false;
		
		for (int index = 0; !invalid && index < userList.size(); ++index)
		{
			userCheck = userList.get(index);
			invalid = userCheck.getName().equals(newUser.getName());
		}
		if (invalid)
		{
			throw new DuplicateUserException("Username already exists!");
		}
		else
		{
			userList.add(newUser);
			writeToFile(newUser.getName() + "," + newUser.getPassword() + "\n", "src/users/customerinfo.dat");
			System.out.println("User Registered. Welcome " + newUser.getName());
			return newUser;
		}
	}
	
	public void register()
	{
		// Prevent duplicate registrations, check for existing username entries
		
		String username, password = null;
		User newUser = null;
		System.out.println("======================\n"
				 + "Enter username: ");
		username = keyboard.nextLine();
		
		System.out.println("\nEnter password: ");
		password = keyboard.nextLine();
		
		newUser = new User(username, password);
		try
		{
			addUser(newUser);
			setAuthUser(newUser);
			customerMenu();
		}
		catch (DuplicateUserException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void login()
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
			// Should this check be moved to the menu code, and login() changed to boolean return to check for success?
		}
		catch(AuthException e)
		{
			System.out.println("\nAuthorisation error - " + e.getMessage() + ".\n");
		}
		
		setAuthUser(authUser);

		if (authUser.getName().equals("Owner"))
        {
            System.out.println("Directing to Owners menu.");
            ownerMenu();
        }
        else
        {
            customerMenu();
        }
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
	
	public void logout()
	{
		setAuthUser(null);
	}

	/** load data from file - currently loads customer data only, will be refactored to be more general */
    public boolean loadFromFile(String customerInfoFileName)
    {
        Scanner customerInputStream = null;
        
        /* TODO add checks for end of file whitespace.
        with an extra empty line in file, it gets appended to the last user's password
        and will result in mismatch when authenticating
        EDIT: This seems to be working? logging in with the last username in file appears to work
        keep an eye on this, may need a fix if it plays up		*/

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
            User newUser = new User(customerName, customerPassword);

            userList.add(newUser);
            //System.out.println(newUser.getName() + "," + newUser.getPassword());
        }
        customerInputStream.close();
        return true;
    }
    
    public void writeToFile(String text, String fileName) throws IOException // (, boolean append)
    {    		// Possibly add an arg switch for append/override? If needed later in project
				// to write whole files at once, we can reuse this method
    	{
    		File f = new File(fileName);
    		FileWriter fw = new FileWriter(f.getAbsoluteFile(), true); // true = append to file
    		BufferedWriter bw = new BufferedWriter(fw);
    		
    		bw.write(text);
    		bw.close();
    	}
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
