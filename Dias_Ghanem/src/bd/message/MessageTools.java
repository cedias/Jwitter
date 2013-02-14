package bd.message;

import org.json.JSONObject;

import bd.exceptions.emptyResultException;

public class MessageTools {

	public static boolean postMessage(int user,String message){
		return true;
	}

	public static boolean deleteMessage(String messageId) {
		return true;
	}

	public static JSONObject listMessages(int userId, int nb, int off) throws emptyResultException {
		return null;
	}
}
