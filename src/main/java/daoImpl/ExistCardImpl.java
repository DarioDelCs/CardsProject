package daoImpl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import org.xmldb.api.DatabaseManager; 
import org.xmldb.api.base.Collection; 
import org.xmldb.api.base.Database; 
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.google.gson.Gson;

import idao.ICard;
import model.Card;

public class ExistCardImpl implements ICard{
	
	private static ExistCardImpl existCardImpl;
	
	private final String driver = "org.exist.xmldb.DatabaseImpl"; 
	private final String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db/"; //modificar por el puerto correspondiendo, y la IP en caso de que no sea en local
	private final String resourceName = "card_collection.xml"; //modificar por el nombre que tiene el fichero en la base de datos
	
	private Class cl;
	private Database database;
	private Collection col;
	
	private ArrayList<Card> cardList = new ArrayList<Card>();
	
	//abrimos la conexion
	private void open() {
		try {
	        cl = Class.forName(driver);
	        database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
            col = DatabaseManager.getCollection(uri); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	//cerramos la conexion
	private void close() {
		try {
			col.close();
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		cl = null;
		database = null;
	}
	
	public static ExistCardImpl getInstance() {
		if(existCardImpl==null) {
			existCardImpl = new ExistCardImpl();
		}
		return existCardImpl;
	}
	
	//al no dejar que se instancia mas de una vez, solo nos conectaremos a eXist la primera vez que creemos una instancia
	private ExistCardImpl() {
		open();
		
		try {
            XMLResource res = (XMLResource) col.getResource(resourceName);
            JSONObject xmlJSONObj = XML.toJSONObject((String)res.getContent());

            JSONArray allCards = xmlJSONObj.getJSONObject("cards").getJSONArray("card");//el nombre del root, y de los atributos padre
            
            cardList.clear();
            for (Object object : allCards) {
            	Card data = new Gson().fromJson(object.toString(), Card.class);
            	cardList.add(data);
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	public ArrayList<Card> getAllCards() {
		return cardList;
	}
	
//	public Card getCardFromId(int id) {
//		for (Card card : cardList) {
//			if (card.getId() == id) {
//				return card;
//			}
//		}
//		return null;
//	}
	
//	public void del(int index) {
//		cardList.remove(index);
//	}
}
