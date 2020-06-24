package controllers;

import dao.ConnectionManager;
import dao.IPokemonRepo;
import dao.PokemonRepoDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PCController
{
	private static IPokemonRepo repo = new PokemonRepoDB(ConnectionManager.getConnection());

	public static void optionSelect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String input = req.getParameter("menu_opt");
		switch(input)
		{
		case "WITHDRAW":
			break;
		}
	}

	public static void addPokemon(HttpServletRequest req, HttpServletResponse resp)
	{

	}

	public static void getPokemon(HttpServletRequest req, HttpServletResponse resp)
	{

	}
}
