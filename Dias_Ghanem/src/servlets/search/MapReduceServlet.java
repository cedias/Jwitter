package servlets.search;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bd.search.SearchTools;

import services.ErrorMsg;


public class MapReduceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JSONObject json;
		try {
			json = SearchTools.calculateTF();
		} catch (JSONException e) {
			json =  ErrorMsg.otherError("aint working");
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
	}

}
