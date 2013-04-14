package services;


import java.sql.SQLException;
import org.json.JSONObject;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.userDoesntExistException;
import bd.exceptions.wrongPasswordException;
import bd.user.UserTools;

/**
 * User API static methods
 * 
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class UserServices {
	
	/**
	 * Create a new user
	 * @param login
	 * @param password
	 * @param nom
	 * @param prenom
	 * @return status message
	 */
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

	
	/**
	 * log a user in
	 * @param username
	 * @param password
	 * @return JSON(id,login,key)
	 */
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
	
	/**
	 * log a user out
	 * @param key
	 * @return status message
	 */
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
	
	/**
	 * get a user info
	 * @param id user id to get info from
	 * @param login user login to get info from
	 * @param key who is asking
	 * @return JSON(resp)
	 */
	public static JSONObject info(String id,String login, String key) {
		try {
			
		
		if((login==null || login=="") && (id==null || id=="")){
			return ErrorMsg.wrongParameter();
		}
		
		int loggedUser = -1;
		int idInfo = -1;
		
		if(id != null && id != ""){
			idInfo = Integer.parseInt(id);
			UserTools.userExists(idInfo);
		}
		else{
			idInfo = UserTools.userExists(login);
		}
		
		if(key != null && key != "")
			loggedUser = UserTools.keyValid(key);
		
		
		return UserTools.info(idInfo, loggedUser);
		
		
		} catch (KeyInvalidException e) {
			return ErrorMsg.invalidKey();
		} catch (SQLException e) {
			return ErrorMsg.bdError();
		} catch (userDoesntExistException e) {
			if(login == null)
				return ErrorMsg.userDoesntExist(id);
			else			
				return ErrorMsg.userDoesntExist(login);
		}
	}
}
