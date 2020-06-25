package web;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager
{
	private static Connection connection = createConnection();

	public static Connection createConnection()
	{
		try
		{
			FileInputStream fis = new FileInputStream("connection.prop");
			Properties p = new Properties();
			p.load(fis);

			return DriverManager.getConnection(p.getProperty("hostname"), p.getProperty("username"), p.getProperty("password"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection()
	{
		return connection;
	}

	public static void close()
	{
		try
		{
			connection.close();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

	public void finalize()
	{
		try
		{
			connection.close();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

}
