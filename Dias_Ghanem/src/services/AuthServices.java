package services;

import org.json.JSONObject;

import bd.auth.AuthTools;

public class AuthServices {
	
	public static JSONObject newUser(String login ,String password,String nom,String prenom){		
		
		try{
			
			if(login == null || password == null || nom == null || prenom == null){
				return ErrorMsg.wrongParameter();
			}	
			
			if(AuthTools.userExists(login)){
				return ErrorMsg.userAlreadyExists(login);
			}else{
				
				if(AuthTools.addUser(login,password,nom,prenom)){
					return JSONtools.ok();
				}else{
					return ErrorMsg.bdError();
				}
			}
		}
		catch(Exception e){
			return ErrorMsg.otherError(e.getMessage());
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
				return AuthTools.createKey(userName);
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
