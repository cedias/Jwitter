package servlets.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.MessageServices;


public class DeleteMessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			String key = req.getParameter("key");
			String messageId= req.getParameter("messageId");
			JSONObject json = MessageServices.deleteMessage(key,messageId);
			resp.getWriter().print(json);
	}

	
}
