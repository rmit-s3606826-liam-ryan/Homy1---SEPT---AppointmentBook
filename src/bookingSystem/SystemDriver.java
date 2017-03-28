package bookingSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import users.User;

import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** System driver class - contains menus and functions used to run the system */
public class SystemDriver
{
	private Scanner keyboard = new Scanner(System.in);
    private String customerInfoFileName = "src/users/customerinfo.dat";

    /** list to hold user data (may use one list for all 
     * people type objects customer/owner/employee and differentiate with a field) */
	List<User> userList = new ArrayList<User>();
	
	User authUser = null;	// TODO Add logout options to menus?
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:./db/database";
	private static final String DB_USER = "notSEPTadmin";
	private static final String DB_PASSWORD = "XxX_Pr0-d4nk-passw0rd_XxX";
	
	public SystemDriver()
	{
		
	}
	
	/** loads the system at start up, call functions to load users currently
	 *  will be used to load all data
	 */
    public void loadSystem()
    {
    	initiateDB();
        loadFromFile(customerInfoFileName);
        registerAndLogin();
    }
	
    /** boolean running keeps menus looping until quit is selected */
	static Boolean running = true;
	
	public void initiateDB()
	{
		Connection c = getDBConnection();
		System.out.println("db done..");
		try
		{
			throw new SQLException("test");
		}
		catch (SQLException e)
		{
			
		}
	}
	
	

	// http://www.javatips.net/blog/h2-database-example
	 private static Connection getDBConnection() {
	        Connection dbConnection = null;
	        try
	        {
	            Class.forName(DB_DRIVER);
	        }
	        catch (ClassNotFoundException e)
	        {
	            System.out.println(e.getMessage());
	        }
	        try
	        {
	            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
	            return dbConnection;
	        }
	        catch (SQLException e)
	        {
	            System.out.println(e.getMessage());
	        }
	        return dbConnection;
	    }
	 
	 private static void insertWithPreparedStatement() throws SQLException {
	        Connection c = getDBConnection();
	        try
	        {
	           // TODO
	        }
	        /*catch (SQLException e)
	        {
	            System.out.println(e.getMessage());
	        }*/
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            c.close();
	        }
	    }
	
	
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
			case 1: viewCustomerBooking();     break;
			case 2: addBooking();              break;
			case 3: running = false;           break;
			case 4: registerAndLogin();        break;
			case 5: customerMenu();            break;
            case 6: printCurrentUser();        break;
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

	private void viewCustomerBooking()
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
