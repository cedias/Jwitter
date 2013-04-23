package bd.search;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

import services.ErrorMsg;

import bd.BDStatic;
import bd.Database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;

public class SearchTools {

	public static BasicDBList searchRSV(String q) throws SQLException, JSONException{
		BasicDBObject json = new BasicDBObject();
		String DFCount = "SELECT COUNT( * ) FROM DF";
		
		
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
	    ResultSet res = stt.executeQuery(DFCount);
	    res.next();
	    int count = res.getInt(1);
	    
	    String sql = "SELECT a.document, a.tf * LOG( "+count+"/ b.df ) " +
	    		"FROM TF a, DF b " +
	    		"WHERE a.word =  \""+q+"\" " +
	    		"AND b.word =  \""+q+"\" " +
	    		"GROUP BY a.document " + 
	    		"LIMIT 0 , 30";
	    
	    res = stt.executeQuery(sql);
	   BasicDBList list = new BasicDBList();
	   
	    while(res.next()){
	    	json.put("document",res.getString(1));
	    	json.put("score", res.getDouble(2));
	    	list.add(json);
	    }
	    
	    stt.close();
	    c.close();
		
		return list;
	}
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
		String query = "UPDATE  `dias_ghanem`.`DF` SET  `df` =  '" + total  +"' WHERE  `DF`.`word` =  '_total'";
		stt.execute(query);
		
		
		return res;

		}
	
	public static JSONObject calculateTF() throws UnknownHostException, JSONException{
		try{
		String reduce , map;
		map = "function map(){  var text = this.message;" +
				"var words = text.match(/\\w+/g);"+
				"if(words == null) return;"+
				"var tf=[];" +
				"var did=this._id;"+
				"for(var i = 0;i< words.length;i++){" +
				"if (tf[words[i]] === undefined)" +
					"tf[words[i]] = 0;" +
				"tf[words[i]]++; }"+
				"for(var mot in tf){ "+
				"emit(mot,{tf:tf[mot],document:did}); }}";

		
	    reduce= "function reduce(key,values){ "+
	    		"return {key:key,tfs:values};" +
	    		"}";
		
	    Mongo m;
		m = new Mongo(BDStatic.mongoDb_host,BDStatic.mongoDb_port);
		DB db = m.getDB(BDStatic.mysql_db);
		DBCollection collection = db.getCollection("messages");
		Connection c = Database.getMySQLConnection();
		Statement stt = c.createStatement();
		
		MapReduceCommand com = new MapReduceCommand(collection, map, reduce,null,MapReduceCommand.OutputType.INLINE, new BasicDBObject());
		MapReduceOutput out = collection.mapReduce(com);
		
		JSONObject res = new JSONObject();
		
		
		/**
		 * Output:
		 * 	DBObject value: 
				{"document":"51754dfeff5e7c67a49be98b","tf":1}
				OR
				{"tfs":[{"document":"51754dfeff5e7c67a49be97e","tf":1},{"document":"51754dfeff5e7c67a49be97f","tf":1}],"key":"Death"}
		 */
		
		for(DBObject result : out.results()){
			String word = result.get("_id").toString();
			DBObject value = (DBObject) result.get("value");
			String document;
			int tf;
			
			if(!value.containsField("tfs")){
				//simple case
				document = value.get("document").toString();
				tf = (int) Double.parseDouble(value.get("tf").toString());
				res.accumulate("res",new JSONObject().put("word", word).put("doc", document).put("tf", tf));
				
				String query = "UPDATE `dias_ghanem`.`TF` SET `tf` = '"+tf+"' WHERE `TF`.`word` = '"+word+"' AND `TF`.`document` = '"+document+"';";
				
				int valid = stt.executeUpdate(query);
				
				if(valid == 0){
					query = "INSERT INTO `dias_ghanem`.`TF` (`word` ,`document` ,`tf` )VALUES ('"+word+"', '"+document+"', '"+tf+"');";
					stt.execute(query);
				}	
				
			}
			else
			{
				//multiple case
				BasicDBList tfs = (BasicDBList) value.get("tfs");
				for(Object elt : tfs){
					DBObject el = (DBObject) elt;
					document = el.get("document").toString();
					tf = (int) Double.parseDouble(el.get("tf").toString());
					res.accumulate("res",new JSONObject().put("word", word).put("doc", document).put("tf", tf));
					
					
					String query = "UPDATE `dias_ghanem`.`TF` SET `tf` = '"+tf+"' WHERE `TF`.`word` = '"+word+"' AND `TF`.`document` = '"+document+"';";
					
					int valid = stt.executeUpdate(query);
					
					if(valid == 0){
						query = "INSERT INTO `dias_ghanem`.`TF` (`word` ,`document` ,`tf` )VALUES ('"+word+"', '"+document+"', '"+tf+"');";
						stt.execute(query);
					}	
					
				}
			}
			/*String key = value.get("document").toString();
			String tfstring = value.get("tf").toString();
			 tf = (int) Double.parseDouble(tfstring);*/
			
		}
		m.close();
		return res;
		}catch(SQLException e){
			return ErrorMsg.otherError(e.getMessage());
		}
	}

}
