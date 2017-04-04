package bookingSystem;

@SuppressWarnings("serial")
public class AuthException extends Exception {
	
	public AuthException()
	{
		
	}
	
	public AuthException(String message)
	{
		super(message);
	}
	
}
