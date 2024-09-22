package custDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Sorts;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.bson.Document;

import java.util.Date;

public class EntryDBHelper {

   public void addEntry(Customer customer, double amount, Date date) {
    try {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("db");
        MongoCollection<Document> table = db.getCollection("entries");

        // Retrieve the latest balance from the database
        Document lastEntry = table.find(eq("custId", customer.getCustId()))
                                  .sort(Sorts.descending("date"))
                                  .first();
        
        double currentBalance = 0;
        if (lastEntry != null) {
            currentBalance = lastEntry.getDouble("balance");
        }

        // Calculate new balance
        double newBalance = currentBalance + amount;
        customer.setBalance(newBalance); // Update the balance in the customer object

        // Create a new entry
        Entry entry = new Entry(customer.getCustId(), date, amount, newBalance);

        // Add entry to customer's list of entries
        customer.addEntry(entry);

        // Insert entry into MongoDB
        insertEntry(table, entry);

        System.out.println("Entry added: " + entry.getCustId() + ", Date: " + entry.getDate() + ", Amount: " + entry.getAmount() + ", Balance: " + entry.getBalance());
    } catch (Exception e) {
        System.err.println("Error adding entry: " + e.getMessage());
    }
}


    private void insertEntry(MongoCollection<Document> table, Entry entry) {
        try {
            Document doc = new Document("custId", entry.getCustId())
                    .append("date", entry.getDate())
                    .append("amount", entry.getAmount())
                    .append("balance",entry.getBalance());

            table.insertOne(doc);
        } catch (Exception ex) {
            System.err.println("Error inserting entry: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-01-01");
        Date entryDate1 = sdf.parse("2024-04-01");
       
        Customer customer = new Customer("Priya", "9527824100", "803445671289", "56 Street, Nashik",startDate);
        

        DBCustomerHelper customerDBHelper = new DBCustomerHelper();
        customerDBHelper.insertCustomer(customer);

        EntryDBHelper entryDBHelper = new EntryDBHelper();
        entryDBHelper.addEntry(customer, 500.0, entryDate1);

        System.out.println("Customer ID: " + customer.getCustId());
        System.out.println("Updated Balance: " + customer.getBalance());

    }
}
