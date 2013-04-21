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
import services.SearchServices;

public class MapReduceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JSONObject json;
		try {
			json = SearchTools.calculateDF();
		} catch (JSONException e) {
			json =  ErrorMsg.otherError("aint working");
		} catch (SQLException e) {
			json =  ErrorMsg.otherError(e.toString());
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().print(json);
	}

}
