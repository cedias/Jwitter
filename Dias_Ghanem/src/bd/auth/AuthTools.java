package bd.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import bd.Database;
import bd.exceptions.KeyInvalidException;
import bd.exceptions.userDoesntExistException;
import bd.exceptions.wrongPasswordException;
import services.ErrorMsg;


public class AuthTools {
	
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
			int res = stt.executeUpdate("INSERT INTO User(login,password,nom,prenom) VALUES ('"+login+"','"+password+"','"+nom+"','"+prenom+"');");
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
			}
			else
			{
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
			
			}else
			{
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
			AuthTools.keyValid(key);
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			String sql = "UPDATE `Sessions` SET `expired`=1 WHERE `key` = '" + key+"';";
			stt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new KeyInvalidException();
		}
		
	}
	
	private static String getKey(int id){
	try {
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from Sessions s where s.id_user='"+id+"';");
			String key;
			if(res.next() && !res.getBoolean(3)){
				key = res.getString(1);			
			}else{
				key = generateKey();
				String query = "INSERT INTO `dias_ghanem`.`Sessions` (`key`, `id_user`, `expired`, `time`) VALUES ('"+key+"','"+id+"', '0', CURRENT_TIMESTAMP);";
				stt.executeUpdate(query);
			}
			stt.close();
			c.close();
			return key;
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
	
	public static String generateKey(){
		String chars = "azertyuiopqsdfgERThjklmwwxcvbYUIOPQSDFGHJKLMWXCVBNn147825369AZ";
		String key = "";
		Random r = new Random();
		for(int i = 0; i<32; i++){
			int rand = r.nextInt(chars.length());
			key+=chars.substring(rand,rand+1);
		}
		return key;
	}

	

	

}
