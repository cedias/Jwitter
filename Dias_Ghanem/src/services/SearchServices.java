package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.user.UserTools;
import bd.exceptions.KeyInvalidException;
import bd.search.SearchTools;

public class SearchServices {
	
	public static JSONObject Search(String key , String query , String friends , String nbMessage , String offset){
	
		try {	
			int rtf;
			int id = -1;
			if(query == null || nbMessage == null || offset == null){
				return ErrorMsg.bdError();
			}
			if(friends == null || friends.isEmpty()){
				rtf=0;
			}else{
				rtf = Integer.parseInt(friends);
			}
			if(key != null && !key.isEmpty()){
				id = UserTools.keyValid(key);
			}
			
			return SearchTools.Search(id, query, rtf , Integer.parseInt(nbMessage) , Integer.parseInt(offset));
			
		}catch (KeyInvalidException e) {
			return ErrorMsg.invalidKey();
		} catch (SQLException e) {
			return ErrorMsg.bdError();
		}
	}
}


