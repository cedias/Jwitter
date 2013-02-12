package services;

import org.json.JSONObject;

import bd.auth.AuthTools;
import bd.message.MessageTools;

public class MessageServices {

	public static JSONObject newMessage(String key,String message){
		if(key==null || message==null){
			return ErrorMsg.wrongParameter();
		}
		if(!AuthTools.keyValid(key)){
			return ErrorMsg.invalidKey();
		}else{
			if(MessageTools.postMessage(key, message)){
				return JSONtools.ok();
			}else{
				return ErrorMsg.bdError();
			}
		}
	}

	public static JSONObject deleteMessage(String key, String messageId) {
		if( key==null || messageId==null){
			return ErrorMsg.wrongParameter();
		}
		if(!AuthTools.keyValid(key)){
			return ErrorMsg.invalidKey();
		}
		if(!MessageTools.idValid(messageId)){
			return ErrorMsg.invalidMessageId();
		}
		if(!MessageTools.deleteMessage(key,messageId)){
			return ErrorMsg.bdError();
		}else{
			return JSONtools.ok();
		}
	}

	public static JSONObject listMessages(String username,
			String nbMessage, String offset) {
			if( username==null || nbMessage == null || offset == null){
			return ErrorMsg.wrongParameter();
			}
		try{
			JSONObject json;
			int nb = Integer.parseInt(nbMessage);
			int off = Integer.parseInt(offset);
			if(!AuthTools.userExists(username))
			{
				return ErrorMsg.userDoesntExist(username);
			}
			json = MessageTools.listMessages(username,nb,off);			
			if(json == null){
				return ErrorMsg.emptyResult();
			}
			return json;			
		}catch(NumberFormatException E){
			return ErrorMsg.wrongParameter();
		}
	}
}
