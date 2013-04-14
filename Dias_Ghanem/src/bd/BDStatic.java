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
	public static String mysql_host = "li328.lip6.fr:33306";
	public static String mysql_db   ="dias_ghanem";
	public static String mysql_username = "dias_ghanem";
	public static String mysql_password = "midg3tisfun";
	
	//mongodb
	public static String mongoDb_host = "li328.lip6.fr";
	public static int mongoDb_port = 27130;
	
}
