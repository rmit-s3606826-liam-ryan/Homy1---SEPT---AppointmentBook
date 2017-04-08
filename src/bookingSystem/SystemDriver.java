package bookingSystem;

import java.io.*;
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

import org.h2.jdbcx.JdbcDataSource;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import bookings.Booking;
import bookings.Timeslot;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private String customerInfoFileName = "src/users/customerinfo.dat";
    private static final Logger logger = Logger.getLogger("SystemDriver");

    /**
     * list to hold user data (may use one list for all people type objects
     * customer/owner/employee and differentiate with a field)
     **/


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
    
    public void adamTest()
    {
        System.out.println("Enter date of birth: ");
        String dobString = keyboard.nextLine();
        DateTimeFormatter slashFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        DateTimeFormatter hyphenFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        DateTimeFormatter dotFormat = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        LocalDate dob = null;
        
        if (dobString.contains("/"))
        {
        	dob = LocalDate.parse(dobString, slashFormat);
        }
        else if (dobString.contains("-"))
        {
        	dob = LocalDate.parse(dobString, hyphenFormat);
        }
        else
        {
        	dob = LocalDate.parse(dobString, dotFormat);
        }
        System.out.println("dob as LocalDate is: "+dob.toString());
    }

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

            catch (NumberFormatException e)
            {
                System.out.println("Please Enter a valid number");
            }
        }
    }

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
            System.out.println(employee + " is available for the following times:\n");
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM TIMESLOTS WHERE employee ='" + employee + "' AND booked = 'false'");
            while (rs.next())
            {
                System.out.println(rs.getString("date"));
            }
            c.close();
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void addWorkingTimes()
    {
        // TODO Auto-generated method stub
        
    }

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
        String ID = keyboard.nextLine();
        for (int x = 0; x < Database.employeeMap.size(); x++)
        {
            if (Database.employeeMap.get(x).getID().equals(ID))
            {
                System.out.println(
                        "Are you sure you wish to remove" + Database.employeeMap.get(x).getName() + " from the system? Y/N\n");

                if (keyboard.nextLine().equals("Y"))
                {
                    Database.employeeMap.remove(x);
                    System.out.println("Sucessfully removed");
                }
            }
        }
    }

    private void addEmployee()
    {
        System.out.println("Please enter new Employees name\n");
        String name = keyboard.nextLine();
        System.out.println("Please enter employee ID\n");
        int id = keyboard.nextInt();
        keyboard.nextLine();
        Employee e = new Employee(name, id);
        Database.employeeMap.put(id, e);
        System.out.println(name + "has sucessfully been created");
    }

    private void viewEmployee()
    {
        System.out.println("1. View all employees\n" + "2. Search an employee\n");
        String input = keyboard.nextLine();
        if (input.equals("1"))
        {
            for (int x = 0; x < Database.employeeMap.size(); x++)
            {
                System.out.println(Database.employeeMap.get(x).getID() + "-" + Database.employeeMap.get(x).getName() + "\n");

            }
        }
        else if (input.equals("2"))
        {
            System.out.println("Enter ID\n");
            String ID = keyboard.nextLine();
            for (int x = 0; x < Database.employeeMap.size(); x++)
            {
                if (Database.employeeMap.get(x).getID().equals(ID))
                {
                    System.out.println(Database.employeeMap.get(x).getID() + "-" + Database.employeeMap.get(x).getName() + "\n");
                }
            }
        }
    }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public User addUser(User newUser) throws DuplicateUserException, IOException
    {
        User userCheck = null;
        boolean invalid = false;

        for (int index = 0; !invalid && index < Database.userMap.size(); ++index)
        {
            userCheck = Database.userMap.get(index);
            invalid = userCheck.getUsername().equals(newUser.getUsername());
        }
        if (invalid)
        {
            throw new DuplicateUserException("Username already exists!");
        }
        else
        {
            Database.userMap.put(null, newUser); //TODO
            writeToFile(newUser.getUsername() + "," + newUser.getPassword() + "\n", "src/users/customerinfo.dat");
            System.out.println("User Registered. Welcome " + newUser.getUsername());
            return newUser;
        }
    }

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
    
    public User getUser(String username)
    {
    	for (User user : Database.userMap.values())
    	{
    		if (user.getUsername() == username)
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
        RegistrationValidation validateCurrent = new RegistrationValidation();
        User newUser = null;

        System.out.println("======================\n");

        // valid set to false after each field is correctly entered
        // only single boolean instead of 6
        // prompt and get username
        boolean valid = false;
        while (valid == false)
        {
            username = promptAndGetString("Enter username: ");
            valid = validateCurrent.validateUserName(username);
            for (int index = 0; index < Database.userMap.size(); ++index)
            {
                User user = Database.userMap.get(index);
                if (user.getUsername().equals(username))
                {
                    System.out.println("Username Taken, Try Again");
                    valid = false;
                }
            }
        }

        // prompt and get password
        valid = false;
        while (valid == false)
        {
            password = promptAndGetString("Enter password: ");
            confirmPassword = promptAndGetString("Confirm password: ");
            valid = validateCurrent.validatePassword(password, confirmPassword);
        }

        // prompt and get email
        valid = false;
        while (valid == false)
        {
            email = promptAndGetString("Enter email address: ");
            valid = validateCurrent.validateEmail(email);
        }

        // prompt and get full name
        valid = false;
        while (valid == false)
        {
            fullName = promptAndGetString("Enter full name: ");
            valid = validateCurrent.validateName(fullName);
        }

        // prompt and get phone number
        valid = false;
        while (valid == false)
        {
            phoneNumber = promptAndGetString("Enter phone number: ");
            valid = validateCurrent.validatePhone(phoneNumber);
        }

        // prompt and get date of birth
        valid = false;
        while(valid == false)
        {
            try
            {
                System.out.println("Enter date of birth: ");
                String dobString = keyboard.nextLine();
                DateTimeFormatter slashFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                DateTimeFormatter hyphenFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                DateTimeFormatter dotFormat = DateTimeFormatter.ofPattern("dd.MM.uuuu");
                
                if (dobString.contains("/"))
                {
                	dob = LocalDate.parse(dobString, slashFormat);
                }
                else if (dobString.contains("-"))
                {
                	dob = LocalDate.parse(dobString, hyphenFormat);
                }
                else
                {
                	dob = LocalDate.parse(dobString, dotFormat);
                }
                valid = true;	// won't reach here unless one of the above three calls parses the string successfully,
                				// in which case the date is valid.
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please only enter numbers");
            }
        }
        
        // if user hasn't quit registation and all fields are valid, assign to user
        try
        {
            newUser = new User(username, password, email, fullName, phoneNumber, null);
            //newUser = new User(username, password, email, fullName, phoneNumber, DOB); //TODO !!! FIX DOB CONSTRUCTOR. need to create appropriate date object.
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
	
	/*
    public boolean loadFromFile(String customerInfoFileName)
    {
        Scanner customerInputStream = null;

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

            Database.userMap.put(null, newUser); // TODO
            // System.out.println(newUser.getName() + "," +
            // newUser.getPassword());
        }
        customerInputStream.close();
        return true;
    }
    
    */

    public void writeToFile(String text, String fileName) throws IOException // (,
                                                                             // boolean
                                                                             // append)
    { // Possibly add an arg switch for append/override? If needed later in
      // project
      // to write whole files at once, we can reuse this method
        {
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f.getAbsoluteFile(), true); // true =
                                                                       // append
                                                                       // to
                                                                       // file
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
}
