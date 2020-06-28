package web;

import com.sun.jersey.multipart.FormDataParam;
import dao.ITrainer;
import dao.TrainerRepoDB;
import models.Trainer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/trainer")
public class TrainerController
{

	private static final Logger log = Logger.getLogger(PCController.class);

	@GET
	@Path("/alltrainers")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllPeople() throws IOException
	{
		ITrainer trainerRepo = new TrainerRepoDB(ConnectionManager.getConnection());
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(trainerRepo.getAllTrainers());
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

	@POST
	@Path("/addtrainer")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addTrainer(@FormDataParam("username") String username, @FormDataParam("password") String password)
	{
		ITrainer tRepo = new TrainerRepoDB(ConnectionManager.getConnection());
		tRepo.addTrainer(username, password);
		log.info("Profile created for " + username);

		return Response.status(201).build();
	}

}