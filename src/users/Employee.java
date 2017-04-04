package users;

public class Employee
{
    private String ID;
    private String name;

    public Employee(String name, String ID)
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
