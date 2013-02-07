package services;

import org.json.JSONObject;

import bd.Friend.FriendTools;
import bd.auth.AuthTools;

public class FriendServices {
	
	public static JSONObject addFriend(String key, String friend){

		if(!AuthTools.keyValid(key)){
			return ErrorMsg.invalidKey();
		}
	
		if(!AuthTools.userExists(friend))
		{
			return ErrorMsg.userDoesntExist(friend);
		}
		
		if(FriendTools.addFriend(key,friend))
		{
			 return JSONtools.ok();
		}
		else
		{
			return ErrorMsg.bdError();
		}
	}
	
	public static JSONObject removeFriend(String key, String friend){
		
		if(!AuthTools.keyValid(key)){
			return ErrorMsg.invalidKey();
		}
		
		
		if(FriendTools.removeFriend(key,friend))
		{
			return JSONtools.ok();
		}
		else
		{
			return ErrorMsg.bdError();
		}

	}
	
	public static JSONObject listFriends(String login, String nbResults ,String offset){
		
		try{
			int nb = Integer.parseInt(nbResults);
			int off = Integer.parseInt(offset);
		
			if(!AuthTools.userExists(login))
			{
				return ErrorMsg.userDoesntExist(login);
			}
			
			JSONObject json = FriendTools.listFriend(login,nb,off);
			
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
