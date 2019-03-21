package daoImpl;

import java.util.NoSuchElementException;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import idao.IDeck;
import model.Deck;

public class MongoDeckImpl implements IDeck{

	private MongoClientURI connectionString;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
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
		database = mongoClient.getDatabase("dbDecks");//nos conectamos a la base de datos dbDecks(si no esta la creara al insertar)
		collection = database.getCollection("decks");//recogemos la colecion elegida, si no existe al insertar la creara
	}
	private void disconnect() {
		collection=null;
		database=null;
	}
	
	public Deck getDeckFromName(String name) {
		open();
		connect();
		
		MongoCursor<Document> cursor = collection.find(Filters.eq("name", name)).iterator();
		
		Deck deck;
		try {
			Document document = cursor.next();
			deck = new Gson().fromJson(document.toJson(), Deck.class);
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

		MongoCursor<Document> cursor = collection.find(Filters.eq("name", deck.getName())).iterator();
		ObjectMapper mapper = new ObjectMapper();

		boolean saved=false;
		if(!cursor.hasNext()) {
			String userJson = null;
			try {
				userJson = mapper.writeValueAsString(deck);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
            Document userDoc = Document.parse(userJson);
			collection.insertOne(userDoc);
			saved=true;
		}
		
		disconnect();
		close();
		return saved;
	}
	
	public boolean updateDeck(Deck deck) {
		open();
		connect();

		MongoCursor<Document> cursor = collection.find(Filters.eq("name", deck.getName())).iterator();
		ObjectMapper mapper = new ObjectMapper();
		
		boolean saved=false;
		if(cursor.hasNext()) {
			String userJson = null;
			try {
				userJson = mapper.writeValueAsString(deck);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
            Document userDoc = Document.parse(userJson);
            System.out.println(Filters.eq("name", deck.getName()));
			collection.updateOne(Filters.eq("name", deck.getName()), userDoc);
			saved=true;
		}
		
		
		disconnect();
		close();
		return saved;
	}
}
