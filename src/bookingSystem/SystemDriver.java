package bookingSystem;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.*;

import users.Employee;
import users.User;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * System driver class - contains menus and functions used to run the system
 **/
public class SystemDriver
{
    private Scanner keyboard = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger("SystemDriver");

    User authUser = null; // TODO Add logout options to menus?

    public SystemDriver()
    {

    }

    /**
     * loads the system at start up, call functions to load users currently will
     * be used to load all data
     **/
    public void loadSystem()
    {
    	Database.loadFromDB();

        registerAndLogin();
    }


    /**
     * boolean running keeps menus looping until quit is selected
     **/
    static Boolean running = true;

    /**
     * Simple Switch statement menu for registration and login
     */
    public void registerAndLogin()
    {
        while (running)
        {
            try
            {
                System.out.println("======================\n" 
                                 + "1. Log In\n" 
                                 + "2. Register\n" + "3. Quit\n"
                                 + "4. owner menu (testing)\n" 
                                 + "5. customer menu (testing)\n"
                                 + "6. show currently authenticated user (testing)\n" 
                                 + "7. Logout\n"
                                 + "8. Adam's test hut\n");

                int answer = Integer.parseInt(keyboard.nextLine());

                switch (answer)
                {
                case 1:  login();                  break;
                case 2:  register();               break;
                case 3:  running = false;          break;
                case 4:  ownerMenu();              break;
                case 5:  customerMenu();           break;
                case 6:  printCurrentUser();       break;
                case 7:  logout();                 break;
                case 8: adamTest();                break;
                default: System.out.println("no"); break;
                }
            }

            catch (NumberFormatException e)
            {
                System.out.println("Please Enter a valid number");
            }
            catch (UserRequestsExitException e)
            {
                System.out.println("User requested exit. Returning to menu...");
            }
        }
    }
    
    public void adamTest() // TODO marker to find code easily
    {
    	String s = "2/13/1988";
    	
    	LocalDate date = parseDate(s);
    	System.out.println("date after parsing: " + date);
    	
    }

    /**
     * Display current user, for now as a test, later maybe for user use
     */
    private void printCurrentUser()
    {
        User user = getAuthUser();
        System.out.println("======================\n" + "Current User: ");
        if (user != null)
        {
            System.out.println(user.getUsername() + "\n");
        }
        else
            System.out.println("NONE\n");
    }

