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
	
	public static JSONObject postMessage(int id,String login,String message){
		try {
			JSONObject json = new JSONObject();
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject obj = new BasicDBObject();
			
			obj.put("id",id);
			obj.put("login",login);
			obj.put("message",message);
			collection.insert(obj);
			
			//parceque charles a dit que le message id sera a rien et moi j'ai trouve que si c'est utile parceque
			//imaginons ilya un user qui a fait un faute d'orthographe et au meme temps il a un amis un peu grammer naiz
			//qui s'appele aussi charles donc cet user aurait envie de changer son msg tout suite donc c'est mieux 
			//de lui donne son cle tout suite.
			
			json.put("message", message);
			json.put("message_id",obj.get("_id").toString());
			json.put("confirmation", "ok");
			return json;
			
		} catch (UnknownHostException e) {
			return ErrorMsg.bdError();
		} catch (JSONException e) {
			return ErrorMsg.bdError();
		}
	}

	public static JSONObject deleteMessage(String messageId) {
		try{
			Mongo m = new Mongo("li328.lip6.fr",27130);
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
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB(BDStatic.mysql_db);
			DBCollection collection = db.getCollection("messages");
			BasicDBObject query = new BasicDBObject();			
			
			query.put("id",id);
			DBCursor cursor = collection.find(query); 
			
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

