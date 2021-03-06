package web;

import dao.IPokemon;
import dao.PokemonRepoDB;
import models.Pokemon;
import org.apache.log4j.Logger;
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
	private static final Logger log = Logger.getLogger(PCController.class);

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
		log.info("Pokemon Created:\n" + p.exportSmogon());
		ps.addPokemon(p);

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
			log.info("Pokemon withdrawn with ID=" + p_id);
			return Response.status(200).build();
		}

		return Response.status(204).build();
	}

	@PUT
	@Path("/{id}/deposit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response depositPokemon(int index, @PathParam("id") int id)
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		Pokemon p = ps.popPokemon(index);
		if(p != null)
		{
			pokeRepo.addPokemon(p);
			log.info("Pokemon deposited.");
			return Response.status(201).build();
		}
		return Response.status(204).build();
	}

	@DELETE
	@Path("/release")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response releasePokemon(int p_id)
	{
		IPokemon pokeRepo = new PokemonRepoDB(ConnectionManager.getConnection());
		pokeRepo.removePokemon(p_id);
		log.info("Pokemon with ID=" + p_id + " successfully removed.");
		return Response.status(200).build();
	}

}
