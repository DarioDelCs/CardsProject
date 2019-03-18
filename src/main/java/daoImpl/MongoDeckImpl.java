package daoImpl;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

	private MongoClientURI connectionString;
	private MongoClient mongoClient;
	private DB database;
	private DBCollection collection;
	
	private void open() {
		connectionString = new MongoClientURI("mongodb://localhost:27017");
		mongoClient = new MongoClient(connectionString);
	}
	private void close() {
		mongoClient.close();
		connectionString=null;
	}
	
	//metodo para conectarnos a una BD y abrir(o crear en caso de que aun no exista) una coleccion
	private void connect() {
		database = mongoClient.getDB("dbDecks");//nos conectamos a la base de datos dbDecks(si no esta la creara al insertar)
		collection = database.getCollection("decks");//recogemos la colecion elegida, si no existe al insertar la creara
	}
	private void disconnect() {
		collection=null;
		database=null;
	}
	
	public Deck getDeckFromName(String name) {
		open();
		connect();
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", name);
		DBCursor cursor = collection.find(searchQuery);
		
		Deck deck;
		try {
			DBObject object = cursor.next();
			deck = new Gson().fromJson(object.toString(), Deck.class);
		}catch (NoSuchElementException e) {
			deck=null;
		}

		disconnect();
		close();
		return deck;
	}

	public boolean saveDeck(Deck deck) {
		open();
		connect();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", deck.getName());
		DBCursor cursor = collection.find(searchQuery);
		
		boolean saved=false;
		if(cursor.size()==0) {
			DBObject obj=null;
			try {
				obj = (DBObject) JSON.parse(new ObjectMapper().writeValueAsString(deck));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			collection.insert(obj);
			saved=true;
		}
		
		disconnect();
		close();
		return saved;
	}
	
	public boolean updateDeck(Deck deck) {
		open();
		connect();

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", deck.getName());
		DBCursor cursor = collection.find(searchQuery);
		
		boolean saved=false;
		if(cursor.size()!=0) {
			DBObject obj=null;
			try {
				obj = (DBObject) JSON.parse(new ObjectMapper().writeValueAsString(deck));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			collection.insert(obj);
			saved=true;
		}
		
		disconnect();
		close();
		return saved;
	}
}
