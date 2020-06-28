package test;

import web.ConnectionManager;
import dao.PokemonRepoDB;
import models.Genders;
import models.Natures;
import models.Pokemon;
import models.Trainer;
import org.junit.AfterClass;
import org.junit.Test;
import dao.LoginDB;

import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;

public class Tests
{

	Pokemon p = new Pokemon();

	@Test
	public void shouldAnswerTrue()
	{
		assertTrue(true);
	}

	@Test
	public void trainerData()
	{
		Trainer t = new Trainer("TEST", 0);
		assertEquals(t.getName(), "TEST");

	}

	@Test
	public void testSmogon()
	{
		assertEquals("MissingNo.\nAbility: N/A\nLevel: 100\nShiny: No\nEVs: \nAdamant Nature\n - \n - \n - \n - ", p.exportSmogon());
	}

	@Test
	public void testGender()
	{
		assertEquals(p.getGender(), Genders.Genderless);
	}

	@Test
	public void testNature()
	{
		assertEquals(p.getNature(), Natures.Adamant);
	}

	@Test
	public void testLogin()
	{
		LoginDB ls = new LoginDB(ConnectionManager.getConnection());
		assertEquals(ls.login("RED", "Pikachu").getName(), "RED");
	}

	@Test
	public void testLoginFail()
	{
		LoginDB ls = new LoginDB(ConnectionManager.getConnection());
		assertNull(ls.login("a", "a"));
	}

	@Test
	public void repoView()
	{
		PokemonRepoDB repo = new PokemonRepoDB(ConnectionManager.getConnection());
		repo.getAllPokemon();
	}

	@Test
	public void restGetPokemon()
	{
		try
		{
			URL url = new URL("http://localhost:8080/Project1/services/pokemon/allpokemon");
			URLConnection urlc = url.openConnection();
		}
		catch(Exception e)
		{
			fail("Should not have thrown any exception");
		}
	}

	@Test
	public void restGetTrainers()
	{
		try
		{
			URL url = new URL("http://localhost:8080/Project1/services/trainer/alltrainers");
			URLConnection urlc = url.openConnection();
		}
		catch(Exception e)
		{
			fail("Should not have thrown any exception");
		}
	}

	@AfterClass
	public static void finish()
	{
		ConnectionManager.close();
	}

}
