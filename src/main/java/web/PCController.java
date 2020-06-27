package web;

import dao.IPokemon;
import dao.PokemonRepoDB;
import models.Pokemon;
import org.codehaus.jackson.map.ObjectMapper;
import services.PokemonService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/pokemon")
public class PCController
{
	private static PokemonService ps = new PokemonService();

	@GET
	@Path("/allpokemon")
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
	@Path("/getparty")
	public Response getParty() throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ps.getLocalTeam());
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	@GET
	@Path("/{id}/trainerpokemon")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrainerPokemon(@PathParam("id") int id) throws IOException
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pokeRepo.getTrainerPokemon(id));
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	@POST
	@Path("/newpokemon")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newPokemon(Pokemon p)
	{

		System.out.println(p.exportSmogon());
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		pokeRepo.addPokemon(p);

		return Response.status(201).build();
	}

	@DELETE
	@Path("/withdraw")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response withdrawPokemon(int p_id)
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		Pokemon p = pokeRepo.withdrawPokemon(p_id);
		if(p != null)
		{
			ps.addPokemon(p);
			return Response.status(200).build();
		}

		return Response.status(204).build();
	}

	@POST
	@Path("/deposit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response depositPokemon(int index)
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		pokeRepo.addPokemon(ps.popPokemon(index));
		return Response.status(201).build();
	}

	@DELETE
	@Path("/release")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response releasePokemon(int p_id)
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		pokeRepo.removePokemon(p_id);
		return Response.status(200).build();
	}

}
