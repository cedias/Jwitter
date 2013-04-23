package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.exceptions.KeyInvalidException;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.friend.FriendTools;
import bd.user.UserTools;

/**
 * Friend API static methods
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 */
public class FriendServices {
	
	/**
	 * Add a friend to the key related user
	 * @param key auth key
	 * @param addFriend id of the friend you want to add
	 * @return JSONObject: ok if everything want well
	 * 
	 */
	public static JSONObject addFriend(String key, String addFriend){
		try {
			
			int friend = Integer.parseInt(addFriend);
			int user = UserTools.keyValid(key);
			
			if(friend == user)
				return ErrorMsg.otherError("You are already friend with yourself, aren't you ?");
			
			FriendTools.addFriend(user,friend);
			return JSONtools.ok();
			
		} catch (NumberFormatException e) {
			return ErrorMsg.wrongParameter();	
			
		} catch (KeyInvalidException e) {
			return ErrorMsg.invalidKey();
			
		}  catch (SQLException e) {
		
			if(e.getErrorCode() == 1062)//Duplicate entry Key
				return ErrorMsg.otherError("You are already friends :)");
			
			if(e.getErrorCode() == 1452)//Key doesn't match FK Constraint
				return ErrorMsg.userDoesntExist(addFriend);
			
			return ErrorMsg.bdError();
			
		} catch (userDoesntExistException e) {
			return ErrorMsg.bdError();
		}
	}
	
	/**
	 * remove a friend to the key related user
	 * @param key auth key
	 * @param remFriend friend id to remove
	 * @return ok if everything went well
	 */
	public static JSONObject removeFriend(String key, String remFriend){
		try {
			
			int friend = Integer.parseInt(remFriend);
			int user = UserTools.keyValid(key);
			
			if(friend == user)
				return ErrorMsg.otherError("You can't unfriend yourself !");
			
			FriendTools.removeFriend(user,friend);
			return JSONtools.ok();
			
		} catch (NumberFormatException e) {
			return ErrorMsg.wrongParameter();	
			
		} catch (KeyInvalidException e) {
			return ErrorMsg.invalidKey();
			
		}  catch (SQLException e) {
			
			return ErrorMsg.bdError();
			
		} catch (userDoesntExistException e) {
			return ErrorMsg.bdError();
		}

	}
	

	/**
	 *  List friends by login (string)
	 * @param login String user login
	 * @param uid String userid
	 * @param nbResults max number of results to return
	 * @param offset
	 * @return JSONObject => [messages...]
	 */
	public static JSONObject listFriends(String uid, String login, String nbResults ,String offset){

		try{
			if((login == null || login == "") && (uid == null || uid == ""))
				return ErrorMsg.wrongParameter();
			
			//default
			if(offset == null|| offset=="")
				offset = "0";
			if(nbResults == null || nbResults=="")
				nbResults = "10";
			
			int nb = Integer.parseInt(nbResults);
			int off = Integer.parseInt(offset);
			int user;
			
			if(uid == "" || uid == null){
				user = UserTools.userExists(login);
			
			} else {
				user = Integer.parseInt(uid);
				login = UserTools.userExists(user);
			}
			

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
