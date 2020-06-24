package servlets;

import controllers.LoginController;
import controllers.PCController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Router {
	public static void routeTo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println(req.getRequestURI());
		switch(req.getRequestURI()){
		case "/Project1/pokemon.get":
			PCController.optionSelect(req, resp);
			break;
		case "/Project1/login.add":
			LoginController.login(req, resp);
			break;
		default:
			req.getRequestDispatcher( "index.html").forward(req, resp);
		}
	}
}
