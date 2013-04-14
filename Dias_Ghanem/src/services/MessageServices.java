package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.exceptions.KeyInvalidException;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.message.MessageTools;
import bd.user.UserTools;

public class MessageServices {

	public static JSONObject newMessage(String key,String message){
		try {
			
			if(key==null || message==null || message.isEmpty())
				return ErrorMsg.wrongParameter();
			
			int id = UserTools.keyValid(key);
			String user = UserTools.userExists(id);
			return 	MessageTools.postMessage(id,user, message);
				
		} 
		
		catch (KeyInvalidException e) {
				return ErrorMsg.invalidKey();
		} 
			
		catch (SQLException e) {
			return ErrorMsg.bdError();
		} catch (userDoesntExistException e) {
			return ErrorMsg.bdError();
		}		
	}

	public static JSONObject deleteMessage(String key, String messageId) {
		try{	
			if(key==null || messageId==null)
				return ErrorMsg.wrongParameter();
		
			UserTools.keyValid(key);
			return MessageTools.deleteMessage(messageId);
			}
		
		catch(KeyInvalidException e){
			return ErrorMsg.invalidKey();
		}
		
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}
	}
	

	public static JSONObject listMessages(String id,String username, String nbMessage, String offset, String last) {
		try{	
			
			//default
			int off = 0;
			int nb = 100;
			int user = -1;
				
			if(offset != null && offset != "")
				off = Integer.parseInt(offset);
			
			if(nbMessage != null && nbMessage!="")
				nb = Integer.parseInt(nbMessage);
	
			
			if(id != null && id != ""){
				user = Integer.parseInt(id);
				UserTools.userExists(user);
			}
				
			if(username != null && username != "" && user == -10)
				user = UserTools.userExists(username);
				
			return MessageTools.listMessages(user,nb,off,last);				
			
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
