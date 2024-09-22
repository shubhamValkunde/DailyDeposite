package custDB;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBCustomerHelper {

    public void insertCustomer(Customer c) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            Document doc = new Document("name", c.getName())
                    .append("mob_No", c.getMob_No())
                    .append("aadhar_No", c.getAadhar_No())
                    .append("custId", c.getCustId())
                    .append("balance", c.getBalance())
                    .append("address", c.getAddress());
                    

            table.insertOne(doc);
            System.err.println("Customer Added Successfully");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void addBalance(String custId, double amountToAdd, Date depositDate) {
    try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
        MongoDatabase db = mongoClient.getDatabase("db");
        MongoCollection<Document> table = db.getCollection("customer");
        MongoCollection<Document> eTable = db.getCollection("entries");

        table.updateOne(eq("custId", custId), inc("balance", amountToAdd));
        Document updatedCustomerDoc = table.find(eq("custId", custId)).first();

        if (updatedCustomerDoc != null) {
            double updatedBalance = updatedCustomerDoc.getDouble("balance");
            

            // Create and add entry
            Entry entry = new Entry();
            entry.setCustId(custId);
            entry.setDate(depositDate); // Use the provided date
            entry.setAmount(amountToAdd);
            entry.setBalance(updatedBalance);
            insertEntry(eTable, entry);
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}



    private void insertEntry(MongoCollection<Document> etable, Entry entry) {
        try {
            Document doc = new Document("custId", entry.getCustId())
                    .append("date", entry.getDate())
                    .append("amount", entry.getAmount())
                    .append("balance", entry.getBalance());
            etable.insertOne(doc);
        } catch (Exception ex) {
            System.err.println("Error inserting entry: " + ex.getMessage());
        }
    }

    public List<Entry> getEntriesByCustomerId(String custId) {
        List<Entry> entries = new ArrayList<>();
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> entriesTable = db.getCollection("entries");

            for (Document doc : entriesTable.find(eq("custId", custId))) {
                Entry entry = new Entry();
                entry.setCustId(doc.getString("custId"));
                entry.setDate(doc.getDate("date"));
                entry.setAmount(doc.getDouble("amount"));
                entry.setBalance(doc.getDouble("balance"));
                entries.add(entry);
            }
        } catch (Exception e) {
            System.err.println("Error fetching entries: " + e.getMessage());
        }
        return entries;
    }

    public void updateCustomer(String custId, Customer c) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            Bson filter = Filters.eq("custId", custId);

            Bson updateName = Updates.set("name", c.getName());
            Bson updateMob_No = Updates.set("mob_No", c.getMob_No());
            Bson updateBalance = Updates.set("balance", c.getBalance());
            Bson updateAddress = Updates.set("address", c.getAddress());
            Bson updateAadhar_No = Updates.set("aadhar_No", c.getAadhar_No());
            Bson updateCustId = Updates.set("custId", c.getCustId());

            Bson combinedUpdates = Updates.combine(updateName, updateMob_No, updateBalance, updateAddress, updateAadhar_No, updateCustId);

            table.updateOne(filter, combinedUpdates);
            System.out.println("Customer updated successfully");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void displayCustomer(String custId) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            Document customer = table.find(eq("custId", custId)).first();

            if (customer != null) {
                System.out.println("Customer Details: " + customer.toJson());
            } else {
                System.out.println("Customer not found");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public List<Customer> getCustomerByCustId(String custId) {
        List<Customer> customers = new ArrayList<>();
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            Document query = new Document("custId", custId);
            for (Document result : table.find(query)) {
                customers.add(documentToCustomer(result));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return customers;
    }

    private Customer documentToCustomer(Document doc) {
        try {
            Customer customer = new Customer();
            customer.setName(doc.getString("name"));
            customer.setMob_No(doc.getString("mob_No"));
            customer.setAadhar_No(doc.getString("aadhar_No"));
            customer.setCustId(doc.getString("custId"));
            customer.setBalance(doc.getDouble("balance"));
            customer.setAddress(doc.getString("address"));
             // Assuming the Customer class has a pwd field
            return customer;
        } catch (Exception e) {
            System.err.println("Error converting document to customer: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error converting document to customer", e);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            for (Document doc : table.find()) {
                customers.add(documentToCustomer(doc));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return customers;
    }

    public void deleteCustomer(String custId) {
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
            MongoDatabase db = mongoClient.getDatabase("db");
            MongoCollection<Document> table = db.getCollection("customer");

            Bson filter = Filters.eq("custId", custId);
            table.deleteOne(filter);
            System.out.println("Customer deleted successfully");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
}
    private double calculateInterest(String custId, Date withdrawalDate) {
    double interest = 0;
    try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
        MongoDatabase db = mongoClient.getDatabase("db");
        MongoCollection<Document> entriesTable = db.getCollection("entries");

        // Fetch all entries for the given customer ID
        List<Document> entries = entriesTable.find(eq("custId", custId)).into(new ArrayList<>());

        double carryXamount = 0;

        for (Document entry : entries) {
            Date date = entry.getDate("date");
            LocalDate entryDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate withdrawDate = withdrawalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(entryDate, withdrawDate);
            carryXamount += entry.getDouble("amount");
            double xamount = carryXamount * daysBetween;
            interest = (xamount * 6) / 36500; // Interest calculation
        }

        // Round interest to 2 decimal places
        BigDecimal interestBD = new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP);
        interest = interestBD.doubleValue();
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
    return interest;
}

public List<MonthlySummary> calculateMonthlySummary(String custId) {
    List<MonthlySummary> summaries = new ArrayList<>();
    try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
        MongoDatabase db = mongoClient.getDatabase("db");
        MongoCollection<Document> entriesTable = db.getCollection("entries");
        MongoCollection<Document> withdrawalsTable = db.getCollection("withdrawals");

        // Fetch all entries for the given customer ID
        List<Document> entries = entriesTable.find(eq("custId", custId)).into(new ArrayList<>());
        List<Document> withdrawals = withdrawalsTable.find(eq("custId", custId)).into(new ArrayList<>());

        double carryOver = 0;
        double carryXamount = 0;
        double carryInterest = 0;

        // Track the last month with entries
        YearMonth lastEntryMonth = null;
        double lastFirstHalfTotal = 0;
        double lastSecondHalfTotal = 0;
        double lastXamountFirstHalf = 0;
        double lastXamountSecondHalf = 0;

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(2024, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            int firstHalfDays = daysInMonth / 2;
            int secondHalfDays = daysInMonth - firstHalfDays;

            // Fetch entries and withdrawals for the current month
            List<Document> monthlyEntries = entriesTable.find(Filters.and(
                    eq("custId", custId),
                    Filters.gte("date", toDate(yearMonth.atDay(1))),
                    Filters.lte("date", toDate(yearMonth.atEndOfMonth()))
            )).into(new ArrayList<>());

            double firstHalfTotal = 0;
            double secondHalfTotal = 0;
            for (Document entry : monthlyEntries) {
                Date date = entry.getDate("date");
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int dayOfMonth = localDate.getDayOfMonth();
                if (dayOfMonth <= firstHalfDays) {
                    firstHalfTotal += entry.getDouble("amount");
                } else {
                    secondHalfTotal += entry.getDouble("amount");
                }
            }

            // Deduct withdrawals for the current month
            double totalWithdrawals = 0;
            for (Document withdrawal : withdrawals) {
                Date withdrawalDate = withdrawal.getDate("withdrawalDate");
                LocalDate withdrawalLocalDate = withdrawalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                YearMonth withdrawalMonth = YearMonth.of(withdrawalLocalDate.getYear(), withdrawalLocalDate.getMonth());

                if (withdrawalMonth.equals(yearMonth)) {
                    totalWithdrawals += withdrawal.getDouble("withdrawnAmount");
                }
            }

            firstHalfTotal -= totalWithdrawals / 2; // Distribute withdrawals equally
            secondHalfTotal -= totalWithdrawals / 2; // Distribute withdrawals equally

            // Add the carryover amount to the first half total
            firstHalfTotal += carryOver;

            // Always add the first half amount to the second half amount
            secondHalfTotal += firstHalfTotal;

            // Update carryover for the next month
            carryOver = secondHalfTotal;

            double xamountFirstHalf = firstHalfTotal * firstHalfDays;
            double xamountSecondHalf = secondHalfTotal * secondHalfDays;

            double monthlyX = xamountFirstHalf + xamountSecondHalf;
            carryXamount = monthlyX; // Update carryXamount for the next month

            // Calculate the interest for the current month
            double interest = calculateInterest(custId, toDate(yearMonth.atDay(1)));
            BigDecimal interestBD = new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP);
            interest = interestBD.doubleValue();

            carryInterest += interest; // Accumulate interest

            // If there are no entries for the current month, use the last available data
            if (monthlyEntries.isEmpty() && lastEntryMonth != null) {
                firstHalfTotal = lastSecondHalfTotal;
                secondHalfTotal = lastSecondHalfTotal;

                carryOver = secondHalfTotal;
                xamountFirstHalf = firstHalfTotal * firstHalfDays;
                xamountSecondHalf = secondHalfTotal * secondHalfDays;
                monthlyX = xamountFirstHalf + xamountSecondHalf;
                carryXamount = monthlyX;

                interest = calculateInterest(custId, toDate(yearMonth.atDay(1)));
                interestBD = new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP);
                interest = interestBD.doubleValue();
            } else {
                // Update the last month's data
                lastFirstHalfTotal = firstHalfTotal;
                lastSecondHalfTotal = secondHalfTotal;
                lastXamountFirstHalf = xamountFirstHalf;
                lastXamountSecondHalf = xamountSecondHalf;
                lastEntryMonth = yearMonth;
            }

            // Create and add the summary
            MonthlySummary summary = new MonthlySummary();
            summary.setMonth(yearMonth.getMonth().toString());
            summary.setDaysInMonth(daysInMonth);
            summary.setFirstHalfAmount(firstHalfTotal);
            summary.setSecondHalfAmount(secondHalfTotal);
            summary.setxAmountFirstHalf(xamountFirstHalf);
            summary.setXamountSecondHalf(xamountSecondHalf);
            summary.setTotalYearXAmount(monthlyX);
            summary.setCarryOver(carryOver);
            summary.setMonthlyInterest(interest);

            summaries.add(summary);
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
    return summaries;
}



public void withdrawBalance(String custId, double amountToWithdraw, Date withdrawalDate) {
    try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {
        MongoDatabase db = mongoClient.getDatabase("db");
        MongoCollection<Document> table = db.getCollection("customer");
        MongoCollection<Document> eTable = db.getCollection("entries");
        MongoCollection<Document> withdrawalsTable = db.getCollection("withdrawals");

        // Find the customer's current balance and start date
        Document customerDoc = table.find(eq("custId", custId)).first();
        if (customerDoc != null) {
            double currentBalance = customerDoc.getDouble("balance");
            Date startDate = customerDoc.getDate("startDate");

            // Calculate the date 6 months from the start date
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.MONTH, 6);
            Date sixMonthsDate = cal.getTime();

            // Check if the withdrawal is before or after the 6-month completion
            double amountToGive = amountToWithdraw;
            double penalty = 0;
            double totalWithdrawals = 0;

            if (withdrawalDate.before(sixMonthsDate)) {
                // Apply 3% penalty
                penalty = amountToWithdraw * 0.03;
                amountToGive = amountToWithdraw - penalty;
                System.out.println("Penalty applied: " + penalty);
                System.out.println("Amount given to customer: " + amountToGive);
                
                // Reset interest for withdrawals before 6 months
                // Interest calculation is done at the end of the year
                double interest = 0;
                BigDecimal interestBD = new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP);
                interest = interestBD.doubleValue();
            } else {
                // Calculate interest based on the withdrawal date
                double interest = calculateInterest(custId, withdrawalDate);
                BigDecimal interestBD = new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP);
                interest = interestBD.doubleValue();
                System.out.println("Interest applied: " + interest);
            }

            // Check if there is enough balance
            if (currentBalance >= amountToWithdraw) {
                // Update the customer's balance
                double updatedBalance = currentBalance - amountToWithdraw;
                table.updateOne(eq("custId", custId), set("balance", updatedBalance));

                // Create and add entry for withdrawal
                Entry entry = new Entry();
                entry.setCustId(custId);
                entry.setDate(withdrawalDate); // Use the provided date
                entry.setAmount(-amountToWithdraw); // Use negative amount for withdrawal
                entry.setBalance(updatedBalance);
                insertEntry(eTable, entry);

                // Record the withdrawal
                Document withdrawalDoc = new Document("custId", custId)
                        .append("withdrawalDate", withdrawalDate)
                        .append("withdrawnAmount", amountToWithdraw);
                withdrawalsTable.insertOne(withdrawalDoc);

            } else {
                System.err.println("Insufficient balance for withdrawal.");
            }
        } else {
            System.err.println("Customer not found.");
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}




public static void main(String[] args) {
 DBCustomerHelper dbHelper = new DBCustomerHelper();
 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        try {
            Date withdrawalDate = dateFormat.parse("10-11-24");
            
           dbHelper.withdrawBalance("Shob37576758",1000,withdrawalDate);
        } catch (ParseException ex) {
            Logger.getLogger(DBCustomerHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        }

        
    
    
}

