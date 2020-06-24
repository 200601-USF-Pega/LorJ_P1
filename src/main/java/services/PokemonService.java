package services;

import exceptions.TeamTransactionException;
import models.Pokemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PokemonService
{
	private Pokemon [] localTeam = new Pokemon[6];
	private int currentIndex = 0;

	public int getIndex()
	{
		return currentIndex;
	}

	public void addPokemon(Pokemon p) throws TeamTransactionException
	{
		if(currentIndex < 6)
		{
			localTeam[currentIndex++] = p;
		}
		else
		{
			throw new TeamTransactionException();
		}
	}
	
	public Pokemon removePokemon(int index) throws TeamTransactionException
	{
		if(index < currentIndex)
		{
			Pokemon p = localTeam[index];
			for(int i = index; i < currentIndex; i++)
			{
				localTeam[i] = localTeam[i + 1];
			}
			localTeam[--currentIndex] = null;
			return p;
		}
		else
		{
			throw new TeamTransactionException();
		}
	}

	public void viewTeam()
	{

		for(int i = 0; i < 6; i++)
		{
			if(localTeam[i] != null)
			{
				System.out.println("[" + i + "] " + localTeam[i]);
			}
		}
	}

	public void exportTeam(String name)
	{
		File file = new File("src/resources/" + name + ".txt");
		try
		{
			FileWriter fw = new FileWriter(file, false);
			for(Pokemon p : localTeam)
			{
				if(p != null)
				{
					fw.write("\n" + p.exportSmogon() + "\n");
				}
			}
			fw.flush();
			fw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
}