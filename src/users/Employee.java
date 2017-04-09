package users;

import java.time.LocalTime;
import java.util.HashMap;

public class Employee
{
    private int id;
    private String name;
    private HashMap<String, LocalTime[]> availability = new HashMap<String, LocalTime[]>();

    public Employee(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getID()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    
    public HashMap<String, LocalTime[]> getAvailability()
    {
    	return availability;
    }
    
    public void addAvailability(String dayOfWeek, LocalTime start, LocalTime finish)
    {
    	LocalTime[] times = {start, finish};
    	availability.put(dayOfWeek, times);
    }
}
