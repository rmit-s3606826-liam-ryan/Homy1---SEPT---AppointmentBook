package bookingSystem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import users.Employee;
import users.User;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * System driver class - contains menus and functions used to run the system
 **/
public class SystemDriver
{
	@FXML
	public void chooseFile(ActionEvent event)
	{
		try
		{
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters()
					.addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
			Stage primaryStage = new Stage();
			File tmp = fc.showOpenDialog(primaryStage);
			String imagepath = tmp.toURI().toURL().toString();
			Image img = new Image(imagepath);
			image1.setImage(img);
		}
		catch (Exception e)
		{
			logger.severe("nooo");
		}
	}
	// these prevent certain gui things being run and causing errors
	// during test running. if testing function that sets something
	// like changing a label, make sure to put them behind a check on
	// this boolean
	private static boolean test = false;
	public void setTest(){test = true;}
	public boolean getTest(){return test;}
	@FXML private Button changeImage;
	@FXML private ImageView image1;
	
	// new business scene
	@FXML private PasswordField txtBusConfirmPassword;
	@FXML private Button makeBusBut;
	@FXML private Label invBusName;
	@FXML private Label invBusAdminUsername;
    @FXML private Label invBusAdminPassword;
    @FXML private Label invBusAddress;
    @FXML private Label invBusPhone;
    @FXML private Label invBusOwnerName;
    @FXML private Label invBusiness;
	@FXML private TextField txtBusName;
    @FXML private TextField txtAdminUsername;
    @FXML private PasswordField txtAdminPassword;
    @FXML private TextField txtOwnerName; // Add to GUI
    @FXML private TextField txtBusAddress; // ^
    @FXML private TextField txtBusPhone;	// ^
    @FXML private TextField txtServiceOne;
    @FXML private TextField txtServiceTwo;
    @FXML private TextField txtServiceThree;

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
	@FXML private TextField txtAddEmpName;
	@FXML private TextField txtAddEmpAddress;
	@FXML private TextField txtAddEmpPhone;
	@FXML private Label addEmpMessage;
	@FXML private Label invAddEmpName;
	@FXML private Label invAddEmpAddress;
	@FXML private Label invAddEmpPhone;
	@FXML private Button addEmpBut;
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
	@FXML private ComboBox<String> custBookingID;
	@FXML private ComboBox<String> makeBookingService;
	@FXML private ComboBox<String> makeBookingEmployee;
	@FXML private ComboBox<String> makeBookingDay;
	@FXML private ComboBox<String> makeBookingTime;
	@FXML private Button makeBookingBut;
	@FXML private Button cancelBookingBut;
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
	
	private static final String comboBoxAccepted_Format = "-fx-opacity: 1; -fx-background-color: rgba(155,255,155,0.6)";

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
	private static SystemDriver systemDriver = null;

	User authUser = null; // TODO Add logout options to menus?
	static Business currentBusiness = null; // are we supposed to "login" as a business?? How are we supposed to implement/track this?

	public SystemDriver()
	{ }
    
    public static SystemDriver getSystemDriver()
    {
    	if (systemDriver == null)
    	{
    		systemDriver = new SystemDriver();
    	}
    return systemDriver;
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
		ObservableList<String> serviceList = FXCollections.observableArrayList();
		for (Service service : db.getServiceMap().values())
		{
			serviceList.add(service.getName());
		}
		availBookingsService.setItems(serviceList);
		makeBookingService.setItems(serviceList);
		

		//availBookingsEmployee.setItems(employeeList);
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		availBookingsDay.getItems().clear();
		availBookingsDay.getItems().addAll(days);
		makeBookingDay.getItems().clear();
		makeBookingDay.getItems().addAll(days);
		
		resetAddBookingForm();
	}

	public void loadMakeBusinessScene(ActionEvent event) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/MakeBusiness.fxml"));
		Scene scene = new Scene(root, 720, 480);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		logger.info("Changed to Make Business Scene");

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

