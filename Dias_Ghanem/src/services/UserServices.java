package services;


import java.sql.SQLException;
import org.json.JSONObject;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.userDoesntExistException;
import bd.exceptions.wrongPasswordException;
import bd.user.UserTools;

public class UserServices {
	
	public static JSONObject newUser(String login ,String password,String nom,String prenom){		
		try{
			
			if(login == null || login =="" || password == null || password == "" || nom == null || nom =="" || prenom == null || prenom == "")
				return ErrorMsg.wrongParameter();
			
			Integer test = UserTools.userExists(login);
			if ( test != null){
				return ErrorMsg.userAlreadyExists(login);
			}
			
		}
		catch(userDoesntExistException e){		
			if(UserTools.addUser(login,password,nom,prenom))
				return JSONtools.ok();
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

			int userid = UserTools.userExists(username);			
			return UserTools.login(userid,password);
			
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
			
			UserTools.logout(key);
			return JSONtools.ok();
		
		}
		catch(KeyInvalidException e){
			return ErrorMsg.invalidKey();
		}	
		catch(Exception e){
			return ErrorMsg.bdError();
		}
	}
	
	public static JSONObject info(String id,String username) {
		if((username==null || username=="")){
			return ErrorMsg.wrongParameter();
		}		
		return UserTools.info(id, username);
	}
}
