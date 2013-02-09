package bd.auth;

import java.util.Random;



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
	
	public static String createKey() {
		String key = "";
		for(int i = 0 ; i <32 ; i++){
			key = key + randomChar();
		}
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
