package bookingSystem;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.io.IOException;

import org.junit.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.TemporaryFolder;

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
        
        systemInMock.provideLines("username", "password", "3");
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
    public void testingLoginFunction() // TODO-Adam http://www.h2database.com/html/features.html#in_memory_databases
    {
        User testUser = new User("username", "password");
        
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
}
