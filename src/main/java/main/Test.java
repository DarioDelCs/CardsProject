package main;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import daoImpl.ExistCardImpl;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Test {

	protected static String DRIVER = "org.exist.xmldb.DatabaseImpl"; 
    protected static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc"; 
    protected static String collectionPath = "/db/"; 
    protected static String resourceName = "cartas.xml"; 
    
	public static void main(String[] args)  throws Exception{
		mongo();
//		ExistCardImpl cards = ExistCardImpl.getInstance();
////		cards.del(2);
//		ExistCardImpl cards2 = ExistCardImpl.getInstance();
//		System.out.println(cards.getAllCards().toString());
//		System.out.println(cards2.getAllCards().toString());
	}
	
	private static void mongo() {
//		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
		MongoClient mongoClient = new MongoClient(connectionString);
		DB database = mongoClient.getDB("myMongoDb");
		database.createCollection("customers", null);
		DBCollection collection = database.getCollection("customers");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "Shubham");
		document.put("company", "Baeldung");
		collection.insert(document);
		for (String aaa : database.getCollectionNames()) {
			System.out.println(aaa);
		}
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "Shubham");
		DBCursor cursor = collection.find(searchQuery);
		 
		while (cursor.hasNext()) {
		    System.out.println(cursor.next());
		}
		System.out.println(database);
	}
	
	private void connect() throws Exception{
//		Logger logger = LogManager.getLogger(Test.class);
//        logger.trace("Entering application.");
//        Bar bar = new Bar();
//        if (!bar.doIt()) {
//            logger.error("Didn't do it.");
//        }
//        logger.trace("Exiting application.");
	        
		  // initialize database driver 
        Class cl = Class.forName(DRIVER); 
        Database database = (Database) cl.newInstance(); 
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database); 
        
        
        
        // get the collection 
        Collection col = DatabaseManager.getCollection(URI + collectionPath, "admin","admin"); 
        System.out.println(col);

        // query a document 

//        String xQuery = "for $x in doc(’" + resourceName + "’)//* " 
//                        + "return data($x)";
        String xQuery = "doc(\"" + resourceName + "\")//*"; 
        System.out.println("Execute xQuery = " + xQuery); 

        // Instantiate a XQuery service 
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0"); 
        service.setProperty("indent", "yes"); 

        // Execute the query, print the result 
        ResourceSet result = service.query(xQuery); 
        ResourceIterator i = result.getIterator(); 
        while (i.hasMoreResources()) { 
                Resource r = i.nextResource(); 
                System.out.println((String) r.getContent()); 
        } 
	}

	
}
