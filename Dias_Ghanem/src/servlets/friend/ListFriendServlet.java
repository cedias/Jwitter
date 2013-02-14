package servlets.friend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.FriendServices;

public class ListFriendServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String login = req.getParameter("user");
		String nbResults = req.getParameter("nb");
		String offset = req.getParameter("off");
		
		JSONObject json = FriendServices.listFriends(login,nbResults, offset);
		
		resp.getWriter().print(json);
		
	}

	

}
