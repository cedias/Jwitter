package bd.search;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;
import services.FriendServices;

import bd.BDStatic;
import bd.Database;
import bd.friend.FriendTools;

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
			if(rtf == 1){
				String friends = listFriends(id_user);
				regex.clear();
				regex.put("$regex", friends);
				query.put("id",regex);
			}
			
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
	
	public static String listFriends(int id_user){
		String sql = "SELECT f.id_to, u.login, f.time  FROM Friends f, User u WHERE id_from = "+id_user+" AND f.id_to = u.id";
		String friends = "";
		
		
		try {
			Connection c = Database.getMySQLConnection();
			Statement stt = c.createStatement();
			ResultSet res = stt.executeQuery(sql);
					
			if(res.isLast()){;
				return null;
			}
			while(res.next()){
				friends = friends + res.getInt(1);
				friends = friends + '|';
			}
			friends = friends.substring(0, friends.length()-1);
			stt.close();
			c.close();
			return friends;
			
		} catch (SQLException e) {
			return null;
		}		
	}
}

