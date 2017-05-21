package bookingSystem;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Logger;

//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import users.Employee;
import users.User;

/**
 * System driver class - contains menus and functions used to run the system
 **/
public class SystemDriver
{
	// new business scene
	@FXML private TextField txtBusName;
    @FXML private TextField txtAdminUsername;
    @FXML private PasswordField txtAdminPassword;
    @FXML private TextField txtOwnerName; // Add to GUI
    @FXML private TextField txtBusAddress; // ^
    @FXML private TextField txtBusPhone;	// ^
    @FXML private TextField txtServiceOne;
    @FXML private TextField txtServiceTwo;
    @FXML private TextField txtServiceThree;
    @FXML private ComboBox<String> durationOne;
    @FXML private ComboBox<String> durationTwo;
    @FXML private ComboBox<String> durationThree;

	// main scene
	@FXML private Button mainLogin;
	@FXML private Button mainRegister;

	// register scene
	@FXML private TextField txtUsername;
	@FXML private Label invUsername;
	@FXML private PasswordField txtPassword;
	@FXML private PasswordField txtConfirmPassword;
	@FXML private Label invPassword;
	@FXML private TextField txtEmail;
	@FXML private Label invEmail;
	@FXML private TextField txtPhone;
	@FXML private Label invPhone;
	@FXML private TextField txtName;
	@FXML private Label invName;
	@FXML private Label registerMessage;
	@FXML private Button regBack;
	@FXML private Button btRegister;

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
	@FXML private Button ownerLogoutOne;
	@FXML private Button ownerLogoutTwo;
	@FXML private Button ownerLogoutThree;
	@FXML private Button ownerLogoutFour;
	@FXML private Button ownerLogoutFive;
	@FXML private Label wtday;
	@FXML private Label wtemp;
	@FXML private Label wtstart;
	@FXML private Label wtend;

	// customer menu scene
	@FXML private ComboBox<String> makeBookingService;
	@FXML private ComboBox<String> makeBookingEmployee;
	@FXML private ComboBox<String> makeBookingDay;
	@FXML private ComboBox<String> makeBookingTime;
	@FXML private Button makeBookingBut;
	@FXML private Label makeBookingMessage;
	@FXML private Label mbs;
	@FXML private Label mbe;
	@FXML private Label mbt;
	@FXML private Label mbd;
	@FXML private ComboBox<String> availBookingsEmployee;
	@FXML private ComboBox<String> availBookingsDay;
	@FXML private ComboBox<String> availBookingsService;
	@FXML private TextArea availBookingsView;
	@FXML private Button getTimesBut;
	@FXML private TextArea custBookingsView;
	@FXML private Button customerLogoutThree;
	@FXML private Button customerLogoutTwo;
	@FXML private Button customerLogoutOne;

	// login scene
	@FXML private TextField txtLoginUsername;
	@FXML private PasswordField txtLoginPassword;
	@FXML private Label invLoginName;
	@FXML private Label invLoginPass;
	@FXML private Button loginButton;
	@FXML private Button loginBack;

	private Scanner keyboard = new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static final DateTimeFormatter defaultDateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
	private static final DateTimeFormatter defaultTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
	
	private static Database db = Database.getDb();

	User authUser = null; // TODO Add logout options to menus?
	static Business currentBusiness = null; // are we supposed to "login" as a business?? How are we supposed to implement/track this?

