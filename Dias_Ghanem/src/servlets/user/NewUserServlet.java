package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

import services.UserServices;
import servlets.GetParameters;



/**
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 * 
 * new user servlet
 * 
 */
public class NewUserServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String login = req.getParameter(GetParameters.login);
			String password = req.getParameter(GetParameters.password);
			String nom = req.getParameter(GetParameters.lastName);
			String prenom = req.getParameter(GetParameters.firstname);
			
			JSONObject json = UserServices.newUser(login,password,nom,prenom);
			
			resp.setContentType("text/plain");
			resp.getWriter().println(json.toString());
	}
}
