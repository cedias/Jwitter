package bd.message;

import java.net.UnknownHostException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;
import services.JSONtools;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

import bd.BDStatic;
import bd.exceptions.emptyResultException;


/**
 * static methods for message operations in the databases
 * 
 * @author Charles-Emmanuel Dias
 * @author Marwan Ghanem
 *

 */
public class MessageTools {
	
	/**
	 * Adds a message
	 * 
	 * @param user_id
	 * @param login
	 * @param message
	 * @return message + message's id JSON
	 */
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
			obj.put("date",new Date().toString());
			collection.insert(obj);
			
			json.put("message", message);
			json.put("message_id",obj.get("_id").toString());
			m.close();
			return json;
			
		} catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		} catch (JSONException e) {
			return ErrorMsg.bdError();
		}
	}

	
	/**
	 * Removes a message from the database
	 * @param messageId
	 * @return ok message
	 */
	public static JSONObject deleteMessage(String messageId,int userId) {
		try{
			Mongo m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();
			
			query.put("_id", new ObjectId(messageId));
			query.put("id",userId);	
			WriteResult wr = collection.remove(query);
			
			if(wr.getN() == 1){
				m.close();
				return JSONtools.ok();
			}else{
				m.close();
				return ErrorMsg.wrongParameter();
			}
			
		}catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		}
		
	}

	/**
	 *  List messages (from last to first) 
	 * @param id if you only want one user's messages
	 * @param nb max results
	 * @param off offset
	 * @param last a message id where to stop
	 * @return JSON[message]
	 * @throws emptyResultException
	 */
	public static JSONObject listMessages(int id, int nb, int off,String last) throws emptyResultException {
		try {

			
			JSONObject json = new JSONObject();
			Mongo m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();			
			
			if(id != -1)
				query.put("id",id);
			
			DBCursor cursor = collection.find(query).sort(new BasicDBObject("date",-1)).limit(nb).skip(off); 
			
			while(cursor.hasNext()){
				DBObject next = cursor.next();
				
				if(last != null && last != "" && next.get("_id").toString().equals(last))
				{
					cursor.close();
					m.close();
					return json;
				}
				
				json.accumulate("messages", next);
			}
			
			cursor.close();
			m.close();
			return json;
			
			}catch (UnknownHostException e) {
				return ErrorMsg.bdError();
			}catch (JSONException e) {
				return ErrorMsg.bdError();
			}
	}
	
}

