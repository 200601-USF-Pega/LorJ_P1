package dao;

import java.sql.*;

public class TrainerRepoDB implements ITrainerRepo
{
	private Connection cm;

	public TrainerRepoDB(Connection cm)
	{
		this.cm = cm;
	}

	@Override
	public void addTrainer(String name, String password)
	{
		try
		{
			PreparedStatement ps = cm.prepareStatement("INSERT INTO trainer_table (trainer_name, t_password) VALUES (?, ?)");
			ps.setString(1, name);
			ps.setString(2, password);
			ps.execute();
		}
		catch(SQLException e)
		{
			e.getStackTrace();
		}
	}

	@Override
	public void deleteTrainer(int id)
	{
		try
		{
			PreparedStatement ps = cm.prepareStatement("DELETE FROM trainer_table WHERE t_id=?");
			ps.setInt(1, id);
			ps.execute();
		}
		catch(SQLException e)
		{
			e.getStackTrace();
		}
	}

	@Override
	public void viewAllTrainers()
	{
		try
		{
			Statement s = cm.createStatement();
			s.executeQuery("SELECT * FROM trainer_table");
			ResultSet rs = s.getResultSet();
			while(rs.next())
			{
				System.out.println("[" + rs.getInt("t_id") + "] " + rs.getString("trainer_name"));
			}
		}
		catch(SQLException e)
		{
			e.getStackTrace();
		}
	}

}
