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

	public static JSONObject createKey() {
		JSONObject res = new JSONObject();
		String key = "";
		for(int i = 0 ; i <32 ; i++){
			key = key + randomChar();
		}
		try{
			res.put("key", key);
		}catch(JSONException e){
			e.printStackTrace();
		}
		return res;
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
