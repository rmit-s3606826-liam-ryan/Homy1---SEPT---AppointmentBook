package bookingSystem;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.*;

import users.Employee;
import users.User;

import bookings.Booking;
import bookings.Timeslot;
import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import bookingSystem.NotSeptLogger;

/**
 * System driver class - contains menus and functions used to run the system
 **/
public class SystemDriver
{
	// main scene
	@FXML private Button mainLogin;
	@FXML private Button mainRegister;
	
	//register scene
	@FXML private TextField txtUsername;
	@FXML private Label invUsername;
	@FXML private TextField txtPassword;
	@FXML private TextField txtConfirmPassword;
	@FXML private Label invPassword;
	@FXML private TextField txtEmail;
	@FXML private Label invEmail;
	@FXML private TextField txtPhone;
	@FXML private Label invPhone;
	@FXML private TextField txtName;
	@FXML private Label invName;
	@FXML private Label registerMessage;
	
	// owner menu scene
	@FXML private RadioButton radioNextWeek;
	@FXML private RadioButton radioLastWeek;
	@FXML private TextArea bookingsView;
	@FXML private TextArea empAvailView;
	@FXML private ComboBox<String> empSelect;
	@FXML private ComboBox<String> empSelect2;
	@FXML private ComboBox<String> empSelect3;
	@FXML private ComboBox<String> selectDay;
	@FXML private TextField txtAddEmp;
	@FXML private Label addEmpMessage;
	@FXML private Button empRemoveBut;
	@FXML private Label empRemoveMessage;
	@FXML private Label workTimeMessage;
	@FXML private Button addTimeBut;
	@FXML private TextField txtWorkStart;
	@FXML private TextField txtWorkEnd;
	
	// customer menu scene
	@FXML private ComboBox<String> makeBookingService;
	@FXML private ComboBox<String> makeBookingEmployee;
    @FXML private ComboBox<String> makeBookingDay;
    @FXML private ComboBox<String> makeBookingTime;
    @FXML private Button makeBookingBut;
    @FXML private Label makeBookingMessage;
	@FXML private ComboBox<String> availBookingsEmployee;
    @FXML private ComboBox<String> availBookingsDay;
    @FXML private ComboBox<String> availBookingsService;
	@FXML private TextArea availBookingsView;
    @FXML private Button getTimesBut;
	@FXML private TextArea custBookingsView;
	
	// login scene
	@FXML private TextField txtLoginUsername;
	@FXML private TextField txtLoginPassword;
	@FXML private Label invLoginName;
	@FXML private Label invLoginPass;
	@FXML private Button loginButton;
	
	
	


    private Scanner keyboard = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
		try
		{
			NotSeptLogger.setup();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

    	Database.extractDbFile();
    	Database.loadFromDB();
    	logger.info("database loaded into system");
    }
    public void setUp()
    {
		ObservableList<String> oblist = FXCollections.observableArrayList();
    	for (Employee employee : Database.getEmployeeMap().values())
    	{
    		oblist.add(employee.getName());
    	}

		empSelect.setItems(oblist);
		empSelect2.setItems(oblist);
		empSelect3.setItems(oblist);
		empSelect.getSelectionModel().selectFirst();
		empSelect2.getSelectionModel().selectFirst();
		empSelect3.getSelectionModel().selectFirst();
		
		selectDay.getItems().clear();
		selectDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    	
		logger.info("Owner Menu: Employee/Day Comboboxes refilled");

    }
    
    public void custSetUp()
    {
		ObservableList<String> oblist = FXCollections.observableArrayList();
    	for (Employee employee : Database.getEmployeeMap().values())
    	{
    		oblist.add(employee.getName());
    	}

		availBookingsEmployee.setItems(oblist);
		availBookingsDay.getItems().clear();
		availBookingsDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    	logger.info("Customer Menu: Employee/Day Comboboxes refilled");
    }
    
    public void loadLoginScene(ActionEvent event) throws Exception
    {
		Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/Login.fxml"));
		Scene scene = new Scene(root, 720, 480);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
    	logger.info("Changed to Login Scene");

    }
    
