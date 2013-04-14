package services;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 * 
 * JSON messages helpers 
 */
public class JSONtools {

	/**
	 * Error Message
	 * @param msg
	 * @param errorCode
	 * @return
	 */
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
	
	/**
	 * Ok Message
	 * @return
	 */
	public static JSONObject ok(){
		JSONObject res = new JSONObject();
		try {
			res.put("message","ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;

	}
}
