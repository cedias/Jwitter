package servlets.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

import services.AuthServices;


public class NewAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String login = req.getParameter("login");
			String password = req.getParameter("password");
			String nom = req.getParameter("nom");
			String prenom = req.getParameter("prenom");
			
			JSONObject json = AuthServices.newUser(login,password,nom,prenom);
			resp.setContentType("text/plain");
			resp.getWriter().println(json.toString());
	}
}
