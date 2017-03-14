package bookingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import users.User;

public class SystemDriver
{
	private Scanner keyboard = new Scanner(System.in);
	
	List<User> userList = new ArrayList<User>();
	
	User authUser = null;	// TODO Add logout options to menus?
	
	public SystemDriver()
	{
		
	}
	
	static Boolean running = true;
	
	public void registerAndLogin()
	{
		loadFromFile("customerinfo");
		
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
		// TODO Auto-generated method stub
		
		// Prevent duplicate registrations, check for existing username entries
		
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

    public boolean loadFromFile(String customerInfoFileName)
    {
        Scanner customerInputStream = null;
        
        // TODO add checks for end of file whitespace.
        // with an extra empty line in file, it gets appended to the last user's password
        //and will result in mismatch when authenticating

        try
        {
        	File f = new File(getClass().getResource("../users/"+customerInfoFileName).getFile());
            customerInputStream = new Scanner(f);
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
            String customerPassword = customerInputStream.next();
            User newUser = new User(customerName, customerPassword);
            userList.add(newUser);

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
