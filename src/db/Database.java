package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;

import bookingSystem.Service;
import bookingSystem.SystemDriver;
import bookings.Booking;
import bookings.Timeslot;
import users.Employee;
import users.User;

public class Database
{
    private static final String DB_DRIVER = "org.h2.Driver";
    // Only connect to DB if it exists; We don't want to create skeleton DBs everywhere
    private static final String DB_CONNECTION = "jdbc:h2:./bin/db/database;IFEXISTS=TRUE";
    private static final String DB_CONNECTION_JAR = "jdbc:h2:./db/database;IFEXISTS=TRUE";
    private static final String DB_USER = "notSEPTadmin";
    private static final String DB_PASSWORD = "XxX_Pr0-d4nk-passw0rd_XxX";
    
    // ***************************************************************
    //
    // DB table names:
    //
    static final String TABLE_USERS = "USERS";
    static final String TABLE_EMPLOYEES = "EMPLOYEES";
    static final String TABLE_SERVICES = "SERVICES";
    static final String TABLE_EMPSERVICES = "EMPLOYEE_SERVICES";
    static final String TABLE_AVAILABILITY = "AVAILABILITY";
    static final String TABLE_TIMESLOTS = "TIMESLOTS";
    static final String TABLE_BOOKINGS = "BOOKINGS";
    //
    // DB column names:
    //
    // USERS Table
    static final String HEADER_USERS_ID = "USER_ID";
    static final String HEADER_USERS_USERNAME = "USERNAME";
    static final String HEADER_USERS_PASSWORD = "PASSWORD";
    static final String HEADER_USERS_EMAIL = "EMAIL";
    static final String HEADER_USERS_PHONE = "PHONE";
    static final String HEADER_USERS_NAME = "NAME";
    //
    // EMPLOYEES Table
    static final String HEADER_EMPLOYEES_ID = "EMPLOYEE_ID";
    static final String HEADER_EMPLOYEES_NAME = "NAME";
    static final String HEADER_EMPLOYEES_PHONE = "PHONE";
    static final String HEADER_EMPLOYEES_ADDRESS = "ADDRESS";
    //
    // SERVICES Tables
    static final String HEADER_SERVICES_ID = "SERVICE_ID";
    static final String HEADER_SERVICES_NAME = "NAME";
    static final String HEADER_SERVICES_DURATION = "DURATION";
    static final String HEADER_EMPSERVICES_EMPID = "EMPLOYEE_ID";
    static final String HEADER_EMPSERVICES_SID = "SERVICE_ID";
    //
    // AVAILABILITY Table
    static final String HEADER_AVAILABILITY_ID = "ID";
    static final String HEADER_AVAILABILITY_EMPLOYEE_ID = "EMPLOYEE_ID";
    static final String HEADER_AVAILABILITY_DAY = "DAYOFWEEK";
    static final String HEADER_AVAILABILITY_START = "STARTTIME";
    static final String HEADER_AVAILABILITY_FINISH = "ENDTIME";
    //
    // TIMESLOTS Table
    static final String HEADER_TIMESLOTS_ID = "TIMESLOT_ID";
    static final String HEADER_TIMESLOTS_DATE = "DATE";
    static final String HEADER_TIMESLOTS_TIME = "TIME";
    static final String HEADER_TIMESLOTS_BOOKED = "BOOKED";
    //
    // BOOKINGS Table
    static final String HEADER_BOOKINGS_ID = "BOOKING_ID";
    static final String HEADER_BOOKINGS_CUSTOMER_ID = "CUSTOMER_ID";
    static final String HEADER_BOOKINGS_EMPLOYEE_ID = "EMPLOYEE_ID";
    static final String HEADER_BOOKINGS_TIMESLOT_ID = "TIMESLOT_ID";
    static final String HEADER_BOOKINGS_SERVICE_ID = "SERVICE_ID";
    //
    // ***************************************************************
    
    
    /* ATTENTION SEPTians.
     * These HashMaps are very easy to search/iterate through.
     * Use the syntaxes below:
     * 
     * If you're only interested in the keys (the integer id), you can iterate through the keySet() of the map:
     * 
     * for (Integer key : mapName.keySet())
     * {
     *     // ...
     * }
     * 
     * If you only need the values (in this case, the booking/customer/user etc. objects), use values().
     * Remember to change "Object" to the appropriate object type.
     * 
     * for (Object value : mapName.values())
     * {
     *     // ...
     * }
     * 
     * If you want access to both the key and value, use entrySet():
     * 
     * for (HashMap.Entry<Integer, Object> entry : mapName.entrySet())
     * {
     * 	   String key = entry.getKey();
     * 	   Object value = entry.getValue();
     *     // ...
     * }
     * 
     */
    private static HashMap<Integer, User> userMap = new HashMap<Integer, User>();
    private static HashMap<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
    private static HashMap<Integer, Timeslot> timeslotMap = new HashMap<Integer, Timeslot>();
    private static HashMap<Integer, Booking> bookingMap = new HashMap<Integer, Booking>();
    private static HashMap<Integer, Service> serviceMap = new HashMap<Integer, Service>();
    
    
    private static boolean runningFromJar()
    {
    	String jarTest = SystemDriver.class.getResource("SystemDriver.class").toString();
    	
    	if (jarTest.startsWith("rsrc:"))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /*  Copy out the db file if running in self-contained .JAR.
     *  Files within .JAR are read-only and would prevent us from writing changes to the DB.
     *  
     *  If running from within eclipse or any other file system, no need to do this,
     *  we can connect to the bin/db/database.mv.db file directly.
     */
    public static void extractDbFile()
    {
    	if (runningFromJar())
    	{
    		//System.out.println("RUNNING FROM JAR!");
        	String path = "db/database.mv.db";

        	InputStream ddlStream = Database.class
        		    .getClassLoader().getResourceAsStream(path);
        	File dbFile =  new File(path);
        	
        	/* Check that db hasn't already been extracted from previous use of the program.
        	 * If it has, we don't want to override any possible additions/changes to the
        	 * DB with a fresh copy from the jar!
        	 */
        	if (!dbFile.exists())
        	{ // make /db/ directory if it doesn't exist... Which is probably the case
            	if (!dbFile.getParentFile().exists())
            	{
            	     dbFile.getParentFile().mkdirs();
            	}
            	
        		try (FileOutputStream output = new FileOutputStream(dbFile);)
        		{
        			byte[] buffer = new byte[2048];
        			int r;
        			while(-1 != (r = ddlStream.read(buffer)))
        			{
        				output.write(buffer, 0, r);
        			}
            	}
        		catch (FileNotFoundException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		catch (IOException e)
        		{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}

    	}
    	else
    	{
    		//System.out.println("RUNNING FROM ECLIPSE");
    	}
    }
    
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
	    	if (runningFromJar())
	    	{
	    		dbConnection = DriverManager.getConnection(DB_CONNECTION_JAR, DB_USER, DB_PASSWORD);
	    	}
	    	else
	    	{
	    		dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
	    	}
	        return dbConnection;
	    }
	    catch (SQLException e)
	    {
	        System.out.println(e.getMessage());
	    }
	    return dbConnection;
	}
	
	public static void loadFromDB()
	{
		try
		{
			getUsers(); // MUST do in this order, so that the required collections are populated
			getServices(); // for adding object links to.
			getEmployees();
			getEmployeeServices();
			getAvailability();
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
		String getUsersQuery = "select * from " + TABLE_USERS;
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
				String name = rs.getString(HEADER_USERS_NAME);
				User user = new User(id, username, password, email, name, phone);
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
	
	static void getServices() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getServicesQuery = "select * from " + TABLE_SERVICES;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getServicesQuery);
			
			while (rs.next())
			{			
				int id = rs.getInt(HEADER_SERVICES_ID);
				String name = rs.getString(HEADER_SERVICES_NAME);
				int duration = rs.getInt(HEADER_SERVICES_DURATION);
				Service service = new Service(id, name, duration);
				serviceMap.put(id, service);
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
		String getUsersQuery = "select * from " + TABLE_EMPLOYEES;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getUsersQuery);
			
			while (rs.next())
			{			
				int id = rs.getInt(HEADER_EMPLOYEES_ID);
				String name = rs.getString(HEADER_EMPLOYEES_NAME);
				String phone = rs.getString(HEADER_EMPLOYEES_PHONE);
				String address = rs.getString(HEADER_EMPLOYEES_ADDRESS);
				Employee employee = new Employee(id, name, phone, address);
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
	
	static void getEmployeeServices() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		
		String getEmpServicesQuery = "select * from " + TABLE_EMPSERVICES;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getEmpServicesQuery);
			
			while (rs.next())
			{			
				int serviceId = rs.getInt(HEADER_EMPSERVICES_SID);
				int employeeId = rs.getInt(HEADER_EMPSERVICES_EMPID);
				
				Service service = serviceMap.get(serviceId);
				employeeMap.get(employeeId).addService(service);
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
	
	static void getAvailability() throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		String getAvailabilityQuery = "select * from " + TABLE_AVAILABILITY;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getAvailabilityQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_AVAILABILITY_ID); // not needed?
				int employee_id = rs.getInt(HEADER_AVAILABILITY_EMPLOYEE_ID); 
				String dayOfWeek = rs.getString(HEADER_AVAILABILITY_DAY);
				java.sql.Time sqlStartTime = rs.getTime(HEADER_AVAILABILITY_START);
				LocalTime startTime = sqlStartTime.toLocalTime();
				java.sql.Time sqlFinishTime = rs.getTime(HEADER_AVAILABILITY_FINISH);
				LocalTime finishTime = sqlFinishTime.toLocalTime();
				
				// Put availability entry into appropriate employee's array
				Employee e = employeeMap.get(employee_id);
				e.addAvailability(dayOfWeek, startTime, finishTime);
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
		String getTimeslotsQuery = "select * from " + TABLE_TIMESLOTS;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getTimeslotsQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_TIMESLOTS_ID);
				String dateString = rs.getString(HEADER_TIMESLOTS_DATE);
		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		    	LocalDate date = LocalDate.parse(dateString, formatter);
				
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
		String getBookingsQuery = "select * from " + TABLE_BOOKINGS;
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(getBookingsQuery);
			
			while (rs.next())
			{
				int id = rs.getInt(HEADER_BOOKINGS_ID);
				
				// Convert ID's to actual objects
				int customerId = rs.getInt(HEADER_BOOKINGS_CUSTOMER_ID);
				int employeeId = rs.getInt(HEADER_BOOKINGS_EMPLOYEE_ID);
				int timeslotId = rs.getInt(HEADER_BOOKINGS_TIMESLOT_ID);
				int serviceId = rs.getInt(HEADER_BOOKINGS_SERVICE_ID);
				
				User customer = userMap.get(customerId);
				Employee employee = employeeMap.get(employeeId);
				Timeslot timeslot = timeslotMap.get(timeslotId);
				Service service = serviceMap.get(serviceId);
				
				Booking booking = new Booking(id, customer, employee, timeslot, service);
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
	
	public static int addUserToDB(String username, String password, String email, String name, String phone) throws SQLException
	{
		Connection c = getDBConnection();
		PreparedStatement insertStmt = null;
		PreparedStatement selectStmt = null;
		int id = 0;
		
		String insertStatement = "INSERT INTO " + TABLE_USERS + " ("
									   + HEADER_USERS_USERNAME
								+ ", " + HEADER_USERS_PASSWORD
								+ ", " + HEADER_USERS_EMAIL
								+ ", " + HEADER_USERS_PHONE
								+ ", " + HEADER_USERS_NAME
								+ ") values (?, ?, ?, ?, ?)";
		try
		{
			c.setAutoCommit(false);
			
			insertStmt = c.prepareStatement(insertStatement);
			
			// fill in variables into sql statement
			insertStmt.setString(1, username);
			insertStmt.setString(2, password);
			insertStmt.setString(5, name);
			
			// take care of possible null entries
			if (email != null)
			{
				insertStmt.setString(3, email);
			}
			else
			{
				insertStmt.setNull(3, Types.VARCHAR);
			}
			if (phone != null)
			{
				insertStmt.setString(4, phone);
			}
			else
			{
				insertStmt.setNull(4, Types.VARCHAR);
			}
			
			insertStmt.executeUpdate();
			insertStmt.close();
            
			selectStmt = c.prepareStatement("SELECT " + HEADER_USERS_ID
											+ " FROM " + TABLE_USERS
											+ " WHERE " + HEADER_USERS_USERNAME
											+ "='" + username + "';");
			
			ResultSet rs = selectStmt.executeQuery();
			rs.next();	// increment resultset to first result.
						// should be only one result as userID is a primary key.
			id = rs.getInt(HEADER_USERS_ID);
			
			selectStmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			System.out.println("Exception Message " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return id;
	}
	
	public static int addEmployeeToDB(String name, String phone, String address) throws SQLException
	{
		Connection c = getDBConnection();
		PreparedStatement insertStmt = null;
		PreparedStatement selectStmt = null;
		int id = 0;
		
		String insertStatement = "INSERT INTO " + TABLE_EMPLOYEES + " ("
									   + HEADER_EMPLOYEES_NAME
									   + ", " + HEADER_EMPLOYEES_PHONE
									   + ", " + HEADER_EMPLOYEES_ADDRESS
									   + ") values (?, ?, ?)";
		try
		{
			c.setAutoCommit(false);
			
			insertStmt = c.prepareStatement(insertStatement);
			
			// fill in variables into sql statement
			insertStmt.setString(1, name);
			insertStmt.setString(2, phone);
			insertStmt.setString(3, address);
			insertStmt.executeUpdate();
			insertStmt.close();
            
			selectStmt = c.prepareStatement("SELECT " + HEADER_EMPLOYEES_ID
											+ " FROM " + TABLE_EMPLOYEES
											+ " WHERE " + HEADER_EMPLOYEES_NAME
											+ "='" + name + "';");
			
			ResultSet rs = selectStmt.executeQuery();
			rs.next();	// increment resultset to first result.
						// should be only one result.
			id = rs.getInt(HEADER_EMPLOYEES_ID);
			
			selectStmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			System.out.println("Exception Message " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return id;
	}
	
	public static boolean removeEmployeeFromDB(int id) throws SQLException
	{
		Connection c = getDBConnection();
		Statement stmt = null;
		
		String deleteStatement = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + HEADER_EMPLOYEES_ID + "=" + id + ";";
		try
		{
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute(deleteStatement);
			stmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return true;
	}
	
	static int addTimeslotToDB(LocalDate date, LocalTime time, Boolean booked) throws SQLException
	{
		Connection c = getDBConnection();
		PreparedStatement insertStmt = null;
		PreparedStatement selectStmt = null;
		int id = 0;
		
		String insertStatement = "INSERT INTO " + TABLE_TIMESLOTS + " ("
									   + HEADER_TIMESLOTS_DATE
								+ ", " + HEADER_TIMESLOTS_TIME
								+ ", " + HEADER_TIMESLOTS_BOOKED
								+ ") values (?, ?, ?)";
		try
		{
			c.setAutoCommit(false);
			
			insertStmt = c.prepareStatement(insertStatement);
			
			// fill in variables into sql statement
			java.sql.Date dbDate = java.sql.Date.valueOf(date);
			insertStmt.setDate(1, dbDate);
			java.sql.Time dbTime = java.sql.Time.valueOf(time);
			insertStmt.setTime(2, dbTime);
			insertStmt.setBoolean(3, booked);
			
			insertStmt.executeUpdate();
			insertStmt.close();
            
			selectStmt = c.prepareStatement("SELECT " + HEADER_TIMESLOTS_ID
											+ " FROM " + TABLE_TIMESLOTS
											+ " WHERE " + HEADER_TIMESLOTS_DATE
											+ "='" + dbDate + "' "
											+ "AND " + HEADER_TIMESLOTS_TIME
											+ "='" + dbTime + "';");
			
			ResultSet rs = selectStmt.executeQuery();
			rs.next();	// increment resultset to first result.
						// should be only one result.
			id = rs.getInt(HEADER_TIMESLOTS_ID);
			
			selectStmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			System.out.println("Exception Message " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return id;
	}
	
	public static int addBookingToDB(int employeeId, int customerId, int timeslotId, int serviceId) throws SQLException
	{
		Connection c = getDBConnection();
		PreparedStatement insertStmt = null;
		PreparedStatement selectStmt = null;
		int id = 0;
		
		String insertStatement = "INSERT INTO " + TABLE_BOOKINGS + " ("
									   + HEADER_BOOKINGS_EMPLOYEE_ID
								+ ", " + HEADER_BOOKINGS_CUSTOMER_ID
								+ ", " + HEADER_BOOKINGS_TIMESLOT_ID
								+ ", " + HEADER_BOOKINGS_SERVICE_ID
								+ ") values (?, ?, ?, ?)";
		try
		{
			c.setAutoCommit(false);
			
			insertStmt = c.prepareStatement(insertStatement);
			
			// fill in variables into sql statement
			insertStmt.setInt(1, employeeId);
			insertStmt.setInt(2, customerId);
			insertStmt.setInt(3, timeslotId);
			insertStmt.setInt(4, serviceId);
			
			insertStmt.executeUpdate();
			insertStmt.close();
            // Select the entry we just entered and get the generated id.
			// Only (employee id + timeslot id) are required in order to return a unique entry -
			// The other columns are just for additional information
			selectStmt = c.prepareStatement("SELECT " + HEADER_BOOKINGS_ID
											+ " FROM " + TABLE_BOOKINGS
											+ " WHERE " + HEADER_BOOKINGS_EMPLOYEE_ID
											+ "=" + employeeId + " AND "
											+ HEADER_BOOKINGS_TIMESLOT_ID + "=" + timeslotId + ";");
			
			ResultSet rs = selectStmt.executeQuery();
			rs.next();	// increment resultset to first result.
						// should be only one result.
			id = rs.getInt(HEADER_BOOKINGS_ID);
			
			selectStmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			System.out.println("Exception Message " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return id;
	}
	
	public static int addWorkingTimesToDB(int employeeId, String dayOfWeek, LocalTime start, LocalTime finish) throws SQLException
	{
		Connection c = getDBConnection();
		PreparedStatement insertStmt = null;
		PreparedStatement selectStmt = null;
		int id = 0;	
		
		String insertStatement = "INSERT INTO " + TABLE_AVAILABILITY + " ("
									   + HEADER_AVAILABILITY_EMPLOYEE_ID
								+ ", " + HEADER_AVAILABILITY_DAY
								+ ", " + HEADER_AVAILABILITY_START
								+ ", " + HEADER_AVAILABILITY_FINISH
								+ ") values (?, ?, ?, ?)";
		try
		{
			c.setAutoCommit(false);
			
			insertStmt = c.prepareStatement(insertStatement);
			
			// fill in variables into sql statement
			insertStmt.setInt(1, employeeId);
			insertStmt.setString(2, dayOfWeek);
			java.sql.Time dbStart = java.sql.Time.valueOf(start);
			insertStmt.setTime(3, dbStart);
			java.sql.Time dbFinish = java.sql.Time.valueOf(finish);
			insertStmt.setTime(4, dbFinish);
			
			insertStmt.executeUpdate();
			insertStmt.close();
            // Select the entry we just entered and get the generated id.
			// Only (employee id + timeslot id) are required in order to return a unique entry -
			// The other columns are just for additional information
			selectStmt = c.prepareStatement("SELECT " + HEADER_AVAILABILITY_ID
											+ " FROM " + TABLE_AVAILABILITY
											+ " WHERE " + HEADER_AVAILABILITY_EMPLOYEE_ID
											+ "=" + employeeId + " AND "
											+ HEADER_AVAILABILITY_DAY + "=\'" + dayOfWeek + "\';");
			
			ResultSet rs = selectStmt.executeQuery();
			rs.next();	// increment resultset to first result.
						// should be only one result.
			id = rs.getInt(HEADER_AVAILABILITY_ID);
			
			selectStmt.close();
			c.commit();
		}
		catch (SQLException e)
		{
			System.out.println("Exception Message " + e.getLocalizedMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c.close();
		}
		return id;
	}
	
	public static HashMap<Integer, User> getUserMap()
	{
		return userMap;
	}
	
	public static HashMap<Integer, Employee> getEmployeeMap()
	{
		return employeeMap;
	}
	
	public static HashMap<Integer, Timeslot> getTimeslotMap()
	{
		return timeslotMap;
	}
	
	public static HashMap<Integer, Booking> getBookingMap()
	{
		return bookingMap;
	}
	
	public static HashMap<Integer, Service> getServiceMap()
	{
		return serviceMap;
	}
}
