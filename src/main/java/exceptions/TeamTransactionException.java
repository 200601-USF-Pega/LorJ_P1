package exceptions;

public class TeamTransactionException extends RuntimeException
{

	public TeamTransactionException()
	{
		super();
	}

	public String getMessage()
	{
		return "Cannot complete transaction.";
	}

}
