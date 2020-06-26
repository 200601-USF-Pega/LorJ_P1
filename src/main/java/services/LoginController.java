package services;

import com.sun.jersey.multipart.FormDataParam;
import dao.ILogin;
import dao.LoginDB;
import models.Trainer;
import web.ConnectionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController
{

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response login_form(@FormDataParam("username") String username, @FormDataParam("password") String password)
	{
		ILogin login = new LoginDB(ConnectionManager.getConnection());
		Trainer trainer = login.login(username, password);
		System.out.println("login");
		if(trainer != null)
		{
			return Response.status(200).build();
		}
		return Response.status(401).build();
	}

}
