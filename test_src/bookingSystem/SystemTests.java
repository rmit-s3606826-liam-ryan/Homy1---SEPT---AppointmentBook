package bookingSystem;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.io.IOException;

import org.junit.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.TemporaryFolder;
import java.util.ArrayList;
import java.util.List;


import users.User;

public class SystemTests
{
    private SystemDriver testSystem;
   
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Rule
    public final TextFromStandardInputStream systemInMock
    = emptyStandardInputStream();

    
    @BeforeClass
    public static void setUpClass()
    {
        System.out.println("---Set up class");
    }

    @Before
    public void setUp() throws Exception
    {
        System.out.println("-Set up");
        testSystem = new SystemDriver();
    }
    
    @After
    public void tearDown() throws IOException
    {
        System.out.println("-Tear down");
        testSystem = null;
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        System.out.println("---Tear down class");
    }
       
    @Test
    public void testingRegisterFunction()
    {
        User testUser = new User("username", "password");
        
    	// provides mocked user input for next called function
        systemInMock.provideLines("username", "password", "5");
        testSystem.register();
        
        assertEquals(testSystem.authUser.getName(), testUser.getName());
        assertEquals(testSystem.authUser.getPassword(), testUser.getPassword());      
    }
    
    @Test (expected = DuplicateUserException.class)
    public void testRegisterDuplicateUsername_fail() throws DuplicateUserException
    {
    	String duplicateUsername = "testUser";
    	User user1 = new User(duplicateUsername, "pw1");
    	User user2 = new User(duplicateUsername, "pw2");
    	try
    	{
    		testSystem.addUser(user1);
        	
        	User user = testSystem.userList.get(0);
        	assertEquals(user.getName(), duplicateUsername); // check that a user exists in the system with username "testUser"
        	//int sizeBeforeTest = testSystem.userList.size();
        	
        	testSystem.addUser(user2);						// attempt to add account with duplicate username
        	//int sizeAfterTest = testSystem.userList.size();
        	//assertEquals(sizeBeforeTest, sizeAfterTest); // check that the userList array has not increased in size
    	} // TODO ***prevent registration tests from spamming our customerinfo.dat with test user accounts***
    	catch (IOException e) {}
    }
    
    @Test
    public void testingLoginFunction()
    {
        User testUser = new User("username", "password");
        
    	// provides mocked user input for next called function
        systemInMock.provideLines("username", "password", "3");
        testSystem.loadFromFile("testFile.dat");
        System.out.println(testSystem.userList.get(0).getName());
        testSystem.login();
        
        User loggedInAs = testSystem.getAuthUser();
        
        assertEquals(loggedInAs.getName(), testUser.getName());
        assertEquals(loggedInAs.getPassword(), testUser.getPassword());      
    }

    @Test
    public void testWriteToFile() // TODO NEEDS EXPANSION!! a lot of different cases of input
    {
    	try
    	{
    		testSystem.writeToFile("username,password\n", "testFile.dat");
    	}
    	catch (IOException e)
    	{
    		Assert.fail(); // fail the test if writeToFile() throws IOException
    	}
    }
    
    @Test
    public void doesReadFromFileMethodProperlyReadAndLoadFileContents()
    {
        String testFileName = "testFile.dat";
        
        assertTrue(testSystem.loadFromFile(testFileName));
        User testUser = new User("username", "password");
        User userFromFile = testSystem.userList.get(0);

        assertEquals(userFromFile.getName(), testUser.getName());
        assertEquals(userFromFile.getPassword(), testUser.getPassword());      
    }
    
    @Test
    public void testLogout()
    {
    	User testUser = new User("testUsername","testPW");
    	testSystem.setAuthUser(testUser);
    	
    	assertEquals(testSystem.getAuthUser(), testUser); // Check that authorised user is set
    	testSystem.logout();
    	assertEquals(testSystem.getAuthUser(), null); // Now check that logout() removed the authorised user
    }
    
    @Test
    public void testThatBookingCorrectlyAssignedToCustomer()
    {
    	List<timeSlots> testBookingList = new ArrayList<timeslots>();
    	User testUser = new User("username", "password", testBookingList);
    	timeSlots testBooking = new timeSlots("24/4/17", "1:00", "Hooch");
    	testBookingList.add(testBooking);
    	
    	testSystem.setAuthUser(testUser);
    	
    	// Check that authorised user is set
    	assertEquals(testSystem.getAuthUser(), testUser);
    	
    	assertEquals(testSystem.getAuthUser().getBooking(), testBookingList.get(0));
    	  	
    }
    
    @Test
    public void testThatAvailableBookingCorrectlyAddedToList()
    {
    	List<timeSlots> testAvailableBooking = new ArrayList<timeslots>();
    	timeSlots testBooking = new timeSlots("24/3/17", "1:00", "Hooch");
    	testAvailableBooking.add(testBooking);
   	
    	assertEquals(testSystem.getAvailableBookings(), testBooking);    	
    }
    
    @Test
    public void testThatBookingProperlyRemovedFromAvailableBookingsWhenCustomerBooks()
    {
    	List<timeSlots> testAvailableBooking = new ArrayList<timeslots>();
    	timeSlots testBooking = new timeSlots("24/3/17", "1:00", "Hooch");
    	timeSlots testBooking2 = new timeSlots("25/3/17", "1:00", "Hooch");
    	
    	testAvailableBooking.add(testBooking);
    	testAvailableBooking.add(testBooking2);

    	assertEquals(testAvailableBooking.get(0), testBooking);
    	
    	User testUser = new User("username", "password", testAvailableBooking);
    	testSystem.setAuthUser(testUser);

    	makeBooking(testSystem.authUser, testBooking);
    	
    	assertEquals(testAvailableBooking.get(0), testBooking2);   	
    }
    
    @Test
    public void testThatOwnerCreatedTimeSlotProperlyAddedToAvailableBookings()
    {
    	timeSlots testBooking = new timeSlots("25/3/17", "1:00", "Hooch");
    	
    	// provides mocked user input for next called function
        systemInMock.provideLines("25/3/17", "1:00", "Hooch");
        
        addBooking();
        
        assertEquals(testSystem.bookingList.get(0), testBooking);
    }
}
