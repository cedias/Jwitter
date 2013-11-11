package bd;

/**
 * Configuration for databases
 * 
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 */
public interface BDStatic {

	//mysql
	public static boolean mysql_pooling = false;
	public static String mysql_host = "";
	public static String mysql_db   ="";
	public static String mysql_username = "";
	public static String mysql_password = "";
	
	//mongodb
	public static String mongoDb_host = "";
	public static int mongoDb_port = 27130;
	
}
