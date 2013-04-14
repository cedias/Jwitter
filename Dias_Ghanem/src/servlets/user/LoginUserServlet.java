package servlets.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.UserServices;
import servlets.GetParameters;

public class LoginUserServlet extends HttpServlet {

	/**
	 * @author Charles-Emmanuel Dias
	 * @author Marwan Ghanem
	 * 
	 * Login servlet
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws wrongParameter
	 * @throws wrongLogin
	 * @throws bdError
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
			String username = req.getParameter(GetParameters.login);
			String password = req.getParameter(GetParameters.password);
			
			JSONObject json = UserServices.login(username, password);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}
	

}
