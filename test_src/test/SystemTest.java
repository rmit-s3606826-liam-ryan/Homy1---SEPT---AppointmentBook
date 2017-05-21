package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.TemporaryFolder;

import bookingSystem.DuplicateUserException;
import bookingSystem.SystemDriver;
import users.User;

public class SystemTest
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
        testRegVal = new RegistrationValidation();
        
    }
    
    @After
    public void tearDown() throws IOException
    {
        System.out.println("-Tear down");
        testSystem = null;
        testRegVal = null;
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        System.out.println("---Tear down class");
    }
    
    /**
     * Tests for registration validation functions
     **/
    @Test
    public void testThatUsernameValidationFunctionAcceptsValidInput()
    {
        assertTrue(testSystem.validateUsername("Jimbob"));
    }
    
    @Test
    public void testThatUsernameValidationFunctionDoesNotAcceptIllegalCharacters()
    {
        assertFalse(testSystem.validateUsername("!@"));
    }

    @Test
    public void testThatPasswordValidationFunctionReturnsTrueWhenPasswordEqualsConfirmPassword()
    {
        assertTrue(testSystem.validatePassword("password", "password"));
    }

    @Test
    public void testThatPasswordValidationFunctionReturnsFalseWhenPasswordNotEqualToConfirmPassword()
    {
        assertFalse(testSystem.validatePassword("password", "notPassword"));
    }
    
    @Test
    public void testThatEmailValidationFunctionReturnsTrueWhenInputFormatValid()
    {
        assertTrue(testSystem.validateEmail("dont.look@me.now"));
    }

    @Test
    public void testThatEmailValidationFunctionReturnsFalseWithNoATSymbol()
    {
        assertFalse(testSystem.validateEmail("dont.lookme.now"));
    }

    @Test
    public void testThatEmailValidationFunctionReturnsFalseWhenNothingBeforeATSymbol()
    {
        assertFalse(testSystem.validateEmail("@me.now"));
    }

    @Test
    public void testThatEmailValidationFunctionReturnsFalseWhenNothingAfterATSymbol()
    {
        assertFalse(testSystem.validateEmail("dont.look@"));
    }

    @Test
    public void testThatEmailValidationFunctionReturnsFalseWhenNoPeriodAfterATSymbol()
    {
        assertFalse(testSystem.validateEmail("dont.look@me"));
    }
    
    @Test
    public void testThatEmailValidationFunctionReturnsFalseWithIllegalSymbol()
    {
        assertFalse(testSystem.validateEmail("dont.look�@me.now"));
    }

    @Test
    public void testThatNameValidationFunctionReturnsTrueWithValidInput()
    {
        assertTrue(testSystem.validateName("Reginal'd John-Darcy"));
    }

    @Test
    public void testThatNameValidationFunctionReturnsFalseWithIllegalCharacter()
    {
        assertFalse(testSystem.validateName("Reginald *John-Darcy"));
    }

    @Test
    public void testThatPhoneValidationFunctionReturnsTrueWithValidLengthOfTen()
    {
        assertTrue(testSystem.validatePhone("0400000001"));
    }

    @Test
    public void testThatPhoneValidationFunctionReturnsTrueWithValidLengthOf8()
    {
        assertTrue(testSystem.validatePhone("54333322"));
    }

    @Test
    public void testThatPhoneValidationFunctionReturnsFalseWithInvalidLengthOfEleven()
    {
        assertFalse(testSystem.validatePhone("04000000011"));
    }

    @Test
    public void testThatPhoneValidationFunctionReturnsFalseWithInalidLengthOfSeven()
    {
        assertFalse(testSystem.validatePhone("0400000"));
    }
    
    
    @Test
    public void testDateParse_NumberOfDayDigits()
    {
    	// If date does not parse successfully, will throw a DateTimeParseException
    	String dateString = "4/12/1990";
    	LocalDate date = testSystem.parseDate(dateString);
    	
    	dateString = "14/12/1990";
    	date = testSystem.parseDate(dateString);
    }
    
    @Test
    public void testDateParse_NumberOfMonthDigits()
    {
    	// If date does not parse successfully, will throw a DateTimeParseException
    	String dateString = "10/1/1990";
    	LocalDate date = testSystem.parseDate(dateString);
    	
    	dateString = "10/11/1990";
    	date = testSystem.parseDate(dateString);
    }
    
    @Test
    public void testDateParse_OneDayOneMonth()
    {
    	// If date does not parse successfully, will throw a DateTimeParseException
    	String dateString = "6/7/1944";
    	LocalDate date = testSystem.parseDate(dateString);
    }
    
    @Test (expected = DateTimeParseException.class)
    public void testDateParse_DayOutOfBounds()
    {
    	String dateString = "36/10/1964";
    	LocalDate date = testSystem.parseDate(dateString);
    }
    
    @Test (expected = DateTimeParseException.class)
    public void testDateParse_MonthOutOfBounds()
    {
    	String dateString = "14/16/1964";
    	LocalDate date = testSystem.parseDate(dateString);
    }
    
    @Test (expected = DateTimeParseException.class)
    public void testDateParse_YearOutOfBounds()
    {
    	String dateString = "4/10/19021";
    	LocalDate date = testSystem.parseDate(dateString);
    }
    
    @Test
    public void testingRegisterFunction()
    {
        User testUser = new User("username", "password", null);
        
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
    	User user1 = new User(duplicateUsername, "pw1", null);
    	User user2 = new User(duplicateUsername, "pw2", null);
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
    public void testingLoginFunction() // TODO-Adam http://www.h2database.com/html/features.html#in_memory_databases
    {
        User testUser = new User("username", "password", null);
        
      	// provides mocked user input for next called function
        systemInMock.provideLines("username", "password");
        testSystem.loadFromFile("testFile.dat");
        System.out.println(testSystem.userList.get(0).getName());
        testSystem.login();
        
        User loggedInAs = testSystem.getAuthUser();
        
        assertEquals(loggedInAs, testUser); 
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
        User testUser = new User("username", "password", null);
        User userFromFile = testSystem.userList.get(0);

        assertEquals(userFromFile.getName(), testUser.getName());
        assertEquals(userFromFile.getPassword(), testUser.getPassword());      
    }
    
    @Test
    public void testLogout()
    {
    	User testUser = new User("testUsername","testPW", null);
    	testSystem.setAuthUser(testUser);
    	
    	assertEquals(testSystem.getAuthUser(), testUser); // Check that authorised user is set
    	testSystem.logout();
    	assertEquals(testSystem.getAuthUser(), null); // Now check that logout() removed the authorised user
    }
    
    @Test
    public void testThatBookingCorrectlyAssignedToCustomer()
    {
    	List<timeSlots> testBookingList = new ArrayList<timeSlots>();
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
    	List<timeSlots> testAvailableBooking = new ArrayList<timeSlots>();
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
