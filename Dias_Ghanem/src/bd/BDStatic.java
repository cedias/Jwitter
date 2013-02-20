package bd;

public interface BDStatic {
	
	/*
	 * MongoBD port: 27130
	 * connect ssh login mysql  password dias
	 * execute on the site java -cp ".:nom.jar" test.Main;
	 * 
	 * in the ari pcs connect ssh.ufr-info-p6.jussieu.fr
	 */
	public static boolean mysql_pooling = false;
	public static String mysql_host = "li328.lip6.fr:33306";
	public static String mysql_db   ="dias_ghanem";
	public static String mysql_username = "dias_ghanem";
	public static String mysql_password = "midg3tisfun";
	
}
