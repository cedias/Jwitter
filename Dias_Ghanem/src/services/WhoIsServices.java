package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;
import bd.auth.AuthTools;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.friend.FriendTools;
import bd.message.MessageTools;

public class WhoIsServices {

	public static JSONObject whoIs(String id,String username) {
		if((username==null || username=="")){
			return ErrorMsg.wrongParameter();
		}		
		JSONObject json = new JSONObject();
		try {

			Integer exist = AuthTools.userExists(username);
			
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.id="+exist+";");
			res.next();
					
			int numFriends = FriendTools.numFriend(exist);
			
			json.put("id", exist);
			json.put("login",res.getString(2));
			json.put("First name", res.getString(3));
			json.put("Last name", res.getString(4));
			
			if(!(id==null || id=="")){
				res = stt.executeQuery("SELECT *  FROM `Friends` WHERE `id_from` =" + id + " AND `id_to` = "+ exist +";");
				if(res.next()){
					json.put("friends with", "yes");
				}else{
					json.put("friends with", "no");
				}
			}
			
			json.put("number of friends", numFriends);
			json.put("Last 5 msgs", MessageTools.listMessages(exist, 5, 0));
			
			
			
		} catch (SQLException e) {
			return ErrorMsg.bdError();
		} catch (userDoesntExistException e) {
			return ErrorMsg.userDoesntExist(username);
		} catch (JSONException e) {
			return ErrorMsg.bdError();
		} catch (emptyResultException e) {
			return ErrorMsg.bdError();
		}	
		return json;
	}
	
}
