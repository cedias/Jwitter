package bd.auth;

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

}