    /**
     * Customer specific menu, user sent here when valid customer account used
     **/
    private void customerMenu()
    {
        while (running)
        {
            try
            {
                System.out.println("======================\n" 
                                 + "1. View 'My' Bookings\n"
                                 + "2. View Available Bookings\n" 
                                 + "3. Make Booking\n" + "4. Log out\n" 
                                 + "5. Quit\n"
                                 + "6. register/login menu (testing)\n" 
                                 + "7. owner menu (testing)\n"
                                 + "8. print current user (testing)");

                int answer = Integer.parseInt(keyboard.nextLine());

                switch (answer)
                {
                case 1:  viewCustomerBooking();    break;
                case 2:  viewAvailableBooking();   break;
                case 3:  addBookingMenu();         break;
                case 4:  logout();
                         registerAndLogin();       break;
                case 5:  running = false;          break;
                case 6:  registerAndLogin();       break;
                case 7:  ownerMenu();              break;
                case 8:  printCurrentUser();       break;
                default: System.out.println("no"); break;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please Enter a valid number");
            }
        }
    }

    /**
     * Owner specific menu - only accessible with owner user name and password
     */
    private void ownerMenu()
    {
        while (running)
        {
            try
            {
                System.out.println("======================\n" 
                                 + "1. View Bookings\n" 
                                 + "2. View Employees\n"
                                 + "3. Add Working Times\n"
                                 + "4. Add Employee\n" 
                                 + "5. Remove Employee\n"
                                 + "6. View employee working times\n"
                                 + "7. Quit\n" 
                                 + "8. Log Out\n"
                                 + "9. register/login menu (testing)\n" 
                                 + "10. customer menu (testing)\n");

                int answer = Integer.parseInt(keyboard.nextLine());

                switch (answer)
                {
                case 1:  viewBooking();              break;
                case 2:  viewEmployee();             break;
                case 3:  addWorkingTimes();          break;
                case 4:  addEmployee();              break;
                case 5:  removeEmployee();           break;
                case 6:  viewEmployeeAvailability(); break;
                case 7:  running = false;            break;
                case 8:  logout();
                         registerAndLogin();         break;
                case 9:  registerAndLogin();         break;
                case 10: customerMenu();             break;
                default: System.out.println("no");   break;
                }
            }
            catch (UserRequestsExitException e)
            {
                System.out.println("User requested exit. Returning to menu...");
            }
            
            catch (NumberFormatException e)
            {
                System.out.println("Please Enter a valid number");
            }
        }
    }

    /**
     * View employees availability - not fully implemented
     **/
    private void viewEmployeeAvailability()
    {
        Connection c = Database.getDBConnection();
        Statement stmt = null;
        try
        {
            stmt = c.createStatement();
            
            System.out.println("======================\n"
                             + "Employee Availability\n"
                             + "======================\n"
                             + "Enter employee name:\n");
            String employee = keyboard.nextLine();
            
<<<<<<< HEAD
            ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE NAME ='" + employee + "'");
            if (rs.next())
            {
                System.out.println(employee + " is available for the following times:\n");
                while (rs.next())
                {
                    System.out.println(rs.getString("date"));
                }
            }
            else
=======
            ResultSet rs = stmt.executeQuery("SELECT * FROM TIMESLOTS WHERE employee ='" + employee + "' AND booked = 'false'");
            System.out.println(employee + " is available for the following times:\n");
            while (rs.next())
>>>>>>> refs/remotes/origin/Adam
            {
                System.out.println("Employee not available");
            }
            c.close();
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Add workers working times - days and hours they work
     */
    private void addWorkingTimes()
    {
        // TODO Auto-generated method stub
        
    }

    /**
     * Menu for making bookings, not fully implemented
     */
    private void addBookingMenu()
    {
        int answer = Integer.parseInt(keyboard.nextLine());

        System.out.println("1. View available bookingtimes\n" + "2. Check if a time and date are available");
        switch (answer)
        {
        case 1:
            displayTimeSlots();
        case 2:
            System.out.println("Please enter the date in yyyy-mm-dd format");
            // need to add a check
        default:
            System.out.println("Not an option, try again");
        }
    }

    // need to fix this
    private void searchTimeSlots(String date)
    {
        Calendar calendar = new GregorianCalendar(0000, 0, 00, 00, 00, 00);
        int Array[] = null; // init new Array(int) instead of null reference?
        StringTokenizer st = new StringTokenizer(date, "-");
        int x = 0;
        while (st.hasMoreTokens())
        {
            Array[x] = Integer.parseInt(st.nextToken());
            x++;
            System.out.println(Array[x]);
        }
        // calendar.set(z, Array[1] ,Array[2]);
    }

    /**
     * displays available time slots
     */
    private void displayTimeSlots()
    {
        for (int x = 0; x < Database.timeslotMap.size(); x++)
        {
            System.out.println(Database.timeslotMap.get(x).getDate() + "\n");

        }
    }
    
    private void addBooking(int customer_id, int employee_id, int timeslot_id, String service, int duration)
    {/* //TODO: all "add" functions require callbacks from DB as they need to add a new entry, auto-generate an ID, then read this back to java
    	// so that we can enter it into the HashMap as the key. A bit fiddly, but manually generating ID is lazy and crappy. I will add this code shortly -Adam
        for (int x = 0; x < Database.timeslotMap.size(); x++)
        {
            if (Database.timeslotMap.get(x).getDate().compareTo(calendar) == 0)
            {
                new Booking(customer_id, employee_id, timeslot_id, service, duration);
            }
            else
            {
                System.out.println("Sorry, no timeslot available for your chosen time");
            }
        }
    }

    private void addTimeSlots(int year, int month, int day, int startHour, int endHour)
    {
        for (int start = startHour; start < endHour; start++)
        {
            Database.timeslotMap.add(new Timeslot(year, month, day, start));
        }*/
    }

    // TODO mock up function - not yet implemented
    private void viewAvailableBooking()
    {/*
        System.out.println("=================================================\n" 
                         + "Available Bookings:\n"
                         + "=================================================\n");
        for (int index = 0; index < Database.timeslotMap.size(); ++index)
        {
            if (Database.timeslotMap.get(index).returnStatus() == false)
            {
                System.out.println(Database.timeslotMap.get(index).getDate() + "-" + Database.timeslotMap.get(index).getEmployee() + "\n");
            }
        }*/
    }

    private void viewCustomerBooking()
    {
        /*Connection c = Database.getDBConnection();
        Statement stmt = null;
        try
        {
            stmt = c.createStatement();

            String currentUser = getAuthUser().getName();
            System.out.println("Welcome " + currentUser);
            System.out.println("You Have a booking(s) with us on:\n");

            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKINGS WHERE username ='" + currentUser + "'");
            while (rs.next())
            {
                System.out.println(rs.getString("date") + " at " + " with " + rs.getString("employee"));
            }
            c.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }*/
    }

    private void removeEmployee()
    {
        System.out.println("Please enter employee ID\n");
        int id = keyboard.nextInt();
        keyboard.nextLine();
        
        Employee employee = Database.employeeMap.get(id);
        System.out.println(
                "Are you sure you wish to remove" + employee.getName() + " from the system? Y/N\n");
        
        if (keyboard.nextLine().equalsIgnoreCase("Y"))
        {
        	try
        	{
				Database.removeEmployeeFromDB(id);
				Database.employeeMap.remove(id);
	            System.out.println("Sucessfully removed \"" + employee.getName() + "\".");
			}
        	catch (SQLException e)
        	{
				System.out.println(e);
			}
            
        }
    }

    private void addEmployee() throws UserRequestsExitException
    {
    	String name = null;
    	Employee newEmployee = null;
        boolean valid = false;
        
        while (valid == false)
        {
            name = promptAndGetString("Please enter new employee's name:\n");
            valid = RegistrationValidation.validateName(name);
            if (valid)
            {
            	for (Employee employee : Database.employeeMap.values())
                {
                	if (employee.getName().equals(name))
                	{
                		System.out.println("Employee already exists");;
                		valid = false;
                	}
                }
            }
        }
        
        try
        {
        	int id = Database.addEmployeeToDB(name);
            newEmployee = new Employee(id, name);
        	Database.employeeMap.put(newEmployee.getID(), newEmployee);
        	System.out.println("\"" + name + "\" has been added as a new employee.");
            customerMenu();
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }

    private void viewEmployee()
    {
        System.out.println("1. View all employees\n" + "2. Search an employee\n");
        String input = keyboard.nextLine();
        if (input.equals("1"))
        {
        	for (Employee employee : Database.employeeMap.values())
        	{
        		System.out.println(employee.getID() + "-" + employee.getName() + "\n");
        	}
        }
        else if (input.equals("2"))
        {
            System.out.println("Enter ID:\n");
            int id = keyboard.nextInt();
            keyboard.nextLine();
            
            Employee employee = Database.employeeMap.get(id);
            System.out.println(employee.getID() + "-" + employee.getName() + "\n");
        }
    }

    /**
     * for viewing future and past bookings, displays all bookings made with in
     * range of seven days before or after the current date
     */
    private void viewBooking()
    {
        Connection c = Database.getDBConnection();
        Statement stmt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        sdf.setLenient(true);
        try
        {

            Calendar currentDate = Calendar.getInstance();
            currentDate.add(Calendar.DATE, 0);

            Calendar lastWeek = Calendar.getInstance();
            lastWeek.add(Calendar.DATE, -7);

            Calendar nextWeek = Calendar.getInstance();
            nextWeek.add(Calendar.DATE, 7);

            stmt = c.createStatement();

            String currentUser = getAuthUser().getUsername();
            System.out.println("Welcome " + currentUser);
            System.out.println("1. View last weeks bookings\n2. View this weeks bookings");
            String input = keyboard.nextLine();

            System.out.println("There are booking(s) for:\n");
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOKINGS");
            while (rs.next())
            {
                // input 1 - view last weeks bookings
                if (input.equals("1"))
                {
                    String databaseDate = rs.getString("date");
                    Date checkDate = sdf.parse(databaseDate);
                    if (checkDate.before(currentDate.getTime()) && checkDate.after(lastWeek.getTime()))
                    {
                        System.out.println(
                                rs.getString("username") + " on " + checkDate + " with " + rs.getString("employee"));
                    }
                }
                //input 2 - view next weeks bookings
                else if (input.equals("2"))
                {
                    String databaseDate = rs.getString("date");
                    Date checkDate = sdf.parse(databaseDate);
                    if (checkDate.before(nextWeek.getTime()) && checkDate.after(currentDate.getTime()))
                    {
                        System.out.println(
                                rs.getString("username") + " on " + checkDate + " with " + rs.getString("employee"));
                    }
                }
                else
                {
                    System.out.println("Invalid Input");
                }
            }

        }
        catch (ParseException e)
        {

            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * login - sets the current authorised user and determines if
     * they are the owner or a customer - directs to appropriate menu
     */
    public void login()
    {
        User authUser = null;
        String username, password = null;
        System.out.println("======================\n" + "Enter username: ");
        username = keyboard.nextLine();

        System.out.println("\nEnter password: ");
        password = keyboard.nextLine();

        try
        {
            authUser = auth(username, password);
            setAuthUser(authUser);
            System.out.println("\nSuccessfully logged in as " + authUser.getUsername() + ".\n");


            if (authUser.getUsername().equals("Owner"))
            {
                System.out.println("Directing to Owners menu.");
                ownerMenu();
            }
            else
            {
                customerMenu();
            }
        }
        catch (AuthException e)
        {
            System.out.println("\nAuthorisation error - " + e.getMessage() + ".\n");
        }

    }

    private User auth(String username, String password) throws AuthException
    {
        User user = getUser(username);
        
        if (user.getPassword().equals(password))
        {
        	return user; // matching username and password, return user object.
        }
        throw new AuthException("Invalid credentials");
    }
    
    /**
     * checks database for the matching user and returns them if found
     **/
    public User getUser(String username)
    {
    	for (User user : Database.userMap.values())
    	{
    		if (user.getUsername().equals(username))
    		{
    			return user;
    		}
    	}
    	return null; // no matching user found
    }

    public void logout()
    {
        setAuthUser(null);
    }

    /**
     * Registration function with simple validation
     * makes calls to validation functions within RegistrationValidation 
     * object
     * @throws UserRequestsExitException
     */
	private void register() throws UserRequestsExitException
	{
        String username = null, password = null, email = null, 
               fullName = null, phoneNumber = null, confirmPassword = null;
        LocalDate dob = null;
        User newUser = null;

        System.out.println("======================\n");

        // valid set to false after each field is correctly entered
        // only single boolean instead of 6
        // prompt and get username
        boolean valid = false;
        while (valid == false)
        {
            username = promptAndGetString("Enter username: ");
            valid = RegistrationValidation.validateUserName(username);
            if (valid)
            {
            	for (User user : Database.userMap.values())
                {
                	if (user.getUsername().equals(username))
                	{
                		System.out.println("Username Taken, Try Again");
                		valid = false;
                	}
                }
            }
        }

        // prompt and get password
        valid = false;
        while (valid == false)
        {
            password = promptAndGetString("Enter password: ");
            confirmPassword = promptAndGetString("Confirm password: ");
            valid = RegistrationValidation.validatePassword(password, confirmPassword);
        }

        // prompt and get email
        valid = false;
        while (valid == false)
        {
            email = promptAndGetString("Enter email address: ");
            valid = RegistrationValidation.validateEmail(email);
        }

        // prompt and get full name
        valid = false;
        while (valid == false)
        {
            fullName = promptAndGetString("Enter full name: ");
            valid = RegistrationValidation.validateName(fullName);
        }

        // prompt and get phone number
        valid = false;
        while (valid == false)
        {
            phoneNumber = promptAndGetString("Enter phone number: ");
            valid = RegistrationValidation.validatePhone(phoneNumber);
        }

        // prompt and get date of birth
        valid = false;
        while(valid == false)
        {
            try
            {
                System.out.println("Enter date of birth: ");
                String dobString = keyboard.nextLine();
                dob = parseDate(dobString);
                
                if (dob != null)
                {
                	valid = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please only enter numbers");
            }
        }
        
        // if user hasn't quit registation and all fields are valid, assign to user
        try
        {
        	int id = Database.addUserToDB(username, password, email, fullName, phoneNumber, dob);
            newUser = new User(id, username, password, email, fullName, phoneNumber, dob);
            Database.userMap.put(newUser.getID(), newUser);
        	System.out.println("User Registered. Welcome " + newUser.getUsername());
            setAuthUser(newUser);
            customerMenu();
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
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

    /**
     * used to keep looping until enough employees/time slots have been added
     **/
    private boolean promptToContinue()
    {
        System.out.println("Any more Inputs (y/n)?");
        String response = keyboard.nextLine();
        return response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes");
    }

    /**
     * used to get user string input - names/ID/password whenever required
     * throws exception when e or exit are entered at any prompt, allows user to
     * quit to main menu at any time
     **/
    private String promptAndGetString(final String PROMPT) throws UserRequestsExitException
    {
        System.out.println(PROMPT);
        String answer = keyboard.nextLine();
        if (answer.equalsIgnoreCase("e") || answer.equalsIgnoreCase("exit"))
        {
            throw new UserRequestsExitException();
        }
        return answer;
    }
    
    public LocalDate parseDate(String dateString)
    /* Parses string into a java.time LocalDate object.
     * Checks size of day and month fields and
     * builds an appropriate formatting pattern.
     * 
     * Works for delimiters:	/ . -
     * 
     * May be a cleaner way to do this, but for now it works well.
     */
    {
    	LocalDate date = null;
    	String[] parts = null;
    	String formatString = "";
    	
    	if (dateString.contains("/"))
        {
        	parts = dateString.split("/");
        	if (parts[0].length() == 1)
    			formatString += "d/";
    		else
    			formatString += "dd/";
        	
        	if (parts[1].length() == 1)
        		formatString += "M/uuuu";

        	else
        		formatString += "MM/uuuu";
        }
        else if (dateString.contains("-"))
        {
        	parts = dateString.split("-");
        	if (parts[0].length() == 1)
    			formatString += "d-";
    		else
    			formatString += "dd-";
        	
        	if (parts[1].length() == 1)
        		formatString += "M-uuuu";
        	else
        		formatString += "MM-uuuu";
        }
        else
        {
        	parts = dateString.split("\\.");
        	if (parts[0].length() == 1)
    			formatString += "d.";
    		else
    			formatString += "dd.";
        	
        	if (parts[1].length() == 1)
        		formatString += "M.uuuu";
        	else
        		formatString += "MM.uuuu";
        }
    	//System.out.println("format string="+formatString);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
    	date = LocalDate.parse(dateString, formatter);
        return date;
    }
}
