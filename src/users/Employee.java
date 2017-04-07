package users;

import java.util.ArrayList;
import java.util.List;

import Bookings.Timeslot;

public class Employee
{
    private int ID;
    private String name;

    public Employee(String name, int ID)
    {
        this.name = name;
        this.ID = ID;
    }

    public Object getID()
    {
        return ID;
    }

    public String getName()
    {
        return name;
    }

}
