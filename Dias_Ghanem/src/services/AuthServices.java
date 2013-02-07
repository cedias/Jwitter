package services;

import org.json.JSONObject;

import bd.auth.AuthTools;

public class AuthServices {
	
	public static JSONObject newUser(String username,String password,String email){		
		
		try{
			
			if(username == null || password == null || email == null){
				return ErrorMsg.wrongParameter();
			}	
			
			if(AuthTools.userExists(username)){
				return ErrorMsg.userAlreadyExists(username);
			}else{
				
				if(AuthTools.addUser(username,password,email)){
					return JSONtools.ok();
				}else{
					return ErrorMsg.bdError();
				}
			}
		}
		catch(Exception e){
			return ErrorMsg.bdError();
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
