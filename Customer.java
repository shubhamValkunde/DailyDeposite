package custDB;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class Customer {

    private String name;
    private String custId;
    private String mob_No;
    private double balance;
    private double xBalance;
    private String address;
    private Date startDate;
    public List<Entry> entries;
    private String aadhar_No;
   public String pwd;
   
 public Customer(String name, String mob_No, String aadhar_No, String address, Date startDate) {
        this.name = name;
        this.mob_No = mob_No;
        this.aadhar_No = aadhar_No;
        this.address = address;
        this.entries = new ArrayList<>();
        this.startDate = startDate; // Use the provided startDate

        // Generate custId after other fields are set
        this.custId = generateCustId(name, mob_No, aadhar_No);
    }
 
 private Date balanceAddedDate;




   public Customer(String updatedName, String updatedMob_No, String updatedAadhar_No, double updatedBalance, String updatedAddress, Date startDate) {
        this.name = updatedName;
        this.mob_No = updatedMob_No;
        this.aadhar_No = updatedAadhar_No;
        this.balance = updatedBalance;
        this.address = updatedAddress;
        this.entries = new ArrayList<>();
        
        this.startDate = startDate; // Use the provided startDate

        // Generate custId based on provided rules
        this.custId = generateCustId(updatedName, updatedMob_No, updatedAadhar_No);

        // Print custId after generation
        System.out.println("Generated custId: " + this.custId);
    }
public Customer(String custId, String pwd){
  this.custId = custId;
    this.pwd = pwd;
}
public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    

    public Customer() {
        this.entries = new ArrayList<>();
    }

    public String getCustId() {
        return custId;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public List<Entry> getEntries() {
        return entries;
    }

    private String generateCustId(String name, String mob_No, String aadhar_No) {
        String namePart = getFirstFourChars(name);
        String mobPart = getLastFourChars(mob_No);
        String aadharPart = getLastFourChars(aadhar_No);
        return namePart + mobPart + aadharPart;
    }

    private String getFirstFourChars(String str) {
        return (str.length() >= 4) ? str.substring(0, 4) : str;
    }

    private String getLastFourChars(String str) {
        return (str.length() >= 4) ? str.substring(str.length() - 4) : str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob_No() {
        return mob_No;
    }


    public void setMob_No(String mob_No) {
        this.mob_No = mob_No;
    }

    public double getBalance() {
        return balance;
    }

    public double setBalance(double balance) {
        this.balance = balance;
        return this.balance;
    }

    public double getxBalance() {
        return xBalance;
    }

    public void setxBalance(double xBalance) {
        this.xBalance = xBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar_No() {
        return aadhar_No;
    }

    public void setAadhar_No(String aadhar_No) {
        this.aadhar_No = aadhar_No;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    


public Date getFirstDepositDate() {
    if (entries == null || entries.isEmpty()) {
        System.out.println("No entry found for the customer.");
        return null;
    }

    return entries.stream()
            .map(Entry::getDate)
            .min(Date::compareTo)
            .orElse(null);
}
public static void main(String[] args) {
        
    }
}
