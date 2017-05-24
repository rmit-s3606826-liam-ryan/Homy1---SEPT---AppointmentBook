package bookingSystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import db.Database;

public class SeptFacade
{
	private static SeptFacade facade = null;
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * loads the system at start up, call functions to load users, business data, booking information, etc.
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
		
		Database db = Database.getDb();
		db.extractDbFile();
		db.loadFromDB();
		logger.info("database loaded into system");
	}
    
    public static SeptFacade getFacade()
    {
    	if (facade == null)
    	{
    		facade = new SeptFacade();
    	}
    return facade;
    }
    
    public void addBusiness(String businessName, String ownerName, String address, String phone, String adminUsername)
    {
    	Database db = Database.getDb();
    	try
    	{
    		db.updateBusiness(businessName, ownerName, address, phone, adminUsername);
		}
    	catch (SQLException e)
    	{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    	
    	Business business = Business.getBusiness();
    	business.updateBusiness(businessName, ownerName, address, phone, adminUsername);
    }
}
