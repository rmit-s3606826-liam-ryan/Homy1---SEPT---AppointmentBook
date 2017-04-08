package bookingSystem;

import java.sql.Connection;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import bookings.Booking;
import bookings.Timeslot;
import users.Employee;
import users.User;


public class Database
{
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:./db/database";
    private static final String DB_USER = "notSEPTadmin";
    private static final String DB_PASSWORD = "XxX_Pr0-d4nk-passw0rd_XxX";
    
    // ***************************************************************
    // Database table column names:
    //
    // USERS Table
    private static final String HEADER_USERS_ID = "USER_ID";
    private static final String HEADER_USERS_USERNAME = "USERNAME";
    private static final String HEADER_USERS_PASSWORD = "PASSWORD";
    private static final String HEADER_USERS_EMAIL = "EMAIL";
    private static final String HEADER_USERS_PHONE = "PHONE";
    private static final String HEADER_USERS_DOB = "DOB";
    private static final String HEADER_USERS_NAME = "NAME";
    //
    // EMPLOYEES Table
    private static final String HEADER_EMPLOYEES_ID = "EMPLOYEE_ID";
    private static final String HEADER_EMPLOYEES_NAME = "NAME";
    //
    // TIMESLOTS Table
    private static final String HEADER_TIMESLOTS_ID = "TIMESLOT_ID";
    private static final String HEADER_TIMESLOTS_DATE = "DATE";
    private static final String HEADER_TIMESLOTS_TIME = "TIME";
    private static final String HEADER_TIMESLOTS_BOOKED = "BOOKED";
    //
    // BOOKINGS Table
    private static final String HEADER_BOOKINGS_ID = "BOOKING_ID";
    private static final String HEADER_BOOKINGS_CUSTOMER_ID = "CUSTOMER_ID";
    private static final String HEADER_BOOKINGS_EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String HEADER_BOOKINGS_TIMESLOT_ID = "TIMESLOT_ID";
    private static final String HEADER_BOOKINGS_SERVICE = "SERVICE";
    private static final String HEADER_BOOKINGS_DURATION = "DURATION";
    // ***************************************************************
    
    static HashMap<Integer, User> userMap = new HashMap<Integer, User>();
    static HashMap<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
    static HashMap<Integer, Timeslot> timeslotMap = new HashMap<Integer, Timeslot>();
    static HashMap<Integer, Booking> bookingMap = new HashMap<Integer, Booking>();
    
	static Connection getDBConnection()
	
	{
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
	
	static void loadFromDB()
	{
		try
		{
			getUsers(); // MUST DO users and employee tables first,
			getEmployees(); // so timeslots and bookings have objects to link to.
			getTimeslots();
			getBookings();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}

	static void getUsers() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getUsersQuery = "select * from USERS";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getUsersQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_USERS_ID);
				String username = rs.getString(HEADER_USERS_USERNAME);
				String password = rs.getString(HEADER_USERS_PASSWORD);
				String email = rs.getString(HEADER_USERS_EMAIL);
				String phone = rs.getString(HEADER_USERS_PHONE);
				String dobAsString = rs.getString(HEADER_USERS_DOB);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
				LocalDate dob = LocalDate.parse(dobAsString, formatter);
				String name = rs.getString(HEADER_USERS_NAME);
				User user = new User(username, password, email, name, phone, dob);
				userMap.put(id, user);
			}
			stmt.close();
			c.commit();
		}
		catch (SQLException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        c.close();
	    }
	}
	
	static void getEmployees() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getUsersQuery = "select * from EMPLOYEES";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getUsersQuery);
			
			while (rs.next())
			{			
				int id = rs.getInt(HEADER_EMPLOYEES_ID);
				String name = rs.getString(HEADER_EMPLOYEES_NAME);
				Employee employee = new Employee(name, id);
				employeeMap.put(id,employee);
			}
			stmt.close();
			c.commit();
		}
		catch (SQLException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        c.close();
	    }
	}
	
	static void getTimeslots() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getTimeslotsQuery = "select * from TIMESLOTS";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getTimeslotsQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_TIMESLOTS_ID);
				String dateAsString = rs.getString(HEADER_TIMESLOTS_DATE);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate date = LocalDate.parse(dateAsString, formatter);
				
				// deprecated lib - only used to pull from DB, then convert to LocalTime
				java.sql.Time timeSQL = rs.getTime(HEADER_TIMESLOTS_TIME);
				LocalTime time = timeSQL.toLocalTime();
				Boolean booked = rs.getBoolean(HEADER_TIMESLOTS_BOOKED);
				Timeslot timeslot = new Timeslot(date, time, booked);
				timeslotMap.put(id, timeslot);
			}
			stmt.close();
			c.commit();
		}
		catch (SQLException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        c.close();
	    }
	}
	
	static void getBookings() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getBookingsQuery = "select * from BOOKINGS";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getBookingsQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_BOOKINGS_ID);
				
				// Convert ID's to actual objects
				int customer_id = rs.getInt(HEADER_BOOKINGS_CUSTOMER_ID);
				int employee_id = rs.getInt(HEADER_BOOKINGS_EMPLOYEE_ID);
				int timeslot_id = rs.getInt(HEADER_BOOKINGS_TIMESLOT_ID); // TODO linking.
				
				User customer = userMap.get(customer_id);
				Employee employee = employeeMap.get(employee_id);
				Timeslot timeslot = timeslotMap.get(timeslot_id);
				String service = rs.getString(HEADER_BOOKINGS_SERVICE);
				int duration = rs.getInt(HEADER_BOOKINGS_DURATION);
				Booking booking = new Booking(id, customer, employee, timeslot, service, duration);
				bookingMap.put(id, booking);
			}
			stmt.close();
			c.commit();
		}
		catch (SQLException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        c.close();
	    }
	}
}
