package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.auth.AuthTools;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.message.MessageTools;

public class MessageServices {

	public static JSONObject newMessage(String key,String message){
		try {
			
			if(key==null || message==null)
				return ErrorMsg.wrongParameter();
			
			int user = AuthTools.keyValid(key);
			MessageTools.postMessage(user, message);
			return JSONtools.ok();
			
		} 
		
		catch (KeyInvalidException e) {
				return ErrorMsg.invalidKey();
		} 
			
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}		
	}

	public static JSONObject deleteMessage(String key, String messageId) {
		try{	
			if(key==null || messageId==null)
				return ErrorMsg.wrongParameter();
		
			AuthTools.keyValid(key);
			MessageTools.deleteMessage(messageId);
			return JSONtools.ok();
			
			}
		
		catch(KeyInvalidException e){
			return ErrorMsg.invalidKey();
		}
		
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}
	}
	

	public static JSONObject listMessages(String username, String nbMessage, String offset) {
		try{	
			if( username==null || nbMessage == null || offset == null)
				return ErrorMsg.wrongParameter();
			
			JSONObject json;
			int nb = Integer.parseInt(nbMessage);
			int off = Integer.parseInt(offset);
			int user = AuthTools.userExists(username);
			
			json = MessageTools.listMessages(user,nb,off);
			
			return json;
			
		}
		catch(NumberFormatException E){
			return ErrorMsg.wrongParameter();
		}
		
		catch (userDoesntExistException e) {
			return ErrorMsg.userDoesntExist(username);
		}
		
		catch (emptyResultException e) {
			return ErrorMsg.emptyResult();
		}
		
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}
		
		
	}
}
