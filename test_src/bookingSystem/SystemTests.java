package bookingSystem;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
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
    
    @Test
    public void testingLoginFunction()
    {
        User testUser = new User("username", "password");
        
        systemInMock.provideLines("username", "password", "3");
        testSystem.loadFromFile("testFile.dat");
        System.out.println(testSystem.userList.get(0).getName());
        testSystem.login();
        
        User loggedInAs = testSystem.getAuthUser();
        
        assertEquals(loggedInAs.getName(), testUser.getName());
        assertEquals(loggedInAs.getPassword(), testUser.getPassword());      
    }

    @Test
    public void testingWriteToFile() throws Exception
    {
        assertTrue(testSystem.writeToFile("username,password\n", "testFile.dat"));
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

}
