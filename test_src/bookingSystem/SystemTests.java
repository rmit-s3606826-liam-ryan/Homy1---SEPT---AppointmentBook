package bookingSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class SystemTests
{

    @Test
    public void doesReadFromFileMethodProperlyReadAndLoadFileContents()
    {
        String customerInfoFileName = "src/users/customerinfo";
        
        SystemDriver testSystem = new SystemDriver();

        assertTrue(testSystem.loadFromFile(customerInfoFileName));
    }

}
