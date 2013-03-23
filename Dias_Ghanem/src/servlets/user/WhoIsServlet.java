package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.UserServices;
import services.MessageServices;
import servlets.GetParameters;

public class WhoIsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter(GetParameters.user_id);
		String username = req.getParameter(GetParameters.login);
		
		JSONObject json = UserServices.whoIs(id,username);
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
	}

	
}
