package servlets.whois;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.MessageServices;
import services.WhoIsServices;
import servlets.GetParameters;

public class WhoIsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = req.getParameter(GetParameters.login);
		
		JSONObject json = WhoIsServices.whoIs(username);
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
	}

	
}
