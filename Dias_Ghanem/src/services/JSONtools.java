package services;


import org.json.JSONException;
import org.json.JSONObject;

import bd.auth.AuthTools;

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
		try {
			res.put("key",AuthTools.createKey());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}

	

}
