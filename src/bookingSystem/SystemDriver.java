package bookingSystem;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.*;

import users.Employee;
import users.User;

import bookings.Booking;
import bookings.Timeslot;
import db.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;

/**
 * System driver class - contains menus and functions used to run the system
 **/
public class SystemDriver
{
    private Scanner keyboard = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger("SystemDriver");
    private static final DateTimeFormatter defaultDateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter defaultTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");

    User authUser = null; // TODO Add logout options to menus?

    public SystemDriver() {}

    /**
     * loads the system at start up, call functions to load users currently will
     * be used to load all data
     **/
    public void loadSystem()
    {
    	Database.extractDbFile();
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
                                 + "4. View employee working times\n"
                                 + "5. Add Employee\n"
                                 + "6. View Employee Availability\n"
                                 + "7. Add Employee Services\n"
                                 + "8. View Services\n"
                                 + "9. Remove Employee\n"
                                 + "10. Quit\n" 
                                 + "11. Log Out\n"
                                 + "12. register/login menu (testing)\n" 
                                 + "13. customer menu (testing)\n");

                int answer = Integer.parseInt(keyboard.nextLine());

                switch (answer)
                {
                case 1:  viewBookings();             break;
                case 2:  viewEmployees();            break;
                case 3:  addWorkingTimes();          break;
                case 4:  viewWorkingTimes();         break;
                case 5:  addEmployee();              break;
                case 6:  viewEmployeeAvailability(); break;
                case 7:  addServices();              break;
                case 8:  viewServices();             break;
                case 9:  removeEmployee();           break;
                case 10:  running = false;           break;
                case 11:  logout();
                         registerAndLogin();         break;
                case 12:  registerAndLogin();        break;
                case 13: customerMenu();             break;
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
    
    private void viewEmployeeAvailability()
    {
    	System.out.println("======================\n"
    						+ "Employee Availability\n"
    						+ "======================\n"
    						+ "Enter employee ID:\n");
    	
    	int input = keyboard.nextInt();
    	keyboard.nextLine();
    	Employee employee = Database.getEmployeeMap().get(input);
    	HashMap<String, LocalTime[]> availability = employee.getAvailability();
    	System.out.println(employee.getName() + " is available for the following times:\n");
    	
    	for (HashMap.Entry<String, LocalTime[]> entry : availability.entrySet())
    	{
    	    String dayOfWeek = entry.getKey();
    	    LocalTime[] times = entry.getValue();
    	    System.out.println(dayOfWeek + ": " + times[0] + " - " + times[1]);
    	}
    }

    /**
     * Add workers working times - days and hours they work
     * @throws UserRequestsExitException 
     * @throws NumberFormatException 
     */
    private void addWorkingTimes() throws NumberFormatException, UserRequestsExitException
    {
    	int id = 0;
    	int employeeId = 0;
    	String dayOfWeek_lowercase = null;
    	String startString = null; // format HH:mm
    	String finishString = null; // format HH:mm
    	LocalTime start = null;
    	LocalTime finish = null;
    	
    	boolean valid = false;
        
        System.out.println("======================\n");
        
        // valid set to true after each field passes the validation
        while (valid == false)
        {
        	employeeId = Integer.parseInt(promptAndGetString("Enter employee id: "));
        	//valid = RegistrationValidation.validateName(name);
        	valid = true;
        }
        
        valid = false;
        while (valid == false)
        {
        	String dayOfWeek = promptAndGetString("Enter day of the week: ");
        	dayOfWeek_lowercase = dayOfWeek.toLowerCase();
        	valid = RegistrationValidation.validateDayOfWeek(dayOfWeek);
        }
        
        valid = false;
        while (valid == false)
        {
        	startString = promptAndGetString("Enter start time (in format HH:MM): ");
        	start = RegistrationValidation.validateTime(startString);
        	if (start != null)
        	{
        		valid = true;
        	}
        }
        
        valid = false;
        while (valid == false)
        {
        	finishString = promptAndGetString("Enter finish time (in format HH:MM): ");
        	finish = RegistrationValidation.validateTime(finishString);
        	if (finish != null)
        	{
        		valid = true;
        	}
        }
        
        try
        {        	
        	// Add entry to DB, return ID, add to local collection
        	id = Database.addWorkingTimesToDB(employeeId, dayOfWeek_lowercase, start, finish); //TODO: Need to validate to check for current working times overlapping, already in system.
        	
        	Employee employee = Database.getEmployeeMap().get(employeeId);
        	employee.addAvailability(dayOfWeek_lowercase, start, finish);
        	System.out.println(dayOfWeek_lowercase + ", " + start + "-" + finish + " has been added to " + employee.getName() + "'s availability.");
        	customerMenu();
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }

    /**
     * Menu for making bookings, not fully implemented
     */
    private void addBookingMenu()
    {
        System.out.println("1. View available bookingtimes\n" + "2. Check if a time and date are available");
        int answer = Integer.parseInt(keyboard.nextLine());
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

    // TODO need to fix this
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
    	for (Timeslot timeslot : Database.getTimeslotMap().values())
    	{
    	    LocalDate date = timeslot.getDate();
    	    LocalTime time = timeslot.getTime();
    	    Boolean booked = timeslot.getStatus();
    	    System.out.println(date.format(defaultDateFormat) + ", " + time.format(defaultTimeFormat) + " - " + "booked = " + booked);
    	}
    } 
    
    private void addBooking() throws NumberFormatException, UserRequestsExitException
    //TODO: VALIDATION NOT DONE. Waiting until this is implemented in gui as many of the fields will be
    // doable with a dropdown box I think, so we won't need to validate manual user input
    
    // also currently uses numerical IDs for all fields which is not user friendly. But you can pull
    // details from the ID using, for example:  Database.getTimeslotMap().get(timeslotId).getTime(),
    // .getDate(), .getStatus() and so on.
    {
    	int bookingId = 0;
    	int employeeId = 0;
    	int customerId = 0;
    	int timeslotId = 0;
    	int serviceId = 0;
    	
    	boolean valid = false;
        
        Booking newBooking = null;
        
        System.out.println("======================\n");
        
        // valid set to true after each field passes the validation
        while (valid == false)
        {
        	employeeId = Integer.parseInt(promptAndGetString("Enter employee id: "));
        	//valid = RegistrationValidation.validateName(name);
        	valid = true;
        }
        
        valid = false;
        while (valid == false)
        {
        	customerId = Integer.parseInt(promptAndGetString("Enter customer id: "));
        	//valid = RegistrationValidation.validatePhone(phone);
        	valid = true;
        }
        
        valid = false;
        while (valid == false)
        {
        	timeslotId = Integer.parseInt(promptAndGetString("Enter timeslot id: "));
        	//valid = RegistrationValidation.validatePhone(phone);
        	valid = true;
        }
        
        valid = false;
        while (valid == false)
        {
        	serviceId = Integer.parseInt(promptAndGetString("Enter service id: "));
        	//valid = RegistrationValidation.validatePhone(phone);
        	valid = true;
        }
        
        try
        {
        	// Get objects according to IDs
        	User c = Database.getUserMap().get(customerId);
        	Employee e = Database.getEmployeeMap().get(employeeId);
        	Timeslot t = Database.getTimeslotMap().get(timeslotId);
        	Service s = Database.getServiceMap().get(serviceId);
        	
        	// Add entry to DB, return booking ID, add to local collection
        	bookingId = Database.addBookingToDB(employeeId, customerId, timeslotId, serviceId);
        	newBooking = new Booking(bookingId, c, e, t, s);
        	
        	Database.getBookingMap().put(newBooking.getID(), newBooking);
        	// TODO: Have not written confirmation output as this will probably done in GUI. Can show the time and day booked, and with which employee, etc.
        	//System.out.println("\"" + name + "\" has been added as a new employee.");
        	customerMenu();
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }

    private void addTimeSlots(int year, int month, int day, int startHour, int endHour)
    {/*
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
    	int id = 0;
    	boolean valid = false;
    	
    	while (true)
    	{
        	try
        	{
        		System.out.println("Please enter employee ID\n");
        		id = keyboard.nextInt(); // may throw InputMismatchException
        		keyboard.nextLine();
        		Employee employee = Database.getEmployeeMap().get(id); // may throw NullPointerException
                System.out.println(
                        "Are you sure you wish to remove " + employee.getName() + " from the system? Y/N");
                
                while (!valid)
                {
                    String k = keyboard.nextLine();
                    if (k.equalsIgnoreCase("Y"))
                    {
                    	Boolean success = Database.removeEmployeeFromDB(id); // may throw SQLException
                    	if (success)
                    	{
                        	Database.getEmployeeMap().remove(id);
                        	System.out.println("Sucessfully removed \"" + employee.getName() + "\".");
                        	ownerMenu();
                    	}
                    	else
                    	{
                    		throw new Exception("Error: Employee currently has bookings and cannot be deleted.");
                    	}
                    }
                    else if (k.equalsIgnoreCase("N"))
                    {
                    	System.out.println("Cancelled remove employee. Returning to menu...");
                    	ownerMenu();
                    }
                    else
                    {
                    	System.out.println("Invalid input - please enter Y or N.");
                    }
                }
        	}
        	catch (InputMismatchException e)
        	{
        		System.out.println("Error: Invalid ID.");
        	}
        	catch (NullPointerException e)
        	{
        		System.out.println("Error: Employee with id=" + id + " doesn't exist!");
        	}
        	catch (SQLException e)
        	{
        		System.out.println(e.getMessage());
        	}
        	catch (Exception e)
        	{
        		System.out.println(e.getMessage());
			}
        	finally
        	{
        		keyboard.nextLine(); // Remove trailing endline char, even if exception is thrown
        	}
    	}
    }

    private void addEmployee() throws UserRequestsExitException
    {
    	int id = 0;
    	String name = null;
    	String phone = null;
    	String address = null;
    	
        boolean valid = false;
        
        Employee newEmployee = null;
        
        System.out.println("======================\n");
        
        // valid set to true after each field passes the validation
        while (valid == false)
        {
        	name = promptAndGetString("Enter employee name: ");
        	valid = RegistrationValidation.validateName(name);
        }
        
        valid = false;
        while (valid == false)
        {
        	phone = promptAndGetString("Enter phone number: ");
        	valid = RegistrationValidation.validatePhone(phone);
        }
        
        valid = false;
        while (valid == false)
        {
        	address = promptAndGetString("Enter address: ");
        	// Should we have validation for address? Gets complicated with possible unit numbers etc
        	// containing symbols and various non-standard characters.
        	//valid = RegistrationValidation.validateAddress(address);
        	valid = true;
        }
        
        try
        {
        	id = Database.addEmployeeToDB(name, phone, address);
        	newEmployee = new Employee(id, name, phone, address);
        	Database.getEmployeeMap().put(newEmployee.getID(), newEmployee);
        	System.out.println("\"" + name + "\" has been added as a new employee.");
        	customerMenu();
        }
        catch (SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }
    
    private void viewEmployees()
    {
        System.out.println("1. View all employees\n" + "2. Search an employee\n");
        String input = keyboard.nextLine();
        if (input.equals("1"))
        {
        	for (Employee employee : Database.getEmployeeMap().values())
        	{
        		System.out.println(employee.getID() + "-" + employee.getName() + "\n");
        	}
        }
        else if (input.equals("2"))
        {
            System.out.println("Enter ID:\n");
            int id = keyboard.nextInt();
            keyboard.nextLine();
            
            Employee employee = Database.getEmployeeMap().get(id);
            System.out.println(employee.getID() + "-" + employee.getName() + "\n");
        }
    }

    /**
     * for viewing future and past bookings, displays all bookings made with in
     * range of seven days before or after the current date
     */
    private void viewBookings()
    {
    	String currentUser = "testUser";
    	LocalDate today = LocalDate.now().plusDays(10); // TODO remove +10 days
		LocalDate lastWeek = today.minus(Period.ofDays(7));
		LocalDate nextWeek = today.plus(Period.ofDays(7));
		boolean noResults = true;
    	//String currentUser = getAuthUser().getUsername();
    	System.out.println("Welcome " + currentUser);
    	System.out.println("1. View last week's bookings\n2. View this weeks bookings");
    	String input = keyboard.nextLine();
    	
    	
    	// input 1 - view last weeks bookings
        if (input.equals("1"))
        {
        	System.out.println("The booking(s) for last week were:\n");
        	
        	for (Booking b : Database.getBookingMap().values())
        	{
        		LocalDate date = b.getTimeslot().getDate();
        		if (date.isAfter(lastWeek) && date.isBefore(today))
        		{
        			LocalTime t = b.getTimeslot().getTime();
                    System.out.println(
                            b.getCustomer().getUsername() + " on " + date.format(defaultDateFormat) + " at " + t.format(defaultTimeFormat) + " with " + b.getEmployee().getName());
                    noResults = false;
        		}
        	}
        }
        //input 2 - view next weeks bookings
        else if (input.equals("2"))
        {
        	System.out.println("The booking(s) for next week are:\n");
        	
        	for (Booking b : Database.getBookingMap().values())
        	{
        		LocalDate date = b.getTimeslot().getDate();
        		if (date.isBefore(nextWeek) && date.isAfter(today))
        		{
        			LocalTime t = b.getTimeslot().getTime();
        			DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
                    System.out.println(
                            b.getCustomer().getUsername() + " on " + date.format(defaultDateFormat) + " at " + t.format(tf) + " with " + b.getEmployee().getName());
                    noResults = false;
        		}
        	}
        }
        else
        {
            System.out.println("Invalid Input");
        }
        if (noResults)
        	System.out.println("NONE");
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
        catch (NullPointerException e)
        {
        	System.out.println("Error: User '" + username + "' does not exist.");
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
    	for (User user : Database.getUserMap().values())
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
            	for (User user : Database.getUserMap().values())
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
        	int id = Database.addUserToDB(username, password, email, fullName, phoneNumber);
            newUser = new User(id, username, password, email, fullName, phoneNumber);
            Database.getUserMap().put(newUser.getID(), newUser);
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
    
    public static LocalDate parseDate(String dateString)
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
