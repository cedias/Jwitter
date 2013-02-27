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
	 * @author Charles-Emmanuel Dias
	 * @author Marwan Ghanem
	 * 
	 * Logout servlet
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * @throws wrongParameter
	 * @throws invalidKey
	 * @throws bdError
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String key = req.getParameter(GetParameters.key);
			JSONObject json = AuthServices.logout(key);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}

}
