package bookingSystem;

import static org.junit.Assert.*;

import org.junit.Test;

import users.User;

public class SystemTests
{

    @Test
    public void doesReadFromFileMethodProperlyReadAndLoadFileContents()
    {
        String customerInfoFileName = "src/users/customerinfo";
        SystemDriver testSystem = new SystemDriver();
        
        assertTrue(testSystem.loadFromFile(customerInfoFileName));
        User testUser = new User("Jimbob", "password");
        User userFromFile = testSystem.userList.get(0);
        
        assertEquals(userFromFile.getName(), testUser.getName());
        assertEquals(userFromFile.getPassword(), testUser.getPassword());
    }

}
