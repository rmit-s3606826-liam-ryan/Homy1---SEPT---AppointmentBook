package bookingSystem;

public class AuthException extends Exception {
	
	public AuthException()
	{
		
	}
	
	public AuthException(String error)
	{
		super(error);
	}
}
