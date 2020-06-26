package services;

import dao.ITrainer;
import dao.TrainerRepoDB;
import org.codehaus.jackson.map.ObjectMapper;
import web.ConnectionManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/trainer")
public class TrainerController
{

	@GET
	@Path("/alltrainers")
	@Consumes("application/json")
	public Response getAllPeople() throws IOException {
		ITrainer trainerRepo = new TrainerRepoDB(ConnectionManager.getConnection());
		ObjectMapper mapper = new ObjectMapper();
		String response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(trainerRepo.getAllTrainers());
		return Response
				.status(Response.Status.OK)
				.entity(response)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.build();
	}

}