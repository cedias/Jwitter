package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.exceptions.KeyInvalidException;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.friend.FriendTools;
import bd.auth.AuthTools;

public class FriendServices {
	
	public static JSONObject addFriend(String key, String addFriend){
		try {
			
			int friend = Integer.parseInt(addFriend);
			int user = AuthTools.keyValid(key);
			
			if(friend == user)
				return ErrorMsg.wrongParameter();
			
			FriendTools.addFriend(user,friend);
			return JSONtools.ok();
			
			
			} catch (SQLException e) {
				return ErrorMsg.bdError();
				
			} catch (KeyInvalidException e) {
				return ErrorMsg.invalidKey();
			
			} catch (NumberFormatException e) {
				return ErrorMsg.wrongParameter();
			}
		
	}
	
	public static JSONObject removeFriend(String key, String friend){
		try {
			
			int user = AuthTools.keyValid(key);
			int friendId = AuthTools.userExists(friend);
		
			FriendTools.removeFriend(user,friendId);
			return JSONtools.ok();
			
		
		} catch (KeyInvalidException e) {
			return ErrorMsg.invalidKey();
			
		} catch (SQLException e) {
			return ErrorMsg.bdError();
			
		} catch (userDoesntExistException e) {
			return ErrorMsg.userDoesntExist(friend);
		}

	}
	
	public static JSONObject listFriends(String login, String nbResults ,String offset){
		try{
			
			int nb = Integer.parseInt(nbResults);
			int off = Integer.parseInt(offset);
			int user = AuthTools.userExists(login);

			return  FriendTools.listFriend(user,nb,off);
			
		} catch(NumberFormatException E) {
			return ErrorMsg.wrongParameter();
			
		} catch (SQLException e) {
			return ErrorMsg.bdError();
			
		} catch (userDoesntExistException e) {
			return ErrorMsg.userDoesntExist(login);
			
		} catch (emptyResultException e) {
			return ErrorMsg.emptyResult();
		}
	}
	
	

}
