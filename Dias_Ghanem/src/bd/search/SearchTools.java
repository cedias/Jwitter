package bd.search;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;

import bd.BDStatic;
import bd.Database;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

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
	
	public static JSONObject calculateDF() throws UnknownHostException, JSONException, SQLException{
		String map , reduce;
		map = "function map(){  var text = this.message; var words = text.match(/\\w+/g); if(words == null) return; var df=[];  for(var i = 0;i< words.length;i++) df[words[i]]=1;";
		map += "for(var mot in df){ emit(mot,{df:1}); }}";
		
		
		reduce = "function reduce(key, values){";
		reduce += "var total =0;";
		reduce += "for (var i =0; i< values.length;i++) {";
		reduce += "total += values[i].df;}";	
	    reduce += "return {df:total}}";

	    Mongo m;
		m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
		DB db = m.getDB(BDStatic.mysql_db);
		DBCollection collection = db.getCollection("messages");
		
		MapReduceCommand com = new MapReduceCommand(collection, map, reduce,null ,  MapReduceCommand.OutputType.INLINE, new BasicDBObject());
		MapReduceOutput out = collection.mapReduce(com);
		
		JSONObject res = new JSONObject();
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		
		int total = 0;
		for(DBObject result : out.results()){
			String word = result.get("_id").toString();
			DBObject value = (DBObject) result.get("value");
			int df = (int) Double.parseDouble((value.get("df").toString()));
			
			String query = "UPDATE `DF` SET `df` = "+ df + " WHERE `DF`.`word` = '" + word + "' LIMIT 1 ";
			
			int valid = stt.executeUpdate(query);
			
			if(valid == 0){
				query = "INSERT INTO `dias_ghanem`.`DF` (`word`, `df`) VALUES ('"+ word +"', '"+df+"');";
				stt.execute(query);
			}	
			res.put(word, df);
			total+=df;
		}
		String query = "UPDATE `DF` SET `df` = "+ total + " WHERE `DF`.`word` = '" + "_total" + "' LIMIT 1 ";
		
		return res;

		}
	
	public static JSONObject calculateTF() throws UnknownHostException, JSONException{
		String reduce , map;
		map = "function map(){  var text = this.message; var words = text.match(/\\w+/g); var tf=[];  for(var i = 0;i< words.length;i++) tf[words[i]]=1;";
		map += "for(var mot in tf){ emit(mot,{tf:tf[mot],document:this.id}); }}";

		
		reduce = "function reduce(key, values){";	
	    reduce += "return {key:values}}";
		
	    //reduce= "function reduce(key,values){ return {word:key , tfs : values }}";
		
	    Mongo m;
		m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
		DB db = m.getDB(BDStatic.mysql_db);
		DBCollection collection = db.getCollection("messages");
		
		MapReduceCommand com = new MapReduceCommand(collection, map, reduce,null,MapReduceCommand.OutputType.INLINE, new BasicDBObject());
		MapReduceOutput out = collection.mapReduce(com);
		
		JSONObject res = new JSONObject();
		
		for(DBObject result : out.results()){
			String word = result.get("_id").toString();
			DBObject value = (DBObject) result.get("value");
			DBObject key = (DBObject) value.get("key");
			String tf = (String) key.get("tf");
			//int doc = (int) Double.parseDouble((value.get("document").toString()));
			res.put(word, tf);
		}
		return res;
	}

}
