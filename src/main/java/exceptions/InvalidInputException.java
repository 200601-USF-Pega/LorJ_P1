package exceptions;

public class InvalidInputException extends RuntimeException
{

	private static final long serialVersionUID = 5604017208769066149L;
	
	public InvalidInputException()
	{
		super();
	}
	
	public String getMessage()
	{
		return "Invalid Input";
	}

}
