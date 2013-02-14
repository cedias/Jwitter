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



public class AuthTools {
	
	public static int userExists(String login) throws SQLException, userDoesntExistException{
		try {
			int id;
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from User u where u.login='"+login+"';");
		
			if(res.next()==true){
				id = res.getInt(0);
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
				username = res.getString(1);
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
	
	public static JSONObject login(int username, String password)throws wrongPasswordException {
		//check credentials
		//creates new session
		//returns session as JSONObject {id,login,key}
		return null;
	}
	
	public static int keyValid(String key) throws KeyInvalidException, SQLException {
		try {
			int id;
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery("Select * from Sessions s where s.key='"+key+"';");
		
			if(res.next()==true  && !res.getBoolean(2)){
					id = res.getInt(1);
			
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
	

	public static boolean passwordValid(String userName,String password){
		return true;
	}
	
	public static boolean keyUsed(){
		return true;
	}

	public static void logout() throws KeyInvalidException {
		//removes it from the table of active keys in the bd
		return;
	}

	public static boolean deactivate() {
		//removes user
		return true;
	}
	
	
	/*
	 * creation d'un cle et il retour dans un format de string
	 * il va le sauvgarde dans le db apres
	 * 
	 */
	public static JSONObject createKey(String username) {
		String key = "";
		for(int i = 0 ; i <32 ; i++){
			key = key + randomChar();
		}
		// sauvgarde
		JSONObject json = new JSONObject();
		try {
			json.put("key", key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
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
