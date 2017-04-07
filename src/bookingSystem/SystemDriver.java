package bookingSystem;

import java.io.*;
import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import java.util.StringTokenizer;
import java.util.logging.*;

import java.util.regex.*;

import com.sun.xml.internal.ws.util.StringUtils;

import Bookings.Booking;
import Bookings.Timeslot;
import Bookings.Booking;
import Bookings.Timeslot;
import users.Employee;
import users.User;

import org.h2.jdbcx.JdbcDataSource;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

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

        loadFromFile(customerInfoFileName);
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
                                 + "7. Logout\n");

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

    private void printCurrentUser()
    {
        User u = getAuthUser();
        System.out.println("======================\n" + "Current User: ");
        if (u != null)
        {
            System.out.println(u.getName() + "\n");
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
        for (int x = 0; x < Database.timeslotList.size(); x++)
        {
            System.out.println(Database.timeslotList.get(x).getDate() + "\n");

        }
    }

    private void addBooking(int year, int month, int day, int startHour, String Customer)
    {
        Calendar calendar = new GregorianCalendar(year, month, day, startHour, 00, 00);
        for (int x = 0; x < Database.timeslotList.size(); x++)
        {
            if (Database.timeslotList.get(x).getDate().compareTo(calendar) == 0)
            {
                new Booking(calendar, Customer);
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
            Database.timeslotList.add(new Timeslot(year, month, day, start));
        }
    }

    // TODO mock up function - not yet implemented
    private void viewAvailableBooking()
    {
        System.out.println("=================================================\n" 
                         + "Available Bookings for" + " \n"
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

    private void viewCustomerBooking()
    {
        Connection c = Database.getDBConnection();
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
        }
    }

    private void removeEmployee()
    {
        System.out.println("Please enter employee ID\n");
        String ID = keyboard.nextLine();
        for (int x = 0; x < Database.employeeList.size(); x++)
        {
            if (Database.employeeList.get(x).getID().equals(ID))
            {
                System.out.println(
                        "Are you sure you wish to remove" + Database.employeeList.get(x).getName() + " from the system? Y/N\n");

                if (keyboard.nextLine().equals("Y"))
                {
                    Database.employeeList.remove(x);
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

        Database.employeeList.add(new Employee(name, id));
        System.out.println(name + "has sucessfully been created");
    }

    private void viewEmployee()
    {
        System.out.println("1. View all employees\n" + "2. Search an employee\n");
        String input = keyboard.nextLine();
        if (input.equals("1"))
        {
            for (int x = 0; x < Database.employeeList.size(); x++)
            {
                System.out.println(Database.employeeList.get(x).getID() + "-" + Database.employeeList.get(x).getName() + "\n");

            }
        }
        else if (input.equals("2"))
        {
            System.out.println("Enter ID\n");
            String ID = keyboard.nextLine();
            for (int x = 0; x < Database.employeeList.size(); x++)
            {
                if (Database.employeeList.get(x).getID().equals(ID))
                {
                    System.out.println(Database.employeeList.get(x).getID() + "-" + Database.employeeList.get(x).getName() + "\n");
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

            String currentUser = getAuthUser().getName();
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

        for (int index = 0; !invalid && index < Database.userList.size(); ++index)
        {
            userCheck = Database.userList.get(index);
            invalid = userCheck.getName().equals(newUser.getName());
        }
        if (invalid)
        {
            throw new DuplicateUserException("Username already exists!");
        }
        else
        {
            Database.userList.add(newUser);
            writeToFile(newUser.getName() + "," + newUser.getPassword() + "\n", "src/users/customerinfo.dat");
            System.out.println("User Registered. Welcome " + newUser.getName());
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
            System.out.println("\nSuccessfully logged in as " + authUser.getName() + ".\n");
            // Should this check be moved to the menu code, and login() changed
            // to boolean return to check for success?
        }
        catch (AuthException e)
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

        for (int i = 0; i < Database.userList.size(); i++)
        {
            user = Database.userList.get(i);
            if (user.getName().equals(username))
            {
                if (user.getPassword().equals(password))
                    return user;
                break; // break regardless of correct or incorrect creds, since
                       // there won't be another matching
                       // username in the userlist with a different p/w. We can
                       // split this up if we want
                       // a different error msg for (matching user, wrong pw)
                       // vs. username doesn't exist.
            }
        }
        throw new AuthException("Invalid credentials");
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
               fullName = null, phoneNumber = null, DOB = null, 
               confirmPassword = null;
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
            for (int index = 0; index < Database.userList.size(); ++index)
            {
                User user = Database.userList.get(index);
                if (user.getName().equals(username))
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
                System.out.println("Enter date of birth, Use separate lines for each - day, month, year: ");
                System.out.println("Please enter the day: ");
                int day = Integer.parseInt(keyboard.nextLine());
                System.out.println("Please enter the month: ");
                int month = Integer.parseInt(keyboard.nextLine());
                System.out.println("Please enter the year: ");
                int year = Integer.parseInt(keyboard.nextLine());
                DOB = day + "/" + month + "/" + year;
                valid = validateCurrent.validateDOB(day, month, year);
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

    /**
     * load data from file - currently loads customer data only, will be
     * refactored to be more general
     */
    public boolean loadFromFile(String customerInfoFileName)
    {
        Scanner customerInputStream = null;

        /*
         * TODO add checks for end of file whitespace. with an extra empty line
         * in file, it gets appended to the last user's password and will result
         * in mismatch when authenticating EDIT: This seems to be working?
         * logging in with the last username in file appears to work keep an eye
         * on this, may need a fix if it plays up
         */

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

            Database.userList.add(newUser);
            // System.out.println(newUser.getName() + "," +
            // newUser.getPassword());
        }
        customerInputStream.close();
        return true;
    }

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
