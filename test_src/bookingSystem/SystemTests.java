package bookingSystem;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import users.User;

public class SystemTests
{
    private SystemDriver testSystem;
   
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
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
