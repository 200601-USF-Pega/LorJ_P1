package controllers;

import dao.IPokemon;
import dao.PokemonRepoDB;
import models.Trainer;
import org.codehaus.jackson.map.ObjectMapper;
import web.ConnectionManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/pokemon")
public class PCController
{

	@GET
	@Path("/allpokemon")
	@Consumes("application/json")
	public Response getAllPokemon() throws IOException
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokeRepo.getAllPokemon());
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	@GET
	@Path("/trainerpokemon")
	@Consumes("application/json")
	public Response getTrainerPokemon() throws IOException
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		ObjectMapper mapper = new ObjectMapper();
		Trainer t = new Trainer("RED", 2);
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokeRepo.getTrainerPokemon(t));
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

}
