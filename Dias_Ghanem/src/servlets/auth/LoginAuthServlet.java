package servlets.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.AuthServices;

public class LoginAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			JSONObject json = AuthServices.login(username, password);			
			
			resp.getWriter().print(json);
	}
	

}