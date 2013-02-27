package bd.search;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;

import bd.BDStatic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class SearchTools {

	public static JSONObject Search(int id_user , String q , int rtf , int nbMessage , int offset){
		
		try {
			int i = 0;
			JSONObject json = new JSONObject();
			Mongo m;
			m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();	
			BasicDBObject regex = new BasicDBObject();
			String queryString = q.replace(',','|');
			
			regex.put("$regex",queryString);
			query.put("message",regex);
			
			DBCursor cursor = collection.find(query).limit(Integer.MAX_VALUE); 
			if(offset > cursor.count())
				return json;
			cursor.skip(offset);
			
			while(cursor.hasNext() && i <= nbMessage){
				json.accumulate("message", cursor.next());	
				i++;
			}
			return json;
	
		} catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		} catch (JSONException e) {
			return ErrorMsg.bdError();
		}
	}
}
