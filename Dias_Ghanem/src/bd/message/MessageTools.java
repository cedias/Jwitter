package bd.message;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;
import services.JSONtools;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import bd.BDStatic;
import bd.exceptions.emptyResultException;

public class MessageTools {
	
	public static JSONObject postMessage(int user_id,String login,String message){
		try {
			JSONObject json = new JSONObject();
			Mongo m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject obj = new BasicDBObject();
			
			obj.put("id",user_id);
			obj.put("login",login);
			obj.put("message",message);
			collection.insert(obj);
			
			json.put("message", message);
			json.put("message_id",obj.get("_id").toString());
			return json;
			
		} catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		} catch (JSONException e) {
			return ErrorMsg.bdError();
		}
	}

	public static JSONObject deleteMessage(String messageId) {
		try{
			Mongo m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();	
			
			query.put("_id", new ObjectId(messageId));
			collection.remove(query);
			if(collection.findOne(query) == null){
				return JSONtools.ok();
			}else{
				return ErrorMsg.bdError();
			}
			
		}catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		}
		
	}

	public static JSONObject listMessages(int id, int nb, int off) throws emptyResultException {
		try {
			int i = 1;
			
			JSONObject json = new JSONObject();
			Mongo m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();			
			
			query.put("id",id);
			DBCursor cursor = collection.find(query).limit(Integer.MAX_VALUE); 
			
			if(off > cursor.count())
				return json;
			
			cursor.skip(off);
			
			while(cursor.hasNext() && i <= nb){
				json.accumulate("message", cursor.next());	
				i++;
			}
			return json;
			}catch (UnknownHostException e) {
				return ErrorMsg.bdError();
			}catch (JSONException e) {
				return ErrorMsg.bdError();
			}
	}
}

