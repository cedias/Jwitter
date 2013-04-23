package servlets.search;

import java.io.IOException;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bd.search.SearchTools;

import services.ErrorMsg;
import services.JSONtools;


public class MapReduceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JSONObject json = null;
		try {
			SearchTools.calculateDF();
			SearchTools.calculateTF();
			json = JSONtools.ok();
		} catch (JSONException e) {
			json =  ErrorMsg.otherError("aint working");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
	}

}
