package bookingSystem;

@SuppressWarnings("serial")
public class DuplicateUserException extends Exception {
	
	public DuplicateUserException()
	{
		
	}
	
	public DuplicateUserException(String message)
	{
		super(message);
	}
}
