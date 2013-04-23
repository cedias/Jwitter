package servlets.search;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.SearchServices;
import servlets.GetParameters;

public class SearchRSVServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
				
				String query = req.getParameter(GetParameters.query);

				
				JSONObject json = SearchServices.SearchRSV(query);
				
				resp.setContentType("text/plain");
				resp.getWriter().print(json);
		}
	
	

}
