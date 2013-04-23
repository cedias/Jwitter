package services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;


import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

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

	public static JSONObject SearchRSV(String query) {
		
		try {
			HashMap<String,Double> results = new HashMap<String,Double>();
			String[] queryWords = query.split("\\+");
			JSONObject res  = new JSONObject();
			for(int i =0;i<queryWords.length;i++){
				
				HashMap<String,Double> scores  = SearchTools.searchRSV(queryWords[i]);
				
				
				for(Entry<String,Double> e : scores.entrySet()){
					String doc = e.getKey();
					Double score = e.getValue();
					
					if(!results.containsKey(doc)){
						results.put(doc, score);
					}else
					{
						results.put(doc, results.get(doc)+score);
					}
				}
				
			}
			
			
			for(Entry<String,Double> e : results.entrySet()){
				res.accumulate("docs", new JSONObject().put("doc", e.getKey()).put("score", e.getValue()));
			}
		
			return res;
			
		} catch (SQLException e) {
			return ErrorMsg.otherError(e.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorMsg.bdError();
		}
		
	}
}


