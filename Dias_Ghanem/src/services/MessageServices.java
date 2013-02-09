package services;

import org.json.JSONObject;

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
}