	public void createNewBusiness(ActionEvent event)
	{		
	    String setText = null;
	    test = true;
	    
		boolean validBusinessName = validateName(txtBusName.getText());
		boolean validOwnerName = validateName(txtOwnerName.getText());
		boolean validAddress = validateAddress(txtBusAddress.getText());
		boolean validPhone = validatePhone(txtBusPhone.getText());
		boolean validAdminUsername = validateUsername(txtAdminUsername.getText());
//		boolean validPassword = validatePassword(txtAdminPassword.getText(), txtBusConfirmPassword.getText());

		setText = validBusinessName ? "" : "Don't name your business that...";
	    invBusName.setText(setText);
	    
	    setText = validOwnerName ? "" : "Invalid name.";
	    invBusOwnerName.setText(setText);
	    
	    setText = validAdminUsername ? "" : "Invalid username.";
	    invBusAdminUsername.setText(setText);
	    
	    setText = validAddress ? "" : "Invalid address.";
	    invBusAddress.setText(setText);
	    
	    setText = validPhone ? "" : "Invalid Phone.";
	    invBusPhone.setText(setText);
	    
//	    setText = validPassword ? "" : "Passwords do not match.";
//	    invBusAdminPassword.setText(setText);
		boolean validBusiness = validBusinessName
							 && validOwnerName 
							 && validAddress 
							 && validPhone 
							 && validAdminUsername;

	    setText = validBusiness ? "" : "Invalid Fields... Look for the red.";
	    invBusiness.setText(setText);

		/*
		String adminPassword = txtAdminPassword.getText();
		Service service1 = new Service(1, txtServiceOne.getText(), Integer.parseInt(durationOne.getValue()));
		Service service2 = new Service(1, txtServiceTwo.getText(), Integer.parseInt(durationTwo.getValue()));
		Service service3 = new Service(1, txtServiceThree.getText(), Integer.parseInt(durationThree.getValue()));
		Service[] services = {service1, service2, service3};
		*/
		if (validBusiness)
		{
			SeptFacade sept = SeptFacade.getFacade();
			sept.addBusiness(txtBusName.getText(),
				         	 txtOwnerName.getText(),
				         	 txtBusAddress.getText(),
				         	 txtBusPhone.getText(),
				         	 txtAdminUsername.getText());
			test = false;
			
			try
			{
				Parent root;
				root = FXMLLoader.load(getClass().getResource("/bookingSystem/Main.fxml"));
				Scene scene = new Scene(root, 720, 480);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				logger.info("Changed to Main Scene");
			}
			catch (IOException e)
			{
				logger.info("when the shit goes down, you better be ready... BETTER BE READY");
			}

		}
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
			//Boolean booked = timeslot.getStatus();
			System.out.println(date.format(defaultDateFormat) + ", " + time.format(defaultTimeFormat));
		}
	}

	public void addBooking() throws NumberFormatException, UserRequestsExitException
	{
		int bookingId = 0;
		int employeeId = 0;
		int customerId = authUser.getID();
		int timeslotId = 0;
		int serviceId = 0;
		
		Database db = Database.getDb();

		boolean valid = false;
		Employee employee = db.getEmployeeByName(makeBookingEmployee.getValue());
		if (employee != null)
		{
			mbe.setText("");
			employeeId = employee.getID();
		}
		else
		{
			mbe.setText(" select employee");
		}
		
		Service service = db.getServiceByName(makeBookingService.getValue());
		if (service != null)
		{
			mbs.setText("");
			serviceId = service.getID();
		}
		else
		{
			mbs.setText(" select service");
		}
		
		if (makeBookingDay.getValue() == null || makeBookingTime.getValue() == null)
		{
			mbd.setText(" select day/time");
		}
		else
		{
			mbd.setText("");
			
			// start bookings from tomorrow onwards, cannot book remainder of
			// current day, to prevent booking times that have already passed
			LocalDate bookingDate = LocalDate.now().plusDays(1);
			DayOfWeek futureBooking = DayOfWeek.valueOf(makeBookingDay.getValue().toUpperCase());
			
			// iterate the booking date forward through the next week until the
			// day of week matches that chosen by the user.
			while (bookingDate.getDayOfWeek().getValue() != (futureBooking.getValue()))
			{
				bookingDate = bookingDate.plusDays(1);
			}
			
			LocalTime bookingTime = LocalTime.parse(makeBookingTime.getValue());
			Timeslot timeslot = db.getTimeslot(bookingDate, bookingTime);
			if (timeslot == null)
			{
				timeslotId = addTimeslot(bookingDate, bookingTime);
				timeslot = db.getTimeslotMap().get(timeslotId);
			}
		}
		
		Booking newBooking = null;

		valid = (serviceId > 0) && (employeeId > 0) && (timeslotId > 0);
		System.out.println("sid="+serviceId + "eid=" + employeeId + "tid="+timeslotId);
		try
		{
			Timeslot timeslot = db.getTimeslotMap().get(timeslotId);
			if (valid && !isBooked(employee, timeslot))
			{
				
				// Add entry to DB, return booking ID, add to local collection
				bookingId = db.addBookingToDB(employeeId, customerId, timeslotId, serviceId);
				newBooking = new Booking(bookingId, authUser, employee, timeslot, service);
				db.getBookingMap().put(newBooking.getID(), newBooking);
				//addChildBookings(newBooking);
				
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
	
	
	private void addChildBookings(Booking parentBooking)
	{
		Employee employee = parentBooking.getEmployee();
		Service service = parentBooking.getService();
		User customer = parentBooking.getCustomer();
		Timeslot timeslot = parentBooking.getTimeslot();
		LocalDate date = timeslot.getDate();
		int duration = service.getDuration();
		int bookingsToAdd = (duration / 30) - 1;
		
		for (int i=1; i<=bookingsToAdd; i++)
		{
			int bookingId = 0;
			Booking newBooking = null;
			LocalTime newTime = timeslot.getTime().plusMinutes(30 * i);
			timeslot = db.getTimeslot(timeslot.getDate(), newTime);
			if (timeslot == null)
			{
				addTimeslot(date, newTime);
			}
			try // Add entry to DB, return booking ID, add to local collection
			{
				bookingId = db.addBookingToDB(employee.getID(), customer.getID(), timeslot.getID(), service.getID(), parentBooking.getID());
				newBooking = new Booking(bookingId, timeslot, parentBooking);
				db.getBookingMap().put(newBooking.getID(), newBooking);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	private int addTimeslot(LocalDate date, LocalTime time)
	{
		Timeslot t = new Timeslot(date, time, false);
		int timeslotId = 0;
		try
		{
			timeslotId = db.addTimeslotToDB(date, time, false);
			db.getTimeslotMap().put(timeslotId, t);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return timeslotId;
	}
	
	public boolean isBooked(Employee e, Timeslot t)
	{
		for (Booking booking : db.getBookingMap().values())
		{
			Employee employee = booking.getEmployee();
			Timeslot timeslot = booking.getTimeslot();
			if (employee.equals(e) && timeslot.equals(t))
			{
				return true;
			}
		}
		return false;
	}

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

			//if (timeslot.getStatus() != false)
			//{
				//continue;
			//}
			availBookingsView.appendText(timeslot.getDate() + " : " + timeslot.getTime() + "\n");

		}
		logger.info("Viewing bookings for " + availBookingsDay.getValue());
	}

	public void viewCustomerBooking()
	{
		custBookingsView.setText("");
		ObservableList<String> oblist = FXCollections.observableArrayList();
		for (Booking booking : db.getBookingMap().values())
		{
			if (authUser.getID() == booking.getCustomer().getID())
			{
				String temp = Integer.toString(booking.getID());
				oblist.add(temp);

				custBookingsView.appendText("DATE: " + booking.getTimeslot().getDate().toString() + "\nAT: "
						+ booking.getTimeslot().getTime().toString() + "\nFOR: " + booking.getService().getName() + "\nWITH: "
						+ booking.getEmployee().getName() + "\n(booking ID)" + booking.getID() + "\n==========================\n");
			}
		}
		custBookingID.setItems(oblist);
		logger.info("Viewing bookings for " + authUser.getUsername());
	}
	
	public void cancelBooking()
	{
		try
		{
			int temp = Integer.parseInt(custBookingID.getValue());
			for (Entry<Integer, Booking> entry : db.getBookingMap().entrySet())
			{
				Integer key = entry.getKey();
				Booking value = entry.getValue();
				if (temp == value.getID())
				{
					db.getBookingMap().remove(key);
				}
			}
		
		viewCustomerBooking();
		}
		catch (Exception e)
		{
			logger.severe("boom, headshot");
			logger.severe("you missed, InvocationTargetException");
		}
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
		Employee newEmployee = null;
		boolean validAddress = txtAddEmpAddress.getLength() != 0 ? validateAddress(txtAddEmpAddress.getText()) : false;
		boolean validPhone = txtAddEmpPhone.getLength() != 0 ? validatePhone(txtAddEmpPhone.getText()) : false;
		boolean validName = txtAddEmpName.getLength() != 0 ? validateName(txtAddEmpName.getText()) : false;

		boolean validEmployee = validAddress && validPhone && validName;

		// valid = RegistrationValidation.validateName(name);
		for (Employee employee : db.getEmployeeMap().values())
		{
			if (employee.getName().equals(txtAddEmpName.getText()))
			{
				addEmpMessage.setText("Employee already exists");
				validEmployee = false;
			}
		}
		if (validEmployee)
		{
			try
			{
				int id = db.addEmployeeToDB(txtAddEmpName.getText(), txtAddEmpPhone.getText(), txtAddEmpAddress.getText());
				newEmployee = new Employee(id, txtAddEmpName.getText(), txtAddEmpPhone.getText(), txtAddEmpAddress.getText());
				db.getEmployeeMap().put(newEmployee.getID(), newEmployee);
				addEmpMessage.setText("\"" + txtAddEmpName.getText() + "\" has been added as a new employee.");
			}
			catch (SQLException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	

	private boolean validateAddress(String address)
	{
		boolean validAddress = address.matches("[a-zA-Z0-9'., -]+");
		if (!validAddress && !test)
		{
			invAddEmpAddress.setText(" How can anyone live there?");
		}
		else if (!test)
		{
			invAddEmpAddress.setText("");
		}
		return validAddress;
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
	
	public void resetAddBookingForm()
	{
		makeBookingService.setValue("");
		makeBookingEmployee.setValue("");
		makeBookingDay.setValue("");
		makeBookingTime.setValue("");
		
		makeBookingService.setDisable(false);
		makeBookingEmployee.setDisable(true);
		makeBookingDay.setDisable(true);
		makeBookingTime.setDisable(true);
		
		makeBookingService.setStyle("");
		makeBookingEmployee.setStyle("");
		makeBookingDay.setStyle("");
		makeBookingTime.setStyle("");
		
		logger.info("Customer Menu: form reset");
	}
	
	public void updateMakeBooking_Service(ActionEvent event)
	{
		Service selectedService = db.getServiceByName(makeBookingService.getValue());
		
		if (selectedService != null)
		{
			makeBookingService.setDisable(true); // accept input, disable box and enable next box
			makeBookingService.setStyle(comboBoxAccepted_Format);
			makeBookingEmployee.setDisable(false);
			
			populateEmployees(selectedService);
			createTimeslotIncrements(selectedService);
		}
	}
	
	
	public void populateEmployees(Service s)
	{
		ObservableList<String> employeeList = FXCollections.observableArrayList();
		for (Employee employee : db.getEmployeeMap().values())
		{
			if (employee.getServices().contains(s))
			{
				employeeList.add(employee.getName());
			}
		}
		// populate employee list with all employees who provide the selected service
		makeBookingEmployee.getItems().clear();
		makeBookingEmployee.setItems(employeeList);
		
		logger.info("Customer Menu: Employee Combobox refilled");
	}
	
	public void createTimeslotIncrements (Service s)
	{
		Business b = Business.getBusiness();
		LocalTime open = b.getEarliestOpen();
		LocalTime close = b.getLatestClose();
		
		if (open.getMinute() != 0)
		{
			if (open.getMinute() > 30)
			{ // round to next hour
				open = open.withMinute(0);
				open.withHour(open.getHour() + 1);
			}
			else
			{ // round to next 30 mins
				open = open.withMinute(30);
			}
		}
		
		// create booking times in increments of 30 minutes, between open and close times
		int timeSlots = (int) (MINUTES.between(open, close) / 30);
		
		/* if the last possible booking slot + duration of the appointment would
		 * book past the business closing time, drop the last appointment slot,
		 * then repeat until the last appointment time would fit in before close.
		 */
		while (open.plusMinutes(((timeSlots - 1) * 30) + s.getDuration()).isAfter(close))
		{
			timeSlots--;
		}
		
        String[] times = new String[timeSlots];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (int i=0; i<timeSlots; i++)
        {
        	LocalTime adjusted = open.plusMinutes(i * 30);
        	String timeSlot = adjusted.format(formatter);
        	times[i] = timeSlot;
        }
        
        makeBookingTime.getItems().clear();
		makeBookingTime.getItems().addAll(times);

		logger.info("Customer Menu: Time Combobox refilled");
	}
	
	public void updateMakeBooking_Employee(ActionEvent event)
	{
		String employee = makeBookingEmployee.getValue();
		Employee selectedEmployee = db.getEmployeeByName(employee);
		
		if (selectedEmployee != null)
		{
			makeBookingEmployee.setDisable(true); // accept input, disable box and enable next boxes
			makeBookingEmployee.setStyle(comboBoxAccepted_Format);
			makeBookingDay.setDisable(false);
			makeBookingTime.setDisable(false);
			
			ObservableList<String> dayList = FXCollections.observableArrayList();
			
			for (String dayOfWeek : selectedEmployee.getAvailability().keySet())
			{
				dayList.add(dayOfWeek);
			}
			// populate day list with all days when the selected employee is working
			makeBookingDay.getItems().clear();
			makeBookingDay.setItems(dayList);
			
			logger.info("Customer Menu: Day Combobox refilled");
		}
	}
	
	public void updateMakeBooking_Day(ActionEvent event)
	{
		String employee = makeBookingEmployee.getValue();
		Employee selectedEmployee = db.getEmployeeByName(employee);
		Service selectedService = db.getServiceByName(makeBookingService.getValue());
		
		if (selectedEmployee != null && selectedService != null
				&& makeBookingDay.getValue() != null)
		{
			makeBookingDay.setStyle(comboBoxAccepted_Format);
		}
	}
	
	public void updateMakeBooking_Time(ActionEvent event)
	{
		String employee = makeBookingEmployee.getValue();
		Employee selectedEmployee = db.getEmployeeByName(employee);
		Service selectedService = db.getServiceByName(makeBookingService.getValue());
		
		if (selectedEmployee != null && selectedService != null
				&& makeBookingDay.getValue() != null
				&& makeBookingTime.getValue() != null)
		{
			makeBookingTime.setStyle(comboBoxAccepted_Format);
		}
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
		
		if (!validUsername && !test)
		{
			invUsername.setText(" Invalid Username");
		}
		else if (taken && !test)
		{
			invUsername.setText(" Username Taken");
			validUsername = false;
		}
		else if (!test)
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
		if (!validPassword && !test)
		{
			invPassword.setText(" Passwords do not match");
		}
		else if (!test)
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
		if (!validEmail && !test) // need a regex that accepts alphanumeric + a few
							// special characters
		{
			invEmail.setText(" Invalid email format");
		}
		else if (!test)
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
		if (!validNumber && !test)
		{
			invPhone.setText(" Invalid phone number");
		}
		else if (!test)
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
		if (!validName && !test)
		{
			invName.setText(" Like no name I've seen");
		}
		else if (!test)
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
