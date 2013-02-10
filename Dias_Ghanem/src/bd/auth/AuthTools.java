package bd.auth;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;



public class AuthTools {
//TODO
	
	public static boolean userExists(String userName){
		return true;
	}

	public static boolean addUser(String userName, String password, String email) {
		return true;
	}

	public static boolean keyValid(String key) {
		return true;
	}
	
	public static boolean passwordValid(String userName,String password){
		return true;
	}
	
	public static boolean keyUsed(){
		return true;
	}

	public static boolean logout() {
		//removes it from the table of active keys in the bd
		return true;
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
