package bd.friend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;


import bd.Database;

public class FriendTools {

	public static boolean addFriend(int userId, int friendId) throws SQLException{
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		
		int rows = stt.executeUpdate("INSERT INTO `dias_ghanem`.`friends` (`id_from`, `id_to`, `time`) VALUES ('"+userId+"', '"+friendId+"', CURTIME());");
		
		return (rows==1)?true:false;
		
	}

	public static boolean removeFriend(int user, String friend) {
		return true;
	}

	public static JSONObject listFriend(int user, int nbResults, int offset) {
		return null;
	}
	
	
}
