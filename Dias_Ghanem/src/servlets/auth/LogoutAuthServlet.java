package servlets.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.AuthServices;
import servlets.GetParameters;

public class LogoutAuthServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String key = req.getParameter(GetParameters.key);
			JSONObject json = AuthServices.logout(key);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}

}