    public void loadRegisterScene(ActionEvent event) throws Exception
    {
		Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/Registration.fxml"));
		Scene scene = new Scene(root, 720, 480);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
    	logger.info("Changed to Register Scene");
    }

    /**
     * boolean running keeps menus looping until quit is selected
     **/
    static Boolean running = true;

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

    
    public void viewEmployeeAvailability()
    {
    	empAvailView.setText("");
    	Employee employee = null;
        for (Entry<Integer, Employee> entry : Database.getEmployeeMap().entrySet())
        {
        	Integer key = entry.getKey();
        	Employee value = entry.getValue();
        	if (empSelect.getValue().equals(value.getName()))
        	{
            	employee = Database.getEmployeeMap().get(key);
            	logger.info("Viewing availability for " + empSelect.getValue());
        	}
        }

        
    	HashMap<String, LocalTime[]> availability = employee.getAvailability();
    	for (HashMap.Entry<String, LocalTime[]> entry : availability.entrySet())
    	{
    	    String dayOfWeek = entry.getKey();
    	    LocalTime[] times = entry.getValue();
    	    empAvailView.appendText(dayOfWeek + ": " + times[0].toString() + " - " + times[1].toString() + "\n");
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
    public void viewAvailableBooking()
    {
    	availBookingsView.setText("");
        for (Timeslot timeslot : Database.getTimeslotMap().values())
        {
            if (availBookingsDay.getValue() != null
             && !availBookingsDay.getValue().equalsIgnoreCase(timeslot.getDate().getDayOfWeek().name()))
            {
            	continue;
            }
            
            if (timeslot.getStatus() != false)
            {
            	continue;
            }
        	availBookingsView.appendText(timeslot.getDate() + " : " + timeslot.getTime() + "\n");
        	
        }
    	logger.info("Viewing bookings for " + availBookingsDay.getValue());
    }

    public void viewCustomerBooking()
    {
    	custBookingsView.setText("");
        for (Booking booking : Database.getBookingMap().values())
        {
        	if (authUser.getID() == booking.getCustomer().getID())
        	{
        		custBookingsView.appendText(
        				  "DATE: "
        				+ booking.getTimeslot().getDate().toString()
        				+ "\nAT: " 
        				+ booking.getTimeslot().getTime().toString()
        				+ "\nFOR: "
        				+ booking.getService()
        				+ "\nWITH: "
        				+ booking.getEmployee().getName()
        				+ "\n==========================\n");	
        	}
        }
        logger.info("Viewing bookings for " + authUser.getUsername());
    }
    public void removeEmployee()
    {
		int id = 0;
		try
		{
			Employee employee = null;
			for (Entry<Integer, Employee> entry : Database.getEmployeeMap().entrySet())
			{
				Integer key = entry.getKey();
				Employee value = entry.getValue();
				if (empSelect3.getValue().equals(value.getName()))
				{
					employee = Database.getEmployeeMap().get(key);
					id = key;
				}
			}

			Boolean success = Database.removeEmployeeFromDB(id); 
			
			if (success)
			{
				logger.info("removed employee " + employee.getName());
				Database.getEmployeeMap().remove(id);
				empRemoveMessage.setText("Sucessfully removed \"" + employee.getName() + "\".");
				setUp();
			}
			else
			{
				logger.warning("failed to remove employee " + employee.getName());

				empRemoveMessage.setText("Error: Employee currently has bookings and cannot be deleted.");
				throw new Exception("Error: Employee currently has bookings and cannot be deleted.");
			}
		}
		catch (SQLException e)
		{
			logger.warning("SQL Exception caught");

			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
    }

    public void addEmployee()
    {
    	String name = null;
    	Employee newEmployee = null;
        boolean valid = true;
        
        name = txtAddEmp.getText();
        //valid = RegistrationValidation.validateName(name);
        for (Employee employee : Database.getEmployeeMap().values())
        {
        	if (employee.getName().equals(name))
            {
        		addEmpMessage.setText("Employee already exists");
                valid = false;
            }
        }
        if (valid)
        {
        	try
        	{
        		int id = Database.addEmployeeToDB(name);
        		newEmployee = new Employee(id, name);
        		Database.getEmployeeMap().put(newEmployee.getID(), newEmployee);
        		addEmpMessage.setText("\"" + name + "\" has been added as a new employee.");
        	}
        	catch (SQLException e)
        	{
        		System.out.println(e.getMessage());
        	}
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
    public void viewBookings()
    {
		ObservableList<String> oblist = FXCollections.observableArrayList();
    	for (Employee employee : Database.getEmployeeMap().values())
    	{
    		oblist.add(employee.getName());
    	}

		empSelect.setItems(oblist);

    	LocalDate today = LocalDate.now(); // TODO remove +10 days
		LocalDate lastWeek = today.minus(Period.ofDays(100));
		LocalDate nextWeek = today.plus(Period.ofDays(7));
		boolean noResults = true;
    	//String currentUser = getAuthUser().getUsername();
    	
    	
    	// input 1 - view last weeks bookings
        if (radioLastWeek.isSelected())
        {
        	bookingsView.setText("");
        	for (Booking booking : Database.getBookingMap().values())
        	{
        		LocalDate date = booking.getTimeslot().getDate();
        		if (date.isAfter(lastWeek) && date.isBefore(today))
        		{
        			LocalTime t = booking.getTimeslot().getTime();
                    bookingsView.appendText(
                            booking.getCustomer().getUsername() + " on " + date.format(defaultDateFormat) + " at " + t.format(defaultTimeFormat) + " with " + booking.getEmployee().getName() + "\n");
                    noResults = false;
        		}
        	}
        }
        //input 2 - view next weeks bookings
        else if (radioNextWeek.isSelected())
        {
        	bookingsView.setText("");
        	for (Booking booking : Database.getBookingMap().values())
        	{
        		LocalDate date = booking.getTimeslot().getDate();
        		if (date.isBefore(nextWeek) && date.isAfter(today))
        		{
        			LocalTime t = booking.getTimeslot().getTime();
        			DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
                    bookingsView.appendText(
                            booking.getCustomer().getUsername() + " on " + date.format(defaultDateFormat) + " at " + t.format(tf) + " with " + booking.getEmployee().getName() + "\n");
                    noResults = false;
        		}
        	}
        }
        else
        {
            System.out.println("Invalid Input");
        }
        if (noResults)
        {
        	bookingsView.setText("NONE");
        }
    }
    
    /**
     * login - sets the current authorised user and determines if
     * they are the owner or a customer - directs to appropriate menu
     * @throws IOException 
     */
    public void login(ActionEvent event) throws IOException
    {
        invLoginPass.setText("");
    	invLoginName.setText("");
        User authUser = null;
        try
        {
            authUser = auth(txtLoginUsername.getText(), txtLoginPassword.getText());
            System.out.println("\nSuccessfully logged in as " + authUser.getUsername() + ".\n");
            setAuthUser(authUser);
            String sceneToDisplay = authUser.getUsername().equals("Owner") 
        				          ? "/bookingSystem/OwnerMenu.fxml"
        				          : "/bookingSystem/CustomerMenu.fxml";
       		
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneToDisplay));
    		Parent root = loader.load();
    		SystemDriver c = loader.getController();
    		c.setAuthUser(authUser);
    		Scene scene = new Scene(root, 720, 480);
    		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		primaryStage.setScene(scene);
        }
        catch (AuthException e)
        {
            invLoginPass.setText("\nAuthorisation error - " + e.getMessage() + ".\n");
        }
        catch (NullPointerException e)
        {
        	invLoginName.setText("Error: User '" + txtLoginUsername.getText() + "' does not exist.");
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

    public void logout(ActionEvent event)
    {
        setAuthUser(null);
        backToMain(event);
    }

    /**
     * Username does not allow special characters
     * Alphanumeric and punctuation
     **/
    public boolean validateUsername(String username)
    {
        boolean validUsername = username.matches("[a-zA-Z0-9'., -]+");
        boolean taken = false;
    	for (User user : Database.getUserMap().values())
    	{
    		if (user.getUsername().equals(username))
    		{	
    			taken = true;
    		}
    	}

        if (!validUsername) //need a regex that accepts only alphanumeric only
        {
	        invUsername.setText(" Invalid Username");				
	    }
        else if (taken)
        {
	        invUsername.setText(" Username Taken");	
	        validUsername = false;
        }
		else
		{
	        invUsername.setText("");				
		}
        return validUsername;
    }
    
    /**
     * Password can be any format
     * function checks whether passwords match
     **/
    public boolean validatePassword(String password, String confirmPassword)
    {
        boolean validPassword = password.equals(confirmPassword);
        if (!validPassword)
        {
	        invPassword.setText(" Passwords do not match");				
	    }
		else
		{
	        invPassword.setText("");				
		}
        return validPassword;
    }

    /**
     *  Validate email regex requires format of <alphaNum/punc>@<alphanum/punc>.<alphanum/punc>  
     **/
    public boolean validateEmail(String email)
    {
        boolean validEmail = email.matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.]{1,1}+[a-zA-Z0-9-.]+");
        if (!validEmail) //need a regex that accepts  alphanumeric + a few special characters
        {
	        invEmail.setText(" Invalid email format");				
	    }
		else
		{
	        invEmail.setText("");				
		}
        return validEmail;
    }
    
    /**
     * Currently only requires using between 8 and 10 characters 
     **/
    public boolean validatePhone(String phone)
    {
        boolean validNumber = phone.matches("[0-9]{8,10}+");
        if (!validNumber)
        {
	        invPhone.setText(" Invalid phone number");				
	    }
		else
		{
	        invPhone.setText("");				
		}
        return validNumber;
    }

    /**
     *  Full name allows only alphabetical characters and common punctuation used in names 
     **/
    public boolean validateName(String name)
    {
        boolean validName = name.matches("[a-zA-Z'., -]+");
        if (!validName)
        {
	        invName.setText(" Like no name I've seen");				
	    }
		else
		{
	        invName.setText("");				
		}
        return validName;
    }


    /**
     * Registration function with simple validation
     * makes calls to validation functions within RegistrationValidation 
     */
	public void register(ActionEvent event) throws Exception
	{
		boolean validUsername = txtUsername.getLength() != 0 ? validateUsername(txtUsername.getText()) : false;
		boolean validPassword = txtConfirmPassword.getLength() != 0 ? validatePassword(txtPassword.getText(), txtConfirmPassword.getText()) : false;
		boolean validEmail = txtEmail.getLength() != 0 ? validateEmail(txtEmail.getText()) : false;
		boolean validPhone = txtPhone.getLength() != 0 ? validatePhone(txtPhone.getText()) : false;
		boolean validName = txtName.getLength() != 0 ? validateName(txtName.getText()) : false;
        
		boolean validRegistration = validUsername 
								 && validPassword
								 && validEmail
								 && validPhone
								 && validName;
		
		try
        {
			if (validRegistration)
			{
				registerMessage.setText("all is well");
				/*
				int id = Database.addUserToDB(txtUsername.getText(),
											  txtPassword.getText(),
											  txtEmail.getText(),
											  txtName.getText(),
											  txtPhone.getText());
            	User newUser = new User(id,
            					   txtUsername.getText(),
            					   txtPassword.getText(),
            					   txtEmail.getText(),
            					   txtName.getText(),
            					   txtPhone.getText());
            	Database.getUserMap().put(newUser.getID(), newUser);
        		System.out.println("User Registered. Welcome " + newUser.getUsername());
        		setAuthUser(newUser);
			*/}
			else
			{
				registerMessage.setText("Invalid Field(s): Can not Register");
			}
        }
        catch (Exception e)
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
    
    public void backToMain(ActionEvent event)
    {
		try
		{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookingSystem/Main.fxml"));
    		Parent root = loader.load();
    		Scene scene = new Scene(root, 720, 480);
    		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		primaryStage.setScene(scene);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

    }

    public void quit()
    {
        System.exit(0);
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
