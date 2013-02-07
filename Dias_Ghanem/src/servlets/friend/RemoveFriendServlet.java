package servlets.friend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.FriendServices;

public class RemoveFriendServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String key = req.getParameter("key");
		String friend = req.getParameter("friend");
		
		JSONObject json = FriendServices.removeFriend(key, friend);
		
		resp.getWriter().print(json);
		
	}
	

}
