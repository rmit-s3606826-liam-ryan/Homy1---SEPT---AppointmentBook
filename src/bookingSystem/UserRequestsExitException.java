package bookingSystem;

/** thrown when user enter 'e' or 'exit', is used for the purposes of
 * returning to the main menu at any point, even though an exception 
 * should always add some information, this is not a true exception and is
 * only a method of control. as such, no new information is given when
 * it is thrown*/
public class UserRequestsExitException extends Exception
{

    /** serialVerisonUID to prevent invalidClassException*/
    private static final long serialVersionUID = 1L;
    
    public UserRequestsExitException()
    {
        
    }

}
