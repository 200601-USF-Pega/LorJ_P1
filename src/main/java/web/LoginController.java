package web;

import com.sun.jersey.multipart.FormDataParam;
import dao.ILogin;
import dao.LoginDB;
import models.Trainer;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController
{

	private static final Logger log = Logger.getLogger(PCController.class);

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response login_form(@FormDataParam("username") String username, @FormDataParam("password") String password)
	{
		ILogin login = new LoginDB(ConnectionManager.getConnection());
		Trainer trainer = login.login(username, password);
		if(trainer != null)
		{
			log.info("Successful login (username=" + username + ", ot=" + trainer.getID() + ")");
			return Response.status(200).entity(trainer).type(MediaType.APPLICATION_JSON_TYPE).build();
		}
		log.info("Login attempt failed (username=" + username + ")");
		return Response.status(401).build();
	}

}
