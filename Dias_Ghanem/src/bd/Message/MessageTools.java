package bd.Message;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageTools {

	public static boolean postMessage(String key,String message){
		return true;
	}

	public static boolean idValid(String messageId) {
		return true;
	}

	public static boolean deleteMessage(String key, String messageId) {
		return true;
	}

	public static JSONObject listMessages(String username, int nb, int off) {
		return null;
	}
	
	public static JSONObject MessageId(String msg) {
		JSONObject res = new JSONObject();
		String id = "";
		for(int i = 0 ; i < 6 ; i++){
			Random r = new Random();
			id = id + (char)(48 + r.nextInt(10));
		}
		try {
			res.put("MessageId", id);
			res.put("Message", msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
}
