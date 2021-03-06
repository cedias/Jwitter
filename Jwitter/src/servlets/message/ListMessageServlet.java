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
 * list messages servlet
 *  
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class ListMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String id = req.getParameter(GetParameters.user_id);
			String username = req.getParameter(GetParameters.login);
			String nbMessage = req.getParameter(GetParameters.maxResults);
			String offset = req.getParameter(GetParameters.offset);
			String last = req.getParameter(GetParameters.message_id);
			

			JSONObject json = MessageServices.listMessages(id,username,nbMessage,offset,last);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}
	
	

}
