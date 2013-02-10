package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.MessageServices;

public class SearchWordMessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String word = req.getParameter("word");
		String nbMessage = req.getParameter("nbMessage");
		String offset = req.getParameter("offset");
		
		JSONObject json = MessageServices.listMessagesWord(word,nbMessage,offset);
		resp.getWriter().print(json);
	}
	
	

}
