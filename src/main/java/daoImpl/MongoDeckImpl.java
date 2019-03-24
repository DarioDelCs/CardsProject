package daoImpl;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import idao.IDeck;
import model.Deck;

public class MongoDeckImpl implements IDeck{

	private MongoClientURI connectionString;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	//abrimos la conexion
	private void open() {
		connectionString = new MongoClientURI("mongodb://localhost:27017");
		mongoClient = new MongoClient(connectionString);
		database = mongoClient.getDatabase("dbDecks");//nos conectamos a la base de datos dbDecks(si no esta la creara al insertar)
		collection = database.getCollection("decks");//recogemos la colecion elegida, si no existe al insertar la creara
	}
	//cerramos la conexion
	private void close() {
		collection=null;
		database=null;
		mongoClient.close();
		connectionString=null;
	}
	
	public Deck getDeckFromName(String name) {
		open();
		
		Document document = collection.find(Filters.eq("name", name)).first();//como sabemos que los nombres no se repiten podemos hacer un first y encontramos el que toca
		
		Deck deck;
		try {
			deck = new Gson().fromJson(document.toJson(), Deck.class);
		}catch (Exception e) {
			deck=null;
		}

		close();
		return deck;
	}

	public boolean saveDeck(Deck deck) {
		open();

		Document document = collection.find(Filters.eq("name", deck.getName())).first();
		ObjectMapper mapper = new ObjectMapper();

		boolean saved=false;
		if(document==null) {
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
		
		close();
		return saved;
	}
	
	public boolean updateDeck(Deck deck) {
		open();

		Document document = collection.find(Filters.eq("name", deck.getName())).first();
		ObjectMapper mapper = new ObjectMapper();
		
		boolean saved=false;
		if(document!=null) {
			String userJson = null;
			try {
				userJson = mapper.writeValueAsString(deck);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
            Document userDoc = Document.parse(userJson);
			collection.findOneAndReplace(Filters.eq("name", deck.getName()), userDoc);
			saved=true;
		}
		
		close();
		return saved;
	}
}
