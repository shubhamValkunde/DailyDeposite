package custDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Date;
import java.util.UUID;
import java.util.List;

public class PrintPassBookDBHelper {
    private MongoDatabase database;

    // Constructor
    public PrintPassBookDBHelper() {
        // Initialize database connection here // Ensure you have a method to get the MongoDB database instance
            MongoClient client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("shriGaneshFinancial");
    }
    

    // Generate a unique printId
      private String generatePrintId() {
        // Generate a random 6-digit number
        int min = 100000;
        int max = 999999;
        int randomNum = min + (int)(Math.random() * ((max - min) + 1));
        
        // Convert the number to a string and return
        return String.valueOf(randomNum);
    }

    // Save PrintPassBook record to the database
    public void savePrintPassBook(PrintPassBook printPassBook) {
        MongoCollection<Document> collection = database.getCollection("PrintPassBook");

        Document doc = new Document("printId", printPassBook.getPrintId())
                .append("custId", printPassBook.getCustId())
                .append("jsonData", printPassBook.getJsonData())
                .append("createdAt", printPassBook.getCreatedAt());

        collection.insertOne(doc);
    }

    // Create a PrintPassBook record with JSON data
    public void createPrintPassBook(String custId, Customer customer, List<Entry> passbookEntries) {
        // Generate JSON data
        Gson gson = new Gson();
        String customerJson = gson.toJson(customer);
        String entriesJson = gson.toJson(passbookEntries);
        String jsonData = "{\"customer\": " + customerJson + ", \"entries\": " + entriesJson + "}";

        // Create a new PrintPassBook instance
        String printId = generatePrintId();
        PrintPassBook printPassBook = new PrintPassBook(printId, jsonData, new Date(),custId);

        // Save to database
        savePrintPassBook(printPassBook);
    }
}
