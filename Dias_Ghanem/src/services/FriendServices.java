package services;

import org.json.JSONObject;

import bd.Friend.FriendTools;
import bd.auth.AuthTools;

public class FriendServices {
	
	public static JSONObject addFriend(String key, String friend){
		JSONObject json;
		
		
		if(!AuthTools.keyValid(key)){
			JSONtools.error("Your Key is invalid", 403);
		}
		
		if(!AuthTools.userExists(friend))
		{
			JSONtools.error("User "+friend+" doesn't exists", 20);
		}
		
		if(FriendTools.addFriend(key,friend))
		{
			 json = JSONtools.ok();
		}
		else
		{
			json = JSONtools.error("BD_ERROR", 900);
		}
		
		return json;
	}
	
	public static JSONObject removeFriend(String key, String friend){
		JSONObject json;
		
		
		if(!AuthTools.keyValid(key)){
			JSONtools.error("Your Key is invalid", 403);
		}
		
		if(!AuthTools.userExists(friend))
		{
			JSONtools.error("User "+friend+" doesn't exists", 20);
		}
		
		if(FriendTools.removeFriend(key,friend))
		{
			 json = JSONtools.ok();
		}
		else
		{
			json = JSONtools.error("BD_ERROR", 900);
		}
		
		return json;
	}
	
	public static JSONObject listFriends(String login, String nbResults ,String offset){
		
		try{
			int nb = Integer.parseInt(nbResults);
			int off = Integer.parseInt(offset);
		
			if(!AuthTools.userExists(login))
			{
				return JSONtools.error("User "+login+" doesn't exists", 20);
			}
			
			return FriendTools.listFriend(login,nb,off);
			
		}
		
		catch(NumberFormatException E)
		{
		
			return JSONtools.error("Wrong Parameters", 100);
		}
	}
	
	

}
