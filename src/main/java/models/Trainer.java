package models;

public class Trainer
{
	private String name;
	private int id;

	public Trainer(String name, int id)
	{
		this.name = name;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getID()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
