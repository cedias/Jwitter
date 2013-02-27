package bd.auth;

import java.util.Arrays;

public class test {

	public static void main(String[]args){
		String[] tab = {"test" , "aezraz" , "ezrezr"};
		String exp = "";
		for(int i = 0 ; i < tab.length ; i++){
			exp = exp + tab[i] + "\\" + "|";
		}
		System.out.println(exp.substring(0, exp.length()-2));
	}
}
