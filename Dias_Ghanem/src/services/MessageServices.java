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
			
			if(key==null || message==null || message.isEmpty())
				return ErrorMsg.wrongParameter();
			
			int id = AuthTools.keyValid(key);
			String user = AuthTools.userExists(id);
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
		
			AuthTools.keyValid(key);
			return MessageTools.deleteMessage(messageId);
			}
		
		catch(KeyInvalidException e){
			return ErrorMsg.invalidKey();
		}
		
		catch (SQLException e) {
			return ErrorMsg.bdError();
		}
	}
	

	public static JSONObject listMessages(String id,String username, String nbMessage, String offset) {
		try{	
			if( (id==null && username==null) || nbMessage == null || offset == null)
				return ErrorMsg.wrongParameter();
			
			//default
			if(offset == null|| offset=="")
				offset = "0";
			if(nbMessage == null || nbMessage=="")
				nbMessage = "10";
		
			JSONObject json;
			int nb = Integer.parseInt(nbMessage);
			int off = Integer.parseInt(offset);
			if(id==null && username!=null){
				int user = AuthTools.userExists(username);
				json = MessageTools.listMessages(user,nb,off);				
			}else{
				json = MessageTools.listMessages(Integer.parseInt(id), nb, off);
			}
			
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
