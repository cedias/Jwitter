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
		try{
			if(userName == null || password == null){
				return ErrorMsg.wrongParameter();
			}
			if(!AuthTools.userExists(userName)){
				return ErrorMsg.userDoesntExist(userName);
			}
			if(!AuthTools.passwordValid(userName, password)){
				return ErrorMsg.wrongLogin();
			}else{
				return JSONtools.createKey();
			}			
		}catch(Exception e){
			return ErrorMsg.bdError();
		}
	}
	
	public static JSONObject logout(String key){
		try{
			if(key == null){
				return ErrorMsg.wrongParameter();
			}
			if(!AuthTools.keyUsed()){
				return ErrorMsg.invalidKey();
			}else{
				if(AuthTools.logout()){
					return JSONtools.ok();
				}else{
					return ErrorMsg.bdError();
				}
			}
		}catch(Exception e){
			return ErrorMsg.bdError();
		}
	}
	
	public static JSONObject deactivate(String userName,String password){
		try{
			if(userName == null || password == null){
				return ErrorMsg.wrongParameter();
			}
			if(!AuthTools.userExists(userName)){
				return ErrorMsg.userDoesntExist(userName);
			}
			if(!AuthTools.passwordValid(userName, password)){
				return ErrorMsg.wrongLogin();
			}else{
				if(AuthTools.deactivate()){
					return JSONtools.ok();
				}else{
					return ErrorMsg.bdError();
				}
			}
		}catch(Exception e){
			return ErrorMsg.bdError();
		}
	}
}
