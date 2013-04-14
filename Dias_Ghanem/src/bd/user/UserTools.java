package bd.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import bd.Database;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.emptyResultException;
import bd.exceptions.userDoesntExistException;
import bd.exceptions.wrongPasswordException;
import bd.friend.FriendTools;
import bd.message.MessageTools;
import services.ErrorMsg;


public class UserTools {
	
	public static int userExists(String login) throws SQLException, userDoesntExistException{
		try {
			int id;
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.login='"+login+"';");
		
			if(res.next()==true){
				id = res.getInt(1);
			}	
			else
			{
				res.close();
				stt.close();
				c.close();				
				throw new userDoesntExistException();
			}
			
			res.close();
			stt.close();
			c.close();
			return id;		
			
		}catch (SQLException e) {			
			throw new SQLException(e.getMessage());
		}
	}
	
	public static String userExists(int id) throws SQLException, userDoesntExistException {
		try {
			String username;
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.id='"+id+"';");
		
			if(res.next()==true){
				username = res.getString(2);
			}
			else
			{
				res.close();
				stt.close();
				c.close();			
				throw new userDoesntExistException();
			}
			
			res.close();
			stt.close();
			c.close();		
			return username;
					
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public static boolean addUser(String login, String password, String nom , String prenom){
		try{
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			String query = "INSERT INTO User(login,password,nom,prenom) VALUES ('"+login+"','"+password+"','"+nom+"','"+prenom+"');";
			
			int res = stt.executeUpdate(query);
			stt.close();
			c.close();
			
			return (res == 0)?false:true;	
			
		}catch (SQLException e) {
			return false;
		}
	}
	
	public static JSONObject login(int id, String password)throws wrongPasswordException {
		try {
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.id="+id+";");
			res.next();
			
			if(password.contentEquals(res.getString(5))){
				String key = getKey(id);
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("login",res.getString(2));
				json.put("key",key);
				res.close();
				stt.close();
				c.close();	
				return json;			
			}else{
				res.close();
				stt.close();
				c.close();
				throw new wrongPasswordException();
			}			
		}catch (SQLException e) {
			return ErrorMsg.otherError(e.getMessage());
		} catch (JSONException e) {
			return ErrorMsg.otherError(e.getMessage());
		}
		
	}
	
	public static int keyValid(String key) throws KeyInvalidException, SQLException {
		try {
			
			int id;
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from Sessions s where s.key='"+key+"';");
		
			if(res.next()==true){
					id = res.getInt(2);
					if(res.getInt(3) == 1){
						res.close();
						stt.close();
						c.close();			
						throw new KeyInvalidException();
					}
			}else{
				res.close();
				stt.close();
				c.close();			
				throw new KeyInvalidException();
			}
			
			res.close();
			stt.close();
			c.close();
			return id;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public static void logout(String key) throws KeyInvalidException {
		try {	
			UserTools.keyValid(key);
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			
			String sql = "UPDATE `Sessions` SET `expired`=1 WHERE `key` = '" + key+"';";
			stt.executeUpdate(sql);
			
		} catch (SQLException e) {
			throw new KeyInvalidException();
		}
		
	}
	
	public static JSONObject info(int id,int loggedUser) throws userDoesntExistException{
		JSONObject json = new JSONObject();
		try {

			
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.id="+id+";");
			res.next();
			
			int numFriends = FriendTools.friendCound(id);
			
			json.put("id", id);
			json.put("login",res.getString(2));
			json.put("first_name", res.getString(3));
			json.put("last_name", res.getString(4));
			
			if(loggedUser != -1){
				res = stt.executeQuery("SELECT *  FROM `Friends` WHERE `id_from` =" + id + " AND `id_to` = "+ id +";");
				if(res.next()){
					json.put("friend_with", "yes");
				}else{
					json.put("friend_with", "no");
				}
			}
			
			json.put("friend_count", numFriends);
			json.put("last_jweets", MessageTools.listMessages(id, 5, 0,null));
			
			
			
		} catch (SQLException e) {
			return ErrorMsg.otherError(e.toString());
		} catch (JSONException e) {
			return ErrorMsg.otherError(e.toString());
		} catch (emptyResultException e) {
			return ErrorMsg.otherError(e.toString());
		}	
		return json;
	}
	
	private static String getKey(int id){
	try {
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from Sessions s where s.id_user='"+id+"'AND  `expired` =0;");
			String key;
			int queryResult = 0;
			if(res.next() && !res.getBoolean(3)){
				key = res.getString(1);	
				queryResult = 1;
			}else{
				key = generateKey();
				String query = "INSERT INTO `dias_ghanem`.`Sessions` (`key`, `id_user`, `expired`, `time`) VALUES ('"+key+"','"+id+"', '0', CURRENT_TIMESTAMP);";
				queryResult = stt.executeUpdate(query);
			}
			
			stt.close();
			c.close();
			return (queryResult == 0)?null:key;			
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
	
	/*
	 * creation d'un cle et il retour dans un format de string
	 * il va le sauvgarde dans le db apres
	 * MAKING A STAND TWAT
	 * 
	 */
	private static String generateKey() {
		String key = "";
		for(int i = 0 ; i <32 ; i++)
			key = key + randomChar();
		
		return key;
	}	
	
	private static char randomChar(){		
		Random r = new Random();
		double rnd = Math.random();
		if(rnd < 0.3){
			return (char) (97 + r.nextInt(25));
		}else if(rnd < 0.7){
			return (char)(48 + r.nextInt(10));
		}else{
			return (char)(65 + r.nextInt(25));
		}
	}

	
}
