package servlets;

/**
 * List of parameters for the API.
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *
 *
 */
public interface GetParameters {

	//auth
	public static String login = "login";
	public static String password = "pass";
	public static String key = "key";
	
	//id
	public static String user_id = "uid";
	public static String friend_id = "fid";
	public static String message_id = "mid";
	
	//search
	public static String maxResults = "maxr";
	public static String offset = "off";
	
	//other
	public static String message = "msg";
	public static String firstname = "fname";
	public static String lastName = "lname"; 
	
}
