package services;

import models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonService
{
	private ArrayList<Pokemon> localTeam = new ArrayList<Pokemon>();

	public List<Pokemon> getLocalTeam()
	{
		return localTeam;
	}

	public void addPokemon(Pokemon p)
	{
		localTeam.add(p);
	}

	public Pokemon popPokemon(int i)
	{
		return localTeam.remove(i);
	}

}
