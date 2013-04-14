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
 * add Friend servlet
 *  
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class AddFriendServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String key = req.getParameter(GetParameters.key);
		String friend = req.getParameter(GetParameters.friend_id);
		
		JSONObject json = FriendServices.addFriend(key, friend);
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
		
		
	}

}
