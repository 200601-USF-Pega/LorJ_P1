package services;

import dao.ILogin;
import dao.IPokemon;
import dao.LoginDB;
import dao.PokemonRepoDB;
import models.Trainer;
import org.codehaus.jackson.map.ObjectMapper;
import web.ConnectionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/login")
public class LoginController
{

	@POST
	public Response login_form(@FormParam("username") String username, @FormParam("password") String password) throws IOException
	{
		ILogin login = new LoginDB(ConnectionManager.getConnection());
		Trainer trainer = login.login(username, password);
		if(trainer != null)
		{
			ObjectMapper mapper = new ObjectMapper();
			String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(trainer);
			return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
		}
		return Response.status(401).build();
	}

}
