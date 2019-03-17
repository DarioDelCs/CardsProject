package daoImpl;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import idao.IDeck;
import model.Card;
import model.Deck;

public class MongoDeckImpl implements IDeck{

	public Deck getDeckFromName() {
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
		MongoClient mongoClient = new MongoClient(connectionString);
		
		DB database = mongoClient.getDB("myMongoDb");
		
		database.createCollection("customers", null);
		DBCollection collection = database.getCollection("customers");
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(ExistCardImpl.getInstance().getAllCards().get(0));
		cards.add(ExistCardImpl.getInstance().getAllCards().get(1));
		cards.add(ExistCardImpl.getInstance().getAllCards().get(2));
		
		Deck deck = new Deck("Fuego", 5, cards);
		System.out.println(deck.toString());
		DBObject obj=null;
		try {
			obj = (DBObject) JSON.parse(new ObjectMapper().writeValueAsString(deck));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(obj.toString());
		collection.insert(obj);

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "Fuego");
		DBCursor cursor = collection.find(searchQuery);
		 
		while (cursor.hasNext()) {
		    System.out.println(cursor.next());
		}
		return null;
	}
	
	public static void main(String[] args) {
		new MongoDeckImpl().getDeckFromName();
	}

}
