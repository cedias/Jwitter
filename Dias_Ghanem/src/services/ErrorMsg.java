package services;

import org.json.JSONObject;

public class ErrorMsg {
	
	// https://gist.github.com/cedias/6181764a2b050b6c9070 <== Error Messages

	public static JSONObject userAlreadyExists(String username)
	{
		return JSONtools.error("User "+ username +" already exists", 3);
	}
	
	public static JSONObject userDoesntExist(String username)
	{
		return JSONtools.error("Username "+ username +"doesn't exist", 20);
	}
	
	public static JSONObject wrongParameter()
	{
		return JSONtools.error("Invalid Parameters", 84);
	}
	
	public static JSONObject invalidKey()
	{
		return JSONtools.error("Invalid Key", 403);
	}
	
	public static JSONObject bdError()
	{
		return JSONtools.error("BD Error", 900);
	}
	
	public static JSONObject otherError(String message)
	{
		return JSONtools.error(message, 999);
	}

	
	public static JSONObject emptyResult()
	{
		return JSONtools.error("Query Result was empty", 398);
	}

	public static JSONObject invalidMessageId() {
		return JSONtools.error("Invalid Message ID ", 56);
	}

	
	public static JSONObject wrongLogin(){
		return JSONtools.error("Wrong username or password", 291);
	}

}
