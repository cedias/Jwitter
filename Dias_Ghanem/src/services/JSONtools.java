package services;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONtools {

	public static JSONObject error(String msg,int errorCode) {
		JSONObject res = new JSONObject();
		try {
			res.put("message",msg);
			res.put("error_code", errorCode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
		
	}

	public static JSONObject ok(){
		JSONObject res = new JSONObject();
		try {
			res.put("message","ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
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
