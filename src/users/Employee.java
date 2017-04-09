package users;

import java.util.ArrayList;
import java.util.List;

import bookings.Timeslot;

public class Employee
{
    private int id;
    private String name;

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

}
