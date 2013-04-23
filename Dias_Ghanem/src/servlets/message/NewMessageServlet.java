package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.MessageServices;
import servlets.GetParameters;

/**
 * Post message servlet
 *  
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class NewMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String key = req.getParameter(GetParameters.key);
			String message = req.getParameter(GetParameters.message);
			JSONObject json = MessageServices.newMessage(key, message);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}

	
}
