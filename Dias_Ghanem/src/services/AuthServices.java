package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.auth.AuthTools;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.userDoesntExistException;
import bd.exceptions.wrongPasswordException;

public class AuthServices {
	
	public static JSONObject newUser(String login ,String password,String nom,String prenom){		
		try{
			
			if(login == null || password == null || nom == null || prenom == null)
				return ErrorMsg.wrongParameter();
				
			AuthTools.userExists(login); 
			
		}
		catch(userDoesntExistException e){
			if(AuthTools.addUser(login,password,nom,prenom))
				return JSONtools.ok();
			
			return ErrorMsg.bdError();			
		}		
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}		
		return ErrorMsg.bdError();
	}

	
	public static JSONObject login(String username,String password){
		try{		
			if(username == null || password == null)
				return ErrorMsg.wrongParameter();

			int userid = AuthTools.userExists(username);			
			return AuthTools.login(userid,password);
			
		}
		catch(userDoesntExistException e){
			return ErrorMsg.wrongLogin();
		}
		catch (wrongPasswordException e) {
			return ErrorMsg.wrongLogin();
		}		
		catch(SQLException e){
			return ErrorMsg.bdError();	
		}
	}
	
	public static JSONObject logout(String key){
		try{
			if(key == null)
				return ErrorMsg.wrongParameter();
			
			AuthTools.logout(key);
			return JSONtools.ok();
		
		}
		catch(KeyInvalidException e){
			return ErrorMsg.invalidKey();
		}	
		catch(Exception e){
			return ErrorMsg.bdError();
		}
	}
}
