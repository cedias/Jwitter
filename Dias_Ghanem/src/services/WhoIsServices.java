package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.auth.AuthTools;
import bd.exceptions.userDoesntExistException;

public class WhoIsServices {

	public static JSONObject whoIs(String username) {
		if((username==null || username=="")){
			return ErrorMsg.wrongParameter();
		}		
		try {
			Integer Exist = AuthTools.userExists(username);
			
		} catch (SQLException e) {
			return ErrorMsg.bdError();
		} catch (userDoesntExistException e) {
			return ErrorMsg.userDoesntExist(username);
		}		
		return null;
	}
}
