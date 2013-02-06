package services;

import org.json.JSONObject;

import bd.auth.AuthTools;

public class AuthServices {
	
	public static JSONObject newUser(String username,String password,String email){		
		
		try{
			
			if(username == null || password == null || email == null){
				return JSONtools.error(username + "  " + password + "  " + email, 84);
			}	
			
			if(AuthTools.userExists(username)){
				return JSONtools.error("Already exists", 3);
			}else{
				
				if(AuthTools.addUser(username,password,email)){
					return JSONtools.ok();
				}else{
					return JSONtools.error("Error general", 999);
				}
			}
		}
		catch(Exception e){
				return JSONtools.error("Error general", 999);
		}
	}
	
	public static JSONObject login(String userName,String password){
		return null;
	}
	
	public static JSONObject logout(String userName){
		return null;
	}
	
	public static JSONObject deactivate(String userName,String password){
		return null;
	}
}
