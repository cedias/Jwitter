package services;

import org.json.JSONObject;

import bd.Friend.FriendTools;
import bd.Message.MessageTools;
import bd.auth.AuthTools;

public class MessageServices {

	public static JSONObject newMessage(String key,String message){
		if(key==null || message==null){
			return ErrorMsg.wrongParameter();
		}
		if(!AuthTools.keyValid(key)){
			return ErrorMsg.invalidKey();
		}else{
			if(MessageTools.postMessage(key, message)){
				return JSONtools.MessageId(message);
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
			return ErrorMsg.invalidId();
		}
		if(!MessageTools.deleteMessage(key,messageId)){
			return ErrorMsg.bdError();
		}else{
			return JSONtools.ok();
		}
	}

	public static JSONObject listMessages(String qes, String username,
			String nbMessage, String offset, String word) {
			if( qes==null || nbMessage == null || offset == null){
			return ErrorMsg.wrongParameter();
			}
			if(username == null && word == null){
				return ErrorMsg.wrongParameter();
			}
		try{
			JSONObject json;
			int nb = Integer.parseInt(nbMessage);
			int off = Integer.parseInt(offset);
		    if(qes=="user"){
			if(!AuthTools.userExists(username))
			{
				return ErrorMsg.userDoesntExist(username);
			}
				json = MessageTools.listMessages(username,nb,off);			
		    }else{
		    	json = MessageTools.listMessagesWord(word, nb, off);
		    }
			if(json == null){
				return ErrorMsg.emptyResult();
			}
			return json;
			
		}catch(NumberFormatException E){
			return ErrorMsg.wrongParameter();
		}
	}

	public static JSONObject listMessagesWord(String word, String nbMessage,
			String offset) {
		try{
			int nb = Integer.parseInt(nbMessage);
			int off = Integer.parseInt(offset);
			
			JSONObject json = MessageTools.listMessagesWord(word,nb,off);
			
			if(json == null){
				return ErrorMsg.emptyResult();
			}

			return json;			
		}		
		catch(NumberFormatException E)
		{
		
			return ErrorMsg.wrongParameter();
		}
	}

}
