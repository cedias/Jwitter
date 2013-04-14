package servlets.friend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.FriendServices;
import servlets.GetParameters;


/**
 * list Friend servlet
 *  
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class ListFriendServlet extends HttpServlet {

	/**
	 * @throws userDoesntExist
	 * @throws wrongparameters
	 * @throws bdError
	 * @throws emptyResult
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String login = req.getParameter(GetParameters.login);
		String user_id = req.getParameter(GetParameters.user_id);
		String nbResults = req.getParameter(GetParameters.maxResults);
		String offset = req.getParameter(GetParameters.offset);
		
		JSONObject json = FriendServices.listFriends(user_id,login,nbResults, offset);
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
		
	}

	

}
