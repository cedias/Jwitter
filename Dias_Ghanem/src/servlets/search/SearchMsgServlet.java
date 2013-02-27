package servlets.search;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.SearchServices;
import servlets.GetParameters;

public class SearchMsgServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
			String key = req.getParameter(GetParameters.key);
			String query = req.getParameter(GetParameters.query);
			String friends = req.getParameter(GetParameters.restrict_to_friends);
			String nbMessage = req.getParameter(GetParameters.maxResults);
			String offset = req.getParameter(GetParameters.offset);
			
			JSONObject json = SearchServices.Search(key,query,friends,nbMessage,offset);
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}
	
	

}
