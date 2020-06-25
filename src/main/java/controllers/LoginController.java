package controllers;

import dao.ILogin;
import dao.LoginDB;
import models.Trainer;
import web.ConnectionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/login")
public class LoginController
{

	@POST
	@Path("/form")
	public Response login_form(@FormParam("username") String username, @FormParam("password") String password) throws IOException
	{
		ILogin login = new LoginDB(ConnectionManager.getConnection());
		Trainer trainer = login.login(username, password);
		if(trainer != null)
		{
			return Response.status(201).build();
		}
		return Response.status(401).build();
	}

}
