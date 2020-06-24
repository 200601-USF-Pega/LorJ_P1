package controllers;

import dao.ConnectionManager;
import dao.ILogin;
import dao.LoginDB;
import models.Trainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController
{
	private static ILogin repo = new LoginDB(ConnectionManager.getConnection());

	public static void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String n = req.getParameter("username");
		String p = req.getParameter("password");
		if(!n.isEmpty() && !p.isEmpty())
		{
			Trainer t = repo.login(n, p);
			if(t != null)
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("index.html");
				dispatcher.forward(req, resp);
			}
			else
			{
				out.print("Sorry, invalid username or password.");
				RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
				dispatcher.include(req, resp);
			}
		}
		else
		{
			out.print("Please input your username and password.");
			RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
			dispatcher.include(req, resp);
		}
	}
}
