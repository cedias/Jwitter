package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.MessageServices;

public class ListMessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String qes = req.getParameter("qes");
			String username = req.getParameter("username");
			String nbMessage = req.getParameter("nbMessage");
			String offset = req.getParameter("offset");
			String word = req.getParameter("word");
			
			JSONObject json = MessageServices.listMessages(qes,username,nbMessage,offset,word);
			resp.getWriter().print(json);
	}
	
	

}
