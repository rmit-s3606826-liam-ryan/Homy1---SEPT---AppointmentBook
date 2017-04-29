package users;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import bookingSystem.Service;

public class Employee
{
    private int id;
    private String name;
    private String phone;
    private String address;
    private HashMap<String, LocalTime[]> availability = new HashMap<String, LocalTime[]>();
    private ArrayList<Service> services = new ArrayList<Service>();

    public Employee(int id, String name, String phone, String address)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public int getID()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public String getAddress()
    {
        return address;
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
    
    public ArrayList<Service> getServices()
    {
    	return services;
    }
    
    public void addService(Service service)
    {
    	services.add(service);
    }
}
