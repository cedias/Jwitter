package bd.friend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONException;
import org.json.JSONObject;
import services.ErrorMsg;
import bd.Database;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;

/**
 * static methods for friend operations in the databases
 * 
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *

 */
public class FriendTools {

	/**
	 * Add Friend
	 * 
	 * @param userId the id to which add a friend
	 * @param friendId the id who is being added as a friend 
	 * @throws SQLException
	 * @throws userDoesntExistException
	 * @return ok message
	 */
	public static void addFriend(int userId, int friendId) throws SQLException, userDoesntExistException {
		String sql= "INSERT INTO `dias_ghanem`.`Friends` (`id_from`, `id_to`, `time`) VALUES ('"+userId+"', '"+friendId+"', TIMESTAMP(CURRENT_TIMESTAMP) );";
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
	    stt.executeUpdate(sql);
	    incrementFriendCount(userId);
	    stt.close();
	    c.close();
	}

	/**
	 * Remove Friend
	 * 
	 * @param user the id to which remove a friend
	 * @param friend the friend to remove
	 * @throws SQLException
	 * @throws userDoesntExistException
	 * @return ok message
	 */
	public static void removeFriend(int user, int friend) throws  SQLException, userDoesntExistException {
		String sql = "DELETE FROM `dias_ghanem`.`Friends` WHERE `Friends`.`id_from` = "+user+" AND `Friends`.`id_to` = "+friend+";";
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		int rc = stt.executeUpdate(sql);
		
		if(rc != 0)
			decrementFriendCount(user);
		
		stt.close();
	    c.close();
		
	}
	
	/**
	 * list friend
	 * 
	 * @param user
	 * @param nbResults
	 * @param offset
	 * @throws SQLException
	 * @throws emptyResultException
	 * @return json list of user friends
	 */
	public static JSONObject listFriend(int user, int nbResults, int offset) throws SQLException, emptyResultException {
		String sql = "SELECT f.id_to, u.login, f.time  FROM Friends f, User u WHERE id_from = "+user+" AND f.id_to = u.id LIMIT "+offset+" , "+nbResults;
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		ResultSet res = stt.executeQuery(sql);
		
		if(!res.next())
			throw new emptyResultException();
		
		JSONObject json = new JSONObject();
		res.beforeFirst();
		
		try {
			
			while(res.next()) {
				JSONObject oneUser = new JSONObject();
				oneUser.put("id", res.getInt(1));
				oneUser.put("login", res.getString(2));
				oneUser.put("date", res.getTimestamp(3));
				
				json.accumulate("friends", oneUser);
			}
			
			res.close();
			stt.close();
			c.close();
			return json;
		
		}
		
		catch (JSONException e) {
			return ErrorMsg.bdError();
		}
	}
	
	/**
	 * Count number of friends (Deprecated - to be remove)
	 * 
	 * @param user id 
	 * @return int number of friends
	 * @throws SQLException
	 */
	@Deprecated
	public static int numFriend(int user) throws SQLException{
		String sql = "SELECT COUNT(*) FROM `Friends` WHERE `id_from` =" + user;
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		ResultSet res = stt.executeQuery(sql);
		
		if(!res.next())
			return -1;
		res.beforeFirst();
		res.next();
		int cpt = res.getInt(1);
		res.close();
		stt.close();
		c.close();
		return cpt;
	}
	
	/**
	 * Friend Count
	 * @param id
	 * @return number of friends user (id) has
	 * @throws SQLException
	 * @throws userDoesntExistException
	 */
	public static int friendCound(int id) throws SQLException, userDoesntExistException {
		String sql = "SELECT friendCount FROM  `User` WHERE  `id` ="+id;
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		ResultSet res = stt.executeQuery(sql);
		
		if(!res.next())
			throw new userDoesntExistException();
		
		int count = res.getInt(1);
		res.close();
		stt.close();
		c.close();
		
		return count;
	}
	
	/**
	 * Increment Friend Count
	 * @param id
	 * @throws SQLException
	 * @throws userDoesntExistException
	 */
	public static void incrementFriendCount(int id) throws SQLException, userDoesntExistException {
		String sql = "UPDATE User SET friendCount=friendCount+1 WHERE id="+id;
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		stt.executeUpdate(sql);
		stt.close();
		c.close();
	}
	
	/**
	 * Decrement Friend Count
	 * @param id
	 * @throws SQLException
	 * @throws userDoesntExistException
	 */
	public static void decrementFriendCount(int id) throws SQLException, userDoesntExistException {
		String sql = "UPDATE User SET friendCount=friendCount-1 WHERE id="+id;
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		stt.executeUpdate(sql);
		stt.close();
		c.close();
		
	}
}