	public SystemDriver()
	{
		SeptFacade sept = new SeptFacade();
	}

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
			System.out.println("no file bro");
		}

		db.extractDbFile();
		db.loadFromDB();
		logger.info("database loaded into system");
	}

	public void setUp()
	{
		try
		{
		ObservableList<String> emplist = FXCollections.observableArrayList();
		for (Employee employee : db.getEmployeeMap().values())
		{
			emplist.add(employee.getName());
		}

		empSelect.setItems(emplist);
		empSelect2.setItems(emplist);
		empSelect3.setItems(emplist);
		empSelect.getSelectionModel().selectFirst();
		empSelect2.getSelectionModel().selectFirst();
		empSelect3.getSelectionModel().selectFirst();

		selectDay.getItems().clear();
		selectDay.getItems().addAll("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");

		logger.info("Owner Menu: Employee/Day Comboboxes refilled");
		}
		catch (Exception e)
		{
			logger.severe("some horrible shit went down");
		}
		

	}

	public void custSetUp()
	{
		ObservableList<String> oblist = FXCollections.observableArrayList();
		for (Employee employee : db.getEmployeeMap().values())
		{
			oblist.add(employee.getName());
		}
		ObservableList<String> servicelist = FXCollections.observableArrayList();
		for (Service service : db.getServiceMap().values())
		{
			servicelist.add(service.getName());
		}
		availBookingsService.setItems(servicelist);
		makeBookingService.setItems(servicelist);

		makeBookingEmployee.setItems(oblist);
		availBookingsEmployee.setItems(oblist);
		availBookingsDay.getItems().clear();
		availBookingsDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
				"Sunday");
		makeBookingDay.getItems().clear();
		makeBookingDay.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
		makeBookingTime.getItems().clear();
		makeBookingTime.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
				"17:00");

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

	public void createNewBusiness()
	{
		Business newBusiness = null;
		
		String businessName = txtBusName.getText();
		String ownerName = txtOwnerName.getText();
		String address = txtBusAddress.getText();
		String phone = txtBusPhone.getText();
		String adminUsername = txtAdminUsername.getText();
		String adminPassword = txtAdminPassword.getText();
		Service service = new Service(1, txtServiceOne.getText(), Integer.parseInt(durationOne.getValue()));
		Service service2 = new Service(1, txtServiceTwo.getText(), Integer.parseInt(durationTwo.getValue()));
		Service service3 = new Service(1, txtServiceThree.getText(), Integer.parseInt(durationThree.getValue()));
	
		newBusiness = new Business(businessName, ownerName, address, phone, adminUsername, adminPassword);
		newBusiness.service.add(service);
		newBusiness.service.add(service2);
		newBusiness.service.add(service3);
	}
		
	public static void setBusiness(Business business)
	{
		currentBusiness = business;
	}
	
	public static Business getBusiness()
	{
		return currentBusiness;
	}
	
	public void viewEmployeeAvailability()
	{
		try
		{
			empAvailView.setText("");
			Employee employee = null;
			for (Entry<Integer, Employee> entry : db.getEmployeeMap().entrySet())
			{
				Integer key = entry.getKey();
				Employee value = entry.getValue();
				if (empSelect.getValue().equals(value.getName()))
				{
					employee = db.getEmployeeMap().get(key);
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
		catch (NullPointerException e)
		{
			logger.severe("terrible shit just happened");
			System.out.println(e.getStackTrace());
		}
	}

	static LocalTime validateTime(String timeString)
	{
		boolean validTime = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time = null;

		try
		{
			time = LocalTime.parse(timeString, formatter);
			validTime = true;
		}
		catch (Exception e)
		{
			// Do nothing, leave validTime = false.
		}

		if (!validTime)
		{
			System.out.println("Please enter a valid time.");
		}
		return time;
	}

	/**
	 * Add workers working times - days and hours they work
	 */
	public void addWorkingTimes() throws NumberFormatException, UserRequestsExitException
	{
		int id = 0;
		int employeeId = 0;
		LocalTime start = null;
		LocalTime finish = null;

		Employee employee = null;
		for (Entry<Integer, Employee> entry : db.getEmployeeMap().entrySet())
		{
			Integer key = entry.getKey();
			Employee value = entry.getValue();
			if (empSelect2.getValue().equals(value.getName()))
			{
				employee = db.getEmployeeMap().get(key);
				employeeId = key;
			}
		}
		if (employee == null)
		{
			wtemp.setText("select an employee");
		}
		else
		{
			wtemp.setText("");
		}

		boolean validStart = false;
		boolean validEnd = false;
		boolean validDay = false;
		if (selectDay.getValue() == null)
		{
			wtday.setText("select a day");
			validDay = false;
		}
		else
		{
			wtday.setText("");
			validDay = true;
		}
		
		start = validateTime(txtWorkStart.getText());
		if (start != null)
		{
			wtstart.setText("");
			validStart = true;
		}
		else
		{
			wtstart.setText("enter a start time");
		}

		finish = validateTime(txtWorkEnd.getText());
		if (finish != null)
		{
			wtend.setText("");
			validEnd = true;
		}
		else
		{
			wtend.setText("enter an end time");
		}

		try
		{
			if (validStart && validEnd && validDay)
			{
				// Add entry to DB, return ID, add to local collection
				// TODO: Need to validate to check for current working times overlapping, already in system.
				id = db.addWorkingTimesToDB(employeeId, selectDay.getValue(), start, finish); 
				employee.addAvailability(selectDay.getValue(), start, finish);
				workTimeMessage.setText(selectDay.getValue() + ", " + start + "-" + finish + " has been added to "
						+ employee.getName() + "'s availability.");

			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * displays available time slots
	 */
	private void displayTimeSlots()
	{
		for (Timeslot timeslot : db.getTimeslotMap().values())
		{
			LocalDate date = timeslot.getDate();
			LocalTime time = timeslot.getTime();
			Boolean booked = timeslot.getStatus();
			System.out.println(date.format(defaultDateFormat) + ", " + time.format(defaultTimeFormat) + " - "
					+ "booked = " + booked);
		}
	}

	public void addBooking() throws NumberFormatException, UserRequestsExitException
	// TODO: VALIDATION NOT DONE. Waiting until this is implemented in gui as
	// many of the fields will be
	// doable with a dropdown box I think, so we won't need to validate manual
	// user input

	// also currently uses numerical IDs for all fields which is not user
	// friendly. But you can pull
	// details from the ID using, for example:
	// db.getTimeslotMap().get(timeslotId).getTime(),
	// .getDate(), .getStatus() and so on.
	{
		int bookingId = 0;
		int employeeId = 0;
		int customerId = authUser.getID();
		int timeslotId = 0;
		int serviceId = 0;

		boolean valid = false;
		Employee employee = null;
		if (makeBookingEmployee.getValue() != null)
		{
			mbe.setText("");

			for (Entry<Integer, Employee> entry : db.getEmployeeMap().entrySet())
			{
				Integer key = entry.getKey();
				Employee value = entry.getValue();
				if (makeBookingEmployee.getValue().equals(value.getName()))
				{
					employee = db.getEmployeeMap().get(key);
					employeeId = key;
				}
			}
		}
		else
		{
			mbe.setText(" select employee");

		}

		Service service = null;
		if (makeBookingService.getValue() != null)
		{
			mbs.setText("");

			for (Entry<Integer, Service> entry : db.getServiceMap().entrySet())
			{
				Integer key = entry.getKey();
				Service value = entry.getValue();
				if (makeBookingService.getValue().equals(value.getName()))
				{
					service = db.getServiceMap().get(key);
					serviceId = key;
				}
			}
		}
		else
		{
			mbs.setText(" select service");

		}

		Timeslot timeslot = null;
		if (makeBookingDay.getValue() != null && makeBookingTime.getValue() != null)
		{
			mbd.setText("");

			for (Entry<Integer, Timeslot> entry : db.getTimeslotMap().entrySet())
			{
				Integer key = entry.getKey();
				Timeslot value = entry.getValue();
				if (makeBookingTime.getValue().equals(value.getTime())
						&& makeBookingDay.getValue().equalsIgnoreCase(timeslot.getDate().getDayOfWeek().name()))
				{
					timeslot = db.getTimeslotMap().get(key);
					timeslotId = key;
				}
			}
		}
		else
		{
			mbd.setText(" select day/time");

		}

		Booking newBooking = null;

		valid = timeslot != null && service != null && employee != null;

		try
		{
			if (valid)
			{
				// Add entry to DB, return booking ID, add to local collection
				bookingId = db.addBookingToDB(employeeId, customerId, timeslotId, serviceId);
				newBooking = new Booking(bookingId, authUser, employee, timeslot, service);

				db.getBookingMap().put(newBooking.getID(), newBooking);
				// TODO: Have not written confirmation output as this will
				// probably done in GUI. Can show the time and day booked, and
				// with which employee, etc.
				makeBookingMessage.setText("Booking for " + makeBookingService.getValue() + " made for "
						+ makeBookingTime.getValue() + " on " + makeBookingDay.getValue());

			}
			else
			{
				makeBookingMessage.setText("Booking not Available");
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	private void addTimeSlots(int year, int month, int day, int startHour, int endHour)
	{/*
		 * for (int start = startHour; start < endHour; start++) {
		 * db.timeslotMap.add(new Timeslot(year, month, day, start)); }
		 */
	}

	// TODO mock up function - not yet implemented
	public void viewAvailableBooking()
	{
		availBookingsView.setText("");
		for (Timeslot timeslot : db.getTimeslotMap().values())
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
		for (Booking booking : db.getBookingMap().values())
		{
			if (authUser.getID() == booking.getCustomer().getID())
			{
				custBookingsView.appendText("DATE: " + booking.getTimeslot().getDate().toString() + "\nAT: "
						+ booking.getTimeslot().getTime().toString() + "\nFOR: " + booking.getService() + "\nWITH: "
						+ booking.getEmployee().getName() + "\n==========================\n");
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
			for (Entry<Integer, Employee> entry : db.getEmployeeMap().entrySet())
			{
				Integer key = entry.getKey();
				Employee value = entry.getValue();
				if (empSelect3.getValue().equals(value.getName()))
				{
					employee = db.getEmployeeMap().get(key);
					id = key;
				}
			}

			Boolean success = db.removeEmployeeFromDB(id);

			if (success)
			{
				logger.info("removed employee " + employee.getName());
				db.getEmployeeMap().remove(id);
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
		// valid = RegistrationValidation.validateName(name);
		for (Employee employee : db.getEmployeeMap().values())
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
				int id = db.addEmployeeToDB(name, null, null);
				newEmployee = new Employee(id, name, null, null);
				db.getEmployeeMap().put(newEmployee.getID(), newEmployee);
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
			for (Employee employee : db.getEmployeeMap().values())
			{
				System.out.println(employee.getID() + "-" + employee.getName() + "\n");
			}
		}
		else if (input.equals("2"))
		{
			System.out.println("Enter ID:\n");
			int id = keyboard.nextInt();
			keyboard.nextLine();

			Employee employee = db.getEmployeeMap().get(id);
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
		for (Employee employee : db.getEmployeeMap().values())
		{
			oblist.add(employee.getName());
		}

		empSelect.setItems(oblist);

		LocalDate today = LocalDate.now();
		LocalDate lastWeek = today.minus(Period.ofDays(100));
		LocalDate nextWeek = today.plus(Period.ofDays(7));
		boolean noResults = true;

		// input 1 - view last weeks bookings
		if (radioLastWeek.isSelected())
		{
			bookingsView.setText("");
			for (Booking booking : db.getBookingMap().values())
			{
				LocalDate date = booking.getTimeslot().getDate();
				if (date.isAfter(lastWeek) && date.isBefore(today))
				{
					LocalTime t = booking.getTimeslot().getTime();
					bookingsView.appendText(
							booking.getCustomer().getUsername() + " on " + date.format(defaultDateFormat) + " at "
									+ t.format(defaultTimeFormat) + " with " + booking.getEmployee().getName() + "\n");
					noResults = false;
				}
			}
		}
		// input 2 - view next weeks bookings
		else if (radioNextWeek.isSelected())
		{
			bookingsView.setText("");
			for (Booking booking : db.getBookingMap().values())
			{
				LocalDate date = booking.getTimeslot().getDate();
				if (date.isBefore(nextWeek) && date.isAfter(today))
				{
					LocalTime t = booking.getTimeslot().getTime();
					DateTimeFormatter tf = DateTimeFormatter.ofPattern("hh:mm a");
					bookingsView
							.appendText(booking.getCustomer().getUsername() + " on " + date.format(defaultDateFormat)
									+ " at " + t.format(tf) + " with " + booking.getEmployee().getName() + "\n");
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
	 * login - sets the current authorised user and determines if they are the
	 * owner or a customer - directs to appropriate menu
	 * 
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws Exception
	{
		invLoginPass.setText("");
		invLoginName.setText("");
		User authUser = null;
		try
		{
			authUser = auth(txtLoginUsername.getText(), txtLoginPassword.getText());
			setAuthUser(authUser);
			String sceneToDisplay = authUser.isAdmin() ? "/bookingSystem/OwnerMenu.fxml"
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
		for (User user : db.getUserMap().values())
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
	 * Username does not allow special characters Alphanumeric and punctuation
	 **/
	public boolean validateUsername(String username)
	{
		boolean validUsername = username.matches("[a-zA-Z0-9'., -]+");
		boolean taken = false;
		for (User user : db.getUserMap().values())
		{
			if (user.getUsername().equals(username))
			{
				taken = true;
			}
		}

		if (!validUsername) // need a regex that accepts only alphanumeric only
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
	 * Password can be any format function checks whether passwords match
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
	 * Validate email regex requires format of <alphaNum/punc>@<alphanum/punc>.
	 * <alphanum/punc>
	 **/
	public boolean validateEmail(String email)
	{
		boolean validEmail = email.matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.]{1,1}+[a-zA-Z0-9-.]+");
		if (!validEmail) // need a regex that accepts alphanumeric + a few
							// special characters
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
	 * Full name allows only alphabetical characters and common punctuation used
	 * in names
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
	 * Registration function with simple validation makes calls to validation
	 * functions within RegistrationValidation
	 */
	public void register(ActionEvent event) throws Exception
	{
		boolean validUsername = txtUsername.getLength() != 0 ? validateUsername(txtUsername.getText()) : false;
		boolean validPassword = txtConfirmPassword.getLength() != 0
				? validatePassword(txtPassword.getText(), txtConfirmPassword.getText()) : false;
		boolean validEmail = txtEmail.getLength() != 0 ? validateEmail(txtEmail.getText()) : false;
		boolean validPhone = txtPhone.getLength() != 0 ? validatePhone(txtPhone.getText()) : false;
		boolean validName = txtName.getLength() != 0 ? validateName(txtName.getText()) : false;

		boolean validRegistration = validUsername && validPassword && validEmail && validPhone && validName;

		try
		{
			if (validRegistration)
			{
				registerMessage.setText("all is well");

				int id = db.addUserToDB(txtUsername.getText(), txtPassword.getText(), txtEmail.getText(),
						txtName.getText(), txtPhone.getText());
				User newUser = new User(id, txtUsername.getText(), txtPassword.getText(), txtEmail.getText(),
						txtName.getText(), txtPhone.getText());
				db.getUserMap().put(newUser.getID(), newUser);
				System.out.println("User Registered. Welcome " + newUser.getUsername());
				setAuthUser(newUser);

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookingSystem/CustomerMenu.fxml"));
				Parent root = loader.load();
				SystemDriver c = loader.getController();
				c.setAuthUser(authUser);
				Scene scene = new Scene(root, 720, 480);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
			}
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

	public static LocalDate parseDate(String dateString)
	/*
	 * Parses string into a java.time LocalDate object. Checks size of day and
	 * month fields and builds an appropriate formatting pattern.
	 * 
	 * Works for delimiters: / . -
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
		// System.out.println("format string="+formatString);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
		date = LocalDate.parse(dateString, formatter);
		return date;
	}
}
