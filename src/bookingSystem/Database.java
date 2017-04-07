package bookingSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Bookings.Booking;
import Bookings.Timeslot;
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
    // USER Table
    private static final String HEADER_USER_USERNAME = "USERNAME";
    private static final String HEADER_USER_PASSWORD = "PASSWORD";
    private static final String HEADER_USER_EMAIL = "EMAIL";
    private static final String HEADER_USER_PHONE = "PHONENO";
    private static final String HEADER_USER_DOB = "DOB";
    private static final String HEADER_USER_NAME = "NAME";
    //
    // EMPLOYEE Table
    private static final String HEADER_EMPLOYEE_ID = "ID";
    private static final String HEADER_EMPLOYEE_NAME = "NAME";
    //
    // TIMESLOTS Table
    private static final String HEADER_TIMESLOTS_DATE = "DATE";
    private static final String HEADER_TIMESLOTS_TIME = "TIME";
    private static final String HEADER_TIMESLOTS_EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String HEADER_TIMESLOTS_BOOKED = "BOOKED";
    //
    // BOOKINGS Table
    private static final String HEADER_BOOKINGS_CUSTOMER_ID = "CUSTOMER_ID";
    private static final String HEADER_BOOKINGS_EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String HEADER_BOOKINGS_TIMESLOT = "TIMESLOT";
    private static final String HEADER_BOOKINGS_SERVICE = "SERVICE";
    private static final String HEADER_BOOKINGS_DURATION = "DURATION";
    // ***************************************************************
    
    static List<User> userList = new ArrayList<User>();
    static List<Employee> employeeList = new ArrayList<Employee>();
    static List<Timeslot> timeslotList = new ArrayList<Timeslot>();
    static List<Booking> bookingList = new ArrayList<Booking>();
    
	// http://www.javatips.net/blog/h2-database-example
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
		 


		
		//Connection c = getDBConnection();
		
		try
		{
			getUsers();
			getEmployees();
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
		String getUsersQuery = "select * from USER";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getUsersQuery);
			
			while (rs.next())
			{			
				String username = rs.getString(HEADER_USER_USERNAME);
				String password = rs.getString(HEADER_USER_PASSWORD);
				String email = rs.getString(HEADER_USER_EMAIL);
				String phone = rs.getString(HEADER_USER_PHONE);
				Date dob = rs.getDate(HEADER_USER_DOB);
				String name = rs.getString(HEADER_USER_NAME);
				User user = new User(username, password, email, name, phone, dob);
				userList.add(user);
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
		String getUsersQuery = "select * from EMPLOYEE";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getUsersQuery);
			
			while (rs.next())
			{			
				int id = rs.getInt(HEADER_EMPLOYEE_ID);
				String name = rs.getString(HEADER_EMPLOYEE_NAME);
				Employee employee = new Employee(name, id);
				employeeList.add(employee);
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
				String employee_id = rs.getString(HEADER_TIMESLOTS_EMPLOYEE_ID);
				Date date = rs.getDate(HEADER_TIMESLOTS_DATE);
				int time = rs.getInt(HEADER_TIMESLOTS_TIME); //24HR time
				Boolean booked = rs.getBoolean(HEADER_TIMESLOTS_BOOKED);
				//Timeslot ts = new Timeslot();
				Timeslot ts = null; //TODO
				timeslotList.add(ts);
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
				String cust_username = rs.getString(HEADER_BOOKINGS_CUSTOMER_ID);
				int employee_id = rs.getInt(HEADER_BOOKINGS_EMPLOYEE_ID);
				int timeslot = rs.getInt(HEADER_BOOKINGS_TIMESLOT); // TODO linking.
				String service = rs.getString(HEADER_BOOKINGS_SERVICE);
				int duration = rs.getInt(HEADER_BOOKINGS_DURATION);
				Booking booking = new Booking(null, cust_username); //TODO pass date as calendar obj?
				bookingList.add(booking);
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
